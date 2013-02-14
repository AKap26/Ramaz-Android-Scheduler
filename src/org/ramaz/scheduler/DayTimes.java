/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

import java.util.ArrayList;

public class DayTimes {
	public static ArrayList<StartEnd> Mon2Thurs;
	public static ArrayList<StartEnd> RoshChodesh;
	public static ArrayList<StartEnd> AMAssembly;
	public static ArrayList<StartEnd> PMAssembly;
	public static ArrayList<StartEnd> Advisory;
	public static ArrayList<StartEnd> FriRoshChodesh;
	public static ArrayList<StartEnd> WinterFri;
	public static ArrayList<StartEnd> Fri;
	
	static {
		Mon2Thurs = new ArrayList<StartEnd>();
		Mon2Thurs.add(new StartEnd("8:50","9:30"));
		Mon2Thurs.add(new StartEnd("9:35","10:15"));
		Mon2Thurs.add(new StartEnd("10:35","11:15"));
		Mon2Thurs.add(new StartEnd("11:20","12:00"));
		Mon2Thurs.add(new StartEnd("12:05","12:45"));
		Mon2Thurs.add(new StartEnd("12:50","1:30"));
		Mon2Thurs.add(new StartEnd("1:35","2:15"));
		Mon2Thurs.add(new StartEnd("2:20","3:00"));
		Mon2Thurs.add(new StartEnd("3:20","4:00"));
		Mon2Thurs.add(new StartEnd("4:05","4:45"));
		
		RoshChodesh = new ArrayList<StartEnd>();
		RoshChodesh.add(new StartEnd("9:10", "9:49"));
		RoshChodesh.add(new StartEnd("9:53", "10:32"));
		RoshChodesh.add(new StartEnd("10:47", "11:26"));
		RoshChodesh.add(new StartEnd("11:30", "12:09"));
		RoshChodesh.add(new StartEnd("12:13", "12:52"));
		RoshChodesh.add(new StartEnd("12:56", "1:35"));
		RoshChodesh.add(new StartEnd("1:39", "2:18"));
		RoshChodesh.add(new StartEnd("2:22", "3:01"));
		RoshChodesh.add(new StartEnd("3:23", "4:02"));
		RoshChodesh.add(new StartEnd("4:06", "4:45"));
		
		AMAssembly = new ArrayList<StartEnd>();
		AMAssembly.add(new StartEnd("8:50", "9:25"));
		AMAssembly.add(new StartEnd("9:30", "10:05"));
		AMAssembly.add(new StartEnd("11:05", "11:40"));
		AMAssembly.add(new StartEnd("11:45", "12:20"));
		AMAssembly.add(new StartEnd("12:25", "1:00"));
		AMAssembly.add(new StartEnd("1:05", "1:40"));
		AMAssembly.add(new StartEnd("1:45", "2:20"));
		AMAssembly.add(new StartEnd("2:25", "3:00"));
		AMAssembly.add(new StartEnd("3:25", "4:00"));
		AMAssembly.add(new StartEnd("4:05", "4:45"));
		
		PMAssembly = new ArrayList<StartEnd>();
		PMAssembly.add(new StartEnd("8:50", "9:25"));
		PMAssembly.add(new StartEnd("9:30", "10:05"));
		PMAssembly.add(new StartEnd("10:10", "10:45"));
		PMAssembly.add(new StartEnd("10:50", "11:25"));
		PMAssembly.add(new StartEnd("11:30", "12:05"));
		PMAssembly.add(new StartEnd("12:10", "12:45"));
		PMAssembly.add(new StartEnd("12:50", "1:25"));
		PMAssembly.add(new StartEnd("1:30", "2:05"));
		PMAssembly.add(new StartEnd("3:30", "4:05"));
		PMAssembly.add(new StartEnd("4:10", "4:45"));
		
		Advisory = new ArrayList<StartEnd>();
		Advisory.add(new StartEnd("8:50", "9:25"));
		Advisory.add(new StartEnd("9:30", "10:05"));
		Advisory.add(new StartEnd("10:10", "10:45"));
		Advisory.add(new StartEnd("10:50", "11:25"));
		Advisory.add(new StartEnd("11:30", "12:05"));
		Advisory.add(new StartEnd("12:10", "12:45"));
		Advisory.add(new StartEnd("12:50", "1:25"));
		Advisory.add(new StartEnd("1:30", "2:05"));
		Advisory.add(new StartEnd("2:30", "3:05"));
		Advisory.add(new StartEnd("3:10", "3:45"));
		
		FriRoshChodesh = new ArrayList<StartEnd>();
		FriRoshChodesh.add(new StartEnd("9:10", "9:39"));
		FriRoshChodesh.add(new StartEnd("9:43", "10:12"));
		FriRoshChodesh.add(new StartEnd("10:16", "10:45"));
		FriRoshChodesh.add(new StartEnd("10:49", "11:18"));
		FriRoshChodesh.add(new StartEnd("11:22", "11:51"));
		FriRoshChodesh.add(new StartEnd("11:55", "12:24"));
		FriRoshChodesh.add(new StartEnd("12:28", "12:57"));
		FriRoshChodesh.add(new StartEnd("1:01", "1:30"));
		FriRoshChodesh.add(new StartEnd("1:34", "2:03"));
		FriRoshChodesh.add(new StartEnd("2:07", "2:36"));
		
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
		Fri.add(new StartEnd("12:55", "1:26"));
		Fri.add(new StartEnd("1:30", "2:01"));
		Fri.add(new StartEnd("2:05", "2:36"));
	}
}