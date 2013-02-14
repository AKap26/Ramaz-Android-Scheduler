package org.ramaz.scheduler;

import android.content.Context;
import android.widget.LinearLayout;

public class ProbeLinearLayout extends LinearLayout {
	int w, h; // Used to return to the caller the size of the screen
	
	public ProbeLinearLayout(Context context) {
		super(context);
	}
	
	@Override
	public void onMeasure(int wSpec, int hSpec) {
		this.w = MeasureSpec.getSize(wSpec);
		this.h = MeasureSpec.getSize(hSpec);
		setMeasuredDimension(this.w, this.h);
	}
}
