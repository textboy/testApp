package com.testApp5;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Reference - http://blog.csdn.net/worker90/article/details/7909328
 */
public class NavigationHeader extends LinearLayout {
	
	private Activity activity;
	private Button btn_left;
    private TextView tv_title;
    
	public NavigationHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        layoutInflater.inflate(R.layout.navigation_header, this);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goBack();
			}
        });
        
        tv_title = (TextView) findViewById(R.id.tv_title);
	}
	
	/*
	 * Refer Side.java > initTongueResult
	 */
	public void goBack() {
		final String TONGUE = "tongueFragment";
		FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
		// 干掉当前的tongueResult
		Fragment tongueResultFragment = getActivity().getFragmentManager().findFragmentById(R.id.content);
		if (tongueResultFragment instanceof TongueResultFragment) {
			transaction.remove(tongueResultFragment);
		}
		// 返回之前的tongue
		Fragment tongueFragment = getActivity().getFragmentManager().findFragmentByTag(TONGUE);
		if (tongueFragment != null && tongueFragment instanceof TongueFragment) {
			transaction.show(tongueFragment);
		}
		transaction.commit();
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
