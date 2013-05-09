/*
    Copyright (C) 2013  Michael Rosenberg
    See SchedView.java for full notice
*/

package org.ramaz.scheduler;

public class StartEnd {
	public String start;
	public String end;
	public Boolean skip;
	
	public StartEnd(String start, String end) {
		this.start = start;
		this.end = end;
		this.skip = false;
	}
	
	public StartEnd(Boolean b) {
		this.start = "";
		this.end = "";
		this.skip = b;
	}
}