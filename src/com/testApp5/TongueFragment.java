package com.testApp5;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TongueFragment extends Fragment {
	
	private SlidingViewGroup slidingViewGroup;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		
		slidingViewGroup = new SlidingViewGroup(getActivity());
		slidingViewGroup.init(getActivity(), point);
        return slidingViewGroup;
    }
}
