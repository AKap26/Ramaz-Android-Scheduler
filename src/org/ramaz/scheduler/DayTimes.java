/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

import java.util.ArrayList;

public class DayTimes {
	public static ArrayList<StartEnd> M,R;
	public static ArrayList<StartEnd> A,B,C;
	public static ArrayList<StartEnd> RoshChodesh;
	public static ArrayList<StartEnd> AMAssembly;
	public static ArrayList<StartEnd> PMAssembly;
	public static ArrayList<StartEnd> Advisory;
	public static ArrayList<StartEnd> FriRoshChodesh;
	public static ArrayList<StartEnd> WinterFri;
	public static ArrayList<StartEnd> Fri;
	
	static {
		M,R = new ArrayList<StartEnd>();
		M,R.add(new StartEnd("8:50","9:30"));
		M,R.add(new StartEnd("9:35","10:15"));
		M,R.add(new StartEnd("10:35","11:15"));
		M,R.add(new StartEnd("11:20","12:00"));
		M,R.add(new StartEnd("12:05","12:45"));
		M,R.add(new StartEnd("12:50","13:30"));
		M,R.add(new StartEnd("13:35","14:15"));
		M,R.add(new StartEnd("14:20","15:00"));
		M,R.add(new StartEnd("15:20","16:00"));
		M,R.add(new StartEnd("16:05","16:45"));
		
		A,B,C = new ArrayList<StartEnd>();
		A,B,C.add(new StartEnd("8:50","9:30"));
		A,B,C.add(new StartEnd("9:35","10:15"));
		A,B,C.add(new StartEnd("10:35","11:15"));
		A,B,C.add(new StartEnd("11:20","12:00"));
		A,B,C.add(new StartEnd("12:05","12:45"));
		A,B,C.add(new StartEnd("12:50","13:30"));
		A,B,C.add(new StartEnd("13:35","14:15"));
		A,B,C.add(new StartEnd("14:20","15:00"));
		A,B,C.add(new StartEnd("15:20","16:00"));
		A,B,C.add(new StartEnd("16:05","16:45"));
		
		RoshChodesh = new ArrayList<StartEnd>();
		RoshChodesh.add(new StartEnd("9:10", "9:49"));
		RoshChodesh.add(new StartEnd("9:53", "10:32"));
		RoshChodesh.add(new StartEnd("10:47", "11:26"));
		RoshChodesh.add(new StartEnd("11:30", "12:09"));
		RoshChodesh.add(new StartEnd("12:13", "12:52"));
		RoshChodesh.add(new StartEnd("12:56", "13:35"));
		RoshChodesh.add(new StartEnd("13:39", "14:18"));
		RoshChodesh.add(new StartEnd("14:22", "15:01"));
		RoshChodesh.add(new StartEnd("15:23", "16:02"));
		RoshChodesh.add(new StartEnd("16:06", "16:45"));
		
		AMAssembly = new ArrayList<StartEnd>();
		AMAssembly.add(new StartEnd("8:50", "9:25"));
		AMAssembly.add(new StartEnd("9:30", "10:05"));
		AMAssembly.add(new StartEnd("11:05", "11:40"));
		AMAssembly.add(new StartEnd("11:45", "12:20"));
		AMAssembly.add(new StartEnd("12:25", "13:00"));
		AMAssembly.add(new StartEnd("13:05", "13:40"));
		AMAssembly.add(new StartEnd("13:45", "14:20"));
		AMAssembly.add(new StartEnd("14:25", "15:00"));
		AMAssembly.add(new StartEnd("15:25", "16:00"));
		AMAssembly.add(new StartEnd("16:05", "16:45"));
		
		PMAssembly = new ArrayList<StartEnd>();
		PMAssembly.add(new StartEnd("8:50", "9:25"));
		PMAssembly.add(new StartEnd("9:30", "10:05"));
		PMAssembly.add(new StartEnd("10:10", "10:45"));
		PMAssembly.add(new StartEnd("10:50", "11:25"));
		PMAssembly.add(new StartEnd("11:30", "12:05"));
		PMAssembly.add(new StartEnd("12:10", "12:45"));
		PMAssembly.add(new StartEnd("12:50", "13:25"));
		PMAssembly.add(new StartEnd("13:30", "14:05"));
		PMAssembly.add(new StartEnd("15:30", "16:05"));
		PMAssembly.add(new StartEnd("16:10", "16:45"));
		
		Advisory = new ArrayList<StartEnd>();
		Advisory.add(new StartEnd("8:50", "9:25"));
		Advisory.add(new StartEnd("9:30", "10:05"));
		Advisory.add(new StartEnd("10:10", "10:45"));
		Advisory.add(new StartEnd("10:50", "11:25"));
		Advisory.add(new StartEnd("11:30", "12:05"));
		Advisory.add(new StartEnd("12:10", "12:45"));
		Advisory.add(new StartEnd("12:50", "13:25"));
		Advisory.add(new StartEnd("13:30", "14:05"));
		Advisory.add(new StartEnd("14:30", "15:05"));
		Advisory.add(new StartEnd("15:10", "15:45"));
		
		FriRoshChodesh = new ArrayList<StartEnd>();
		FriRoshChodesh.add(new StartEnd("9:10", "9:39"));
		FriRoshChodesh.add(new StartEnd("9:43", "10:12"));
		FriRoshChodesh.add(new StartEnd("10:16", "10:45"));
		FriRoshChodesh.add(new StartEnd("10:49", "11:18"));
		FriRoshChodesh.add(new StartEnd("11:22", "11:51"));
		FriRoshChodesh.add(new StartEnd("11:55", "12:24"));
		FriRoshChodesh.add(new StartEnd("12:28", "12:57"));
		FriRoshChodesh.add(new StartEnd("13:01", "13:30"));
		FriRoshChodesh.add(new StartEnd("13:34", "14:03"));
		FriRoshChodesh.add(new StartEnd("14:07", "14:36"));
		
		WinterFri = new ArrayList<StartEnd>();
		WinterFri.add(new StartEnd("8:50", "9:19"));
		WinterFri.add(new StartEnd("9:23", "9:52"));
		WinterFri.add(new StartEnd("9:56", "10:25"));
		WinterFri.add(new StartEnd("10:40", "11:09"));
		WinterFri.add(new StartEnd(true));
		WinterFri.add(new StartEnd(true));
		WinterFri.add(new StartEnd(true));
		WinterFri.add(new StartEnd("11:13", "11:42"));
		WinterFri.add(new StartEnd("11:46", "12:15"));
		WinterFri.add(new StartEnd("12:19", "12:48"));
		
		Fri = new ArrayList<StartEnd>();
		Fri.add(new StartEnd("8:50", "9:21"));
		Fri.add(new StartEnd("9:25", "9:56"));
		Fri.add(new StartEnd("10:00", "10:31"));
		Fri.add(new StartEnd("10:35", "11:06"));
		Fri.add(new StartEnd("11:10", "11:41"));
		Fri.add(new StartEnd("11:45", "12:16"));
		Fri.add(new StartEnd("12:20", "12:51"));
		Fri.add(new StartEnd("12:55", "13:26"));
		Fri.add(new StartEnd("13:30", "14:01"));
		Fri.add(new StartEnd("14:05", "14:36"));
	}
}
