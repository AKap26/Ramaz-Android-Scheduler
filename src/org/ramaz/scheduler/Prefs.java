/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

import org.ramaz.scheduler.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
 
public class Prefs extends PreferenceActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.pref_layout);
        }
}