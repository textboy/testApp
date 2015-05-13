package com.testApp5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigateBottomBar extends LinearLayout implements OnClickListener {

	public NavigateBottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater layoutInflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        layoutInflater.inflate(R.layout.bottom_bar, this);
        
        // 初始化布局元素
        initViews();
	}
	
    private View homeLayout;			// 消息界面布局
    private View tongueLayout;		// 联系人界面布局
    private View searchLayout;			// 动态界面布局
    private View aboutLayout;			// 设置界面布局

    private ImageView homeImage;		// 消息图标
    private ImageView tongueImage;	// 联系人图标
    private ImageView searchImage;		// 动态图标
    private ImageView aboutImage;		// 设置图标

    private TextView homeText;		// 消息标题
    private TextView tongueText;		// 联系人标题
    private TextView searchText;			// 动态标题
    private TextView aboutText;		// 设置标题
    
    private UnderBuilding homeFragment;		// 用于展示消息的Fragment
    private TongueFragment tongueFragment;		// 用于展示联系人的Fragment
    private UnderBuilding searchFragment;			// 用于展示动态的Fragment
    private UnderBuilding aboutFragment;		// 用于展示设置的Fragment

    private FragmentTransaction transaction;	// 用于对Transaction进行管理
    private Activity activity;

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        homeLayout = findViewById(R.id.message_layout);
        tongueLayout = findViewById(R.id.contacts_layout);
        searchLayout = findViewById(R.id.news_layout);
        aboutLayout = findViewById(R.id.setting_layout);
        
        homeImage = (ImageView) findViewById(R.id.message_image);
        tongueImage = (ImageView) findViewById(R.id.contacts_image);
        searchImage = (ImageView) findViewById(R.id.news_image);
        aboutImage = (ImageView) findViewById(R.id.setting_image);
        
        homeText = (TextView) findViewById(R.id.message_text);
        tongueText = (TextView) findViewById(R.id.contacts_text);
        searchText = (TextView) findViewById(R.id.news_text);
        aboutText = (TextView) findViewById(R.id.setting_text);
        
        homeLayout.setOnClickListener(this);
        tongueLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    	// 开启一个Fragment事务
    	if (getActivity().getFragmentManager() != null) {
	        transaction = getActivity().getFragmentManager().beginTransaction();
	        if (transaction != null) {
	        	switch (v.getId()) {
	        	case R.id.message_layout:
	        		// 当点击了消息tab时，选中第1个tab
	        		setTabSelectStyle(0);
	        		setTabSelectFragment(0);
	        		break;
	        	case R.id.contacts_layout:
	        		// 当点击了联系人tab时，选中第2个tab
	        		setTabSelectStyle(1);
	        		setTabSelectFragment(1);
	        		break;
	        	case R.id.news_layout:
	        		// 当点击了动态tab时，选中第3个tab
	        		setTabSelectStyle(2);
	        		setTabSelectFragment(2);
	        		break;
	        	case R.id.setting_layout:
	        		// 当点击了设置tab时，选中第4个tab
	        		setTabSelectStyle(3);
	        		setTabSelectFragment(3);
	        		break;
	        	default:
	        		break;
	        	}
	        	transaction.commit();
	        }
    	}
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelectStyle(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearTabSelectStyle();
        // 开启一个Fragment事务
        switch (index) {
        case 0:
            // 当点击了消息tab时，改变控件的图片和文字颜色
            homeImage.setImageResource(R.drawable.test1);
            homeText.setTextColor(Color.WHITE);
            break;
        case 1:
            // 当点击了联系人tab时，改变控件的图片和文字颜色
            tongueImage.setImageResource(R.drawable.test1);
            tongueText.setTextColor(Color.WHITE);
            break;
        case 2:
            // 当点击了动态tab时，改变控件的图片和文字颜色
            searchImage.setImageResource(R.drawable.test1);
            searchText.setTextColor(Color.WHITE);
            break;
        case 3:
        default:
            // 当点击了设置tab时，改变控件的图片和文字颜色
            aboutImage.setImageResource(R.drawable.test1);
            aboutText.setTextColor(Color.WHITE);
            break;
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearTabSelectStyle() {
        homeImage.setImageResource(R.drawable.test);
        homeText.setTextColor(Color.parseColor("#82858b"));
        tongueImage.setImageResource(R.drawable.test);
        tongueText.setTextColor(Color.parseColor("#82858b"));
        searchImage.setImageResource(R.drawable.test);
        searchText.setTextColor(Color.parseColor("#82858b"));
        aboutImage.setImageResource(R.drawable.test);
        aboutText.setTextColor(Color.parseColor("#82858b"));
    }
    
    private void setTabSelectFragment(int index) {
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideTabFragments(transaction);
        switch (index) {
        case 0:
        	if (homeFragment == null) {
                // 如果UnderBuilding为空，则创建一个并添加到界面上
            	homeFragment = new UnderBuilding();
                transaction.add(R.id.content, homeFragment);
            } else {
                // 如果UnderBuilding不为空，则直接将它显示出来
                transaction.show(homeFragment);
            }
            break;
        case 1:
        	if (tongueFragment == null) {
        		final String TONGUE = "tongueFragment";
                // 如果UnderBuilding为空，则创建一个并添加到界面上
                tongueFragment = new TongueFragment();
                transaction.add(R.id.content, tongueFragment, TONGUE);
            } else {
                // 如果UnderBuilding不为空，则直接将它显示出来
                transaction.show(tongueFragment);
            }
            break;
        case 2:
        	if (searchFragment == null) {
                // 如果UnderBuilding为空，则创建一个并添加到界面上
                searchFragment = new UnderBuilding();
                transaction.add(R.id.content, searchFragment);
            } else {
                // 如果UnderBuilding不为空，则直接将它显示出来
                transaction.show(searchFragment);
            }
            break;
        case 3:
        default:
        	if (aboutFragment == null) {
                // 如果UnderBuilding为空，则创建一个并添加到界面上
                aboutFragment = new UnderBuilding();
                transaction.add(R.id.content, aboutFragment);
            } else {
                // 如果UnderBuilding不为空，则直接将它显示出来
                transaction.show(aboutFragment);
            }
            break;
        }
    }
    
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideTabFragments(FragmentTransaction transaction) {
    	final String TONGUE_RESULT = "tongueResultFragment";
    	
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (tongueFragment != null) {
            transaction.hide(tongueFragment);
        }
        if (searchFragment != null) {
            transaction.hide(searchFragment);
        }
        if (aboutFragment != null) {
            transaction.hide(aboutFragment);
        }
        
        Fragment tongueResultFragment = getActivity().getFragmentManager().findFragmentByTag(TONGUE_RESULT);
		if (tongueResultFragment != null && tongueResultFragment instanceof TongueResultFragment) {
			transaction.remove(tongueResultFragment);
		}
    }

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
