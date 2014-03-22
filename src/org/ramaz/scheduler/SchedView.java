/*
	Ramaz Schedule App for Android
    Copyright (C) 2013  Michael Rosenberg

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ramaz.scheduler;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.ramaz.scheduler.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SchedView extends Activity {

	private int lightRowBg = 0xFFEBEBEB; //0xffaaaaaa;
	private int darkRowBg = 0xFFB0B0B0; //Color.DKGRAY;
	private int textColor = 0xFF000000;
	private int lightHighlightColor = 0xFF99B0F0;
	private int darkHighlightColor = darkRowBg + (lightHighlightColor - lightRowBg); //0xFF5E75B5;
	private int fingerPressColor = 0xFFFFD738;
	private int classNum = 10; // Number of classes in a day
	private int currentPeriodCheckInterval = 5000; // 5 seconds
	public ArrayList<ArrayList<ClassRoom>> schedule = null;
	ArrayList<StartEnd> times = null;
	private RefreshTask refreshTask;
	private Timer timer;
	public int lastWeekDay = 0, lastDayType = 0;
	public void setSchedule(ArrayList<ArrayList<ClassRoom>> sched) {
		this.schedule = sched;
	}
	
	public void writeSchedule() {
		FileOutputStream fos = null;
		try {
			fos = openFileOutput("schedule.txt", Context.MODE_PRIVATE);
		}
		catch (Exception e){}
		for (ArrayList<ClassRoom> i: this.schedule) {
			for (ClassRoom j: i) {
				try {
					fos.write((j.subject.replaceAll(";", "%semi%") + ";" +
							   j.room.replaceAll(";", "%semi%") + "\n").getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			fos.close();
		} catch (IOException e) {}
	}

	private void refresh() {
		if (this.schedule == null)
			this.schedule = new ArrayList<ArrayList<ClassRoom>>();
		this.refreshTask = new RefreshTask(this);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String username = preferences.getString("username", "");
		String password = preferences.getString("password", "");
		this.refreshTask.execute(username, password);
		
		int b = 0;
		for (ArrayList<ClassRoom> i: this.schedule) {
			for (ClassRoom j: i) {
				b++;
			}
		}
		System.out.println("There are " + b + " classes");
		
	}
	
	private void setRowColor(int row_index, int color) {
		TableRow row = (TableRow) findViewById(row_index);
		if (row_index >= classNum || row_index < 0)
			return;
		row.findViewById(R.id.orderText).setBackgroundColor(color);
		row.findViewById(R.id.timeText).setBackgroundColor(color);
		row.findViewById(R.id.classText).setBackgroundColor(color);
		row.findViewById(R.id.roomText).setBackgroundColor(color);
	}
	
	private void setRowColor(TableRow row, int color) {
		row.findViewById(R.id.orderText).setBackgroundColor(color);
		row.findViewById(R.id.timeText).setBackgroundColor(color);
		row.findViewById(R.id.classText).setBackgroundColor(color);
		row.findViewById(R.id.roomText).setBackgroundColor(color);
	}
	
	private void resetRowColors() {
		for (int i = 0; i < classNum; i++) {
			TableRow row = (TableRow) findViewById(i);
			TextView timeTxt = (TextView) row.findViewById(R.id.timeText);
			TextView classTxt = (TextView) row.findViewById(R.id.classText);
			TextView roomTxt = (TextView) row.findViewById(R.id.roomText);
			TextView orderTxt = (TextView) row.findViewById(R.id.orderText);
			int color;
			if (i%2 == 1)
				color = this.darkRowBg;
			else
				color = this.lightRowBg;
			
			orderTxt.setBackgroundColor(color);
			timeTxt.setBackgroundColor(color);
			classTxt.setBackgroundColor(color);
			roomTxt.setBackgroundColor(color);
		}
	}
	
	private int timeStrToInt(String t) {
		int r;
		String[] s = t.split(":");
		r = Integer.parseInt(s[0]) * 100;
		r += Integer.parseInt(s[1]);
		return r;
	}
	
	private void highlightCurrentClass() {
		System.out.println("Checking for current class...");
		resetRowColors();
		int now = timeStrToInt(new SimpleDateFormat("H:mm").format(Calendar.getInstance().getTime()));
		if (now < 845 || now >= 1645)
			return;
		if (times == null)
			return;
		int i = 0;
		for (StartEnd se : times) {
			//System.out.println("now == " + now + ", se.start == " + se.start + ", se.end == " + se.end);
			if (se.skip) // Is this class skipped today?
				continue;
			int start = timeStrToInt(se.start);
			int end = timeStrToInt(se.end);
			if (now < start || now < end) {
				if (i % 2 == 0)
					setRowColor(i, lightHighlightColor);
				else
					setRowColor(i, darkHighlightColor);
				return;
			}
			i++;
		}
	}

	private void loadSchedule() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("schedule.txt"))); // Should throw FileNotFoundException if it fails
		this.schedule = new ArrayList<ArrayList<ClassRoom>>();
		String line = null;
		int count = 0;
		for (int i = 0; i < 10; i++) {
			ArrayList<ClassRoom> row = new ArrayList<ClassRoom>();
			for (int j = 0; j < 6; j++) {
				line = br.readLine();
				System.out.println("Line is " + line + " on line " + ((i*6) + j+1));
				String[] cr = line.split(";");
				if (cr.length < 2) {
					String tmp = cr[0];
					cr = new String[2];
					cr[0] = tmp; cr[1] = "";
				}
				cr[0] = cr[0].replaceAll("%semi%", ";");
				cr[1] = cr[1].replaceAll("%semi%", ";");
				System.out.println("Loaded " + cr[0] + ", " + cr[1]);
				count++;
				row.add(new ClassRoom(cr[0], cr[1]));
			}
			this.schedule.add(row);
		}
		System.out.println("Loaded " + count);
	}
	
	private void loadState() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("state.txt")));
			this.lastDayType = Integer.parseInt(br.readLine());
			this.lastWeekDay = Integer.parseInt(br.readLine());
			br.close();
		} catch (Exception e) {}
	}

	public void displayClasses(int dayChoice) {
		System.out.println("In displayClasses");
		if (schedule == null)
			return;
		if (this.schedule.size() != 10) {
			Toast.makeText(this, "Something went wrong, please refresh", Toast.LENGTH_SHORT).show();
			return;
		}
		this.lastWeekDay = dayChoice;
		//TextView titleText = (TextView)findViewById(R.id.titleText);
		String title = getResources().getStringArray(R.array.week_days)[this.lastWeekDay];
		title += " - ";
		title += getResources().getStringArray(R.array.day_types)[this.lastDayType];
		//titleText.setText(title);
		setTitle(title);
		//System.out.println("Status bar height == " + getStatusBarHeight());
		System.out.println("Schedule size: " + this.schedule.size());
		for (int i = 0; i < this.schedule.size(); i++) {
			/*if (i == 2)
				setRowColor(i, lightHighlightColor);
			if (i == 5)
				setRowColor(i, darkHighlightColor);*/
			TableRow row = (TableRow) findViewById(i);
			TextView classTxt = (TextView) row.findViewById(R.id.classText);
			TextView roomTxt = (TextView) row.findViewById(R.id.roomText);

			ClassRoom curr = this.schedule.get(i).get(dayChoice);
			classTxt.setText(curr.subject);
			System.out.println("Class is \""+curr.subject+"\"");
			roomTxt.setText(curr.room);

		}
	}

	public void displayTimes(int timeChoice) {
		if (this.schedule == null)
			return;
		this.lastDayType = timeChoice;
		//TextView titleText = (TextView)findViewById(R.id.titleText);
		String title = getResources().getStringArray(R.array.week_days)[this.lastWeekDay];
		title += " - ";
		title += getResources().getStringArray(R.array.day_types)[this.lastDayType];
		System.out.println("title == " + title);
		//titleText.setText(title);
		setTitle(title);
		switch (timeChoice) {
		case 0:
			times = DayTimes.Mon2Thurs;
			break;
		case 1:
			times = DayTimes.RoshChodesh;
			break;
		case 2:
			times = DayTimes.AMAssembly;
			break;
		case 3:
			times = DayTimes.PMAssembly;
			break;
		case 4:
			times = DayTimes.Advisory;
			break;
		case 5:
			times = DayTimes.FriRoshChodesh;
			break;
		case 6:
			times = DayTimes.WinterFri;
			break;
		case 7:
			times = DayTimes.Fri;
			break;
		default:
			return;
		}
		Iterator<StartEnd> it = times.iterator();
		int i = 0;
		while (it.hasNext()) {
			StartEnd se = it.next();
			SimpleDateFormat twelveHour = new SimpleDateFormat("h:mm");
			Date d1 = new Date();
			Date d2 = new Date();
			try {
				d1 = twelveHour.parse(se.start);
				d2 = twelveHour.parse(se.end);
			} catch(Exception e){}
			String start = twelveHour.format(d1);
			String end = twelveHour.format(d2);
			LinearLayout row = (LinearLayout) findViewById(i);
			TextView timeTxt = (TextView) row.findViewById(R.id.timeText);
			TextView classTxt = (TextView) row.findViewById(R.id.classText);
			TextView roomTxt = (TextView) row.findViewById(R.id.roomText);
			if (!se.skip) {
				timeTxt.setText(start + "\n" + end);
				classTxt.setTextColor(this.textColor);
				roomTxt.setTextColor(this.textColor);
			} else {
				timeTxt.setText("");
				if (i % 2 == 0) {
					classTxt.setTextColor(this.darkRowBg); // Dark background on
														   // even rows
					roomTxt.setTextColor(this.darkRowBg);
				} else {
					classTxt.setTextColor(this.lightRowBg); // Light on odd
					roomTxt.setTextColor(this.lightRowBg);
				}
			}
			//System.out.println(se.start + " " + se.end);
			i++;
		}
		highlightCurrentClass();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(40);
		Intent classViewIntent = new Intent(this, ClassView.class);
		classViewIntent.putExtra("subject", ((TextView) v.findViewById(R.id.classText)).getText().toString());
		classViewIntent.putExtra("room", ((TextView) v.findViewById(R.id.roomText)).getText().toString());
		String period = ((TextView) v.findViewById(R.id.orderText)).getText().toString();
		period = period.substring(0, period.length() - 1);
		startActivityForResult(classViewIntent, Integer.parseInt(period) - 2);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode == RESULT_OK){
			String subject = data.getStringExtra("subject");
			String room = data.getStringExtra("room");
			ClassRoom newClass = this.schedule.get(requestCode).get(this.lastWeekDay);
			newClass.subject = subject;
			newClass.room = room;
			this.displayClasses(this.lastWeekDay);
			this.writeSchedule();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sched_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		AlertDialog.Builder choose = new AlertDialog.Builder(this);
		switch (item.getItemId()) {
		case R.id.menu_day_type:
			choose.setTitle("Choose Type of Day");
			choose.setItems(R.array.day_types,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							SchedView.this.displayTimes(item);
						}
					});
			AlertDialog popup = choose.create();
			popup.show();
			return true;
		case R.id.menu_week_days:
			choose.setTitle("Choose Day of Week");
			choose.setItems(R.array.week_days,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							SchedView.this.displayClasses(item);
						}
					});
			AlertDialog p = choose.create();
			p.show();
			return true;
		case R.id.menu_prefs:
			Intent i = new Intent(this, Prefs.class);
			startActivity(i);
			return true;
		case R.id.menu_refresh:
			this.refresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.refreshTask = null;
		this.schedule = null;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		LinearLayout root = new LinearLayout(this);
		root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		root.setOrientation(LinearLayout.VERTICAL); // Stay in portrait mode
		
		TableLayout table = new TableLayout(this);
		table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
		
		/*if (requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_text);
		}
		else {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			titleTxt = (TextView) getLayoutInflater().inflate(R.layout.title_text, null);
			root.addView(titleTxt);
		}*/
		
		for (int i = 0; i < classNum; i++) {
			
			TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.sched_row, null);
			row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1f));
			TextView orderTxt = (TextView) row.findViewById(R.id.orderText);
			TextView timeTxt = (TextView) row.findViewById(R.id.timeText);
			TextView classTxt = (TextView) row.findViewById(R.id.classText);
			TextView roomTxt = (TextView) row.findViewById(R.id.roomText);
			
			orderTxt.setTextColor(this.textColor);
			timeTxt.setTextColor(this.textColor);
			classTxt.setTextColor(this.textColor);
			roomTxt.setTextColor(this.textColor);

			orderTxt.setText(Integer.toString(i + 2) + ".");

			registerForContextMenu(row);
			row.setId(i);
			row.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(final View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						runOnUiThread(new Runnable() {
							public void run() {
								setRowColor((TableRow) v, fingerPressColor);
							}
						});
					}
					else if (event.getAction() == MotionEvent.ACTION_UP) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (v.getId() % 2 == 0)
									setRowColor((TableRow) v, lightRowBg);
								else
									setRowColor((TableRow) v, darkRowBg);
								highlightCurrentClass(); // Immediately reset the highlighting in case it was changed
							}
						});
					}
					return false;
				}
			});
		
			table.addView(row);
		}
		root.addView(table);
		setContentView(root);
		resetRowColors();
		
		try {
			this.loadSchedule();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Go to preferences and enter your login info, then click menu↳refresh.", Toast.LENGTH_LONG).show();
			return;
		}
		
		loadState();
		this.displayClasses(this.lastWeekDay);
		this.displayTimes(this.lastDayType);
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						highlightCurrentClass();
					}
				});
			}
		}, 0, currentPeriodCheckInterval);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		System.out.println("Dying...");
		FileOutputStream fos = null;
		try {
			fos = openFileOutput("state.txt", Context.MODE_PRIVATE);
			if (lastDayType != -1 && lastWeekDay != -1) {
				fos.write((this.lastDayType + "\n" + this.lastWeekDay).getBytes());
				fos.close();
			}
		} catch (IOException e) {}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConf) {super.onConfigurationChanged(newConf);}
	
	public int getStatusBarHeight() {
		Rect r = new Rect();
		Window w = getWindow();
		w.getDecorView().getWindowVisibleDisplayFrame(r);
		return r.top;
	}

	public int getTitleBarHeight() {
		int viewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		return (viewTop - getStatusBarHeight());
	}
}
