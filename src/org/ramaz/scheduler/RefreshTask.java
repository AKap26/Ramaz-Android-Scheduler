/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

public class RefreshTask extends AsyncTask<String, Void, String> {
	RamazConn conn;
	private boolean failed;
	private Context context;
	private ProgressDialog dialog;
	ArrayList<ArrayList<ClassRoom>> schedule;
	
	public RefreshTask(Context context) {
		this.context = context;
	}
	
	@Override
	public void onPreExecute() {
		this.failed = false;
		this.schedule = new ArrayList<ArrayList<ClassRoom>>();
		if (this.conn == null) {
			this.conn = new RamazConn();
		}
		/*
		this.dialog = new ProgressDialog(context);
		this.dialog.setMessage("Downloading...");
		this.dialog.show();
		this.dialog.setCancelable(true);*/
		this.dialog = ProgressDialog.show(this.context, "", "Downloading...", true, true,
				new DialogInterface.OnCancelListener() {
					
					public void onCancel(DialogInterface dialog) {
						System.out.println("Asynctask stopped");
						RefreshTask.this.cancel(true);
					}
				});
	}
	
	@Override
	public String doInBackground(String... data) {
		String username = data[0];
		String password = data[1];
		try {
			System.out.println("Downloading...");
			this.conn.downloadData(username, password);
			System.out.println("Done downloading");
			this.schedule = this.conn.getSched();
			System.out.println("Got Schedule");
		} catch (Exception e) {
			this.dialog.dismiss();
			this.failed = true;
			return e.toString();
		}
		return "";
	}
	
	@Override
	public void onPostExecute(String possible_error) {
		if (this.failed) {
			Toast.makeText(this.context, possible_error, Toast.LENGTH_SHORT).show();
			return;
		} else {
			Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show();
			this.dialog.dismiss();
			SchedView x = (SchedView)this.context;
			x.setSchedule(this.schedule);
			for (ArrayList<ClassRoom> i: x.schedule) {
				for (ClassRoom j: i) {
					System.out.println(j.subject + " " + j.room);
				}
			}
			x.writeSchedule();
			x.displayTimes(x.lastDayType);
			x.displayClasses(x.lastWeekDay);
		}
	}
}
