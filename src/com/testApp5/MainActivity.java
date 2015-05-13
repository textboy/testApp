package com.testApp5;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {
	
	private SlidingViewGroup slidingViewGroup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		
		slidingViewGroup = new SlidingViewGroup(this);
		slidingViewGroup.init(this, point);
		setContentView(slidingViewGroup);
	}
}
