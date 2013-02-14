package org.ramaz.scheduler;

import org.ramaz.scheduler.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RamazAppActivity extends Activity {
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Context Menu");
        menu.add(Menu.NONE, v.getId(), 0, "Action 1");  
        menu.add(Menu.NONE, v.getId(), 0, "Action 2");  
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button but = (Button)this.findViewById(R.id.clickme);
        registerForContextMenu(but);
        but.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent i = new Intent(RamazAppActivity.this, SchedView.class);
        		startActivity(i);
        		//finish(); // Die after initiating new Activity
        	}
        });
        /*try {
			RamazConn conn = new RamazConn();
			conn.downloadData("rosenbergmi", "redpen522");
			ArrayList<ArrayList<ClassRoom>> mySched = conn.getSched();
			for (ArrayList<ClassRoom> i: mySched) {
				for (ClassRoom j: i) {
					System.out.println(j.subject + " " + j.room);
				}
			}
		} catch (Exception e) {e.printStackTrace();}*/
    }
}