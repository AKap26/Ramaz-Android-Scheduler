/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class ClassView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.class_view);
		setTitle("Change Class");
		Intent myIntent= getIntent(); // gets the previously created intent
		String subject = myIntent.getStringExtra("subject");
		String room = myIntent.getStringExtra("room");
		final EditText subjectEdit = (EditText) findViewById(R.id.subject_edit);
		final EditText roomEdit = (EditText) findViewById(R.id.room_edit);
		subjectEdit.setText(subject);
		roomEdit.setText(room);
		Button acceptButton = (Button) findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("subject", subjectEdit.getText().toString());
				returnIntent.putExtra("room", roomEdit.getText().toString());
				setResult(RESULT_OK,returnIntent);
				finish();
		}
		});
		
		Button cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED,returnIntent);
				finish();
		}
		});
	}

}