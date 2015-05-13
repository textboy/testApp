package com.testApp5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends Activity {

    private View layoutIndex;							// index layout

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化布局元素
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        initViews();
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
    	layoutIndex = LayoutInflater.from(getApplicationContext()).inflate(R.layout.index, null);
		setContentView(layoutIndex);
		
		// NavigateBottomBar
		NavigateBottomBar uiNavigateBottomBar = (NavigateBottomBar)findViewById(R.id.uiNavigateBottomBar);
		uiNavigateBottomBar.setActivity(this);
		// 第一次启动时选中第一个tab
		View messageLayout = (View)findViewById(R.id.message_layout);
		messageLayout.performClick();
    }
}
