package com.testApp5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UnderBuilding extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.under_building, 
                container, false);
        return layout;
    }
}
