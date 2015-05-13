package com.testApp5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class SlidingViewGroup extends ViewGroup {
	
	private final float WIDTH_RATE = 0.65f;
	private Main mainView;
	private Side sideView;
	private Point point;
	private boolean isSliding = false;
	private boolean isMoveOut = false;
	private int xSideLeft;
	private int sideWidth;
	private int xMoveSideLeft;
	private int xMoveStart;
	private int yMoveStart;
	private int yMoveSideTop;
	private int yMoveSideBottom;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:				// side出现
				moveIn();
				break;
			case 3:				// side收起来
				moveOut();
				break;
			case 5: 				// 拖动时
				moving();
				break;
			default:
				break;
			}
		}
	};
	
	public void init(Activity activity, Point point) {
		initParam(point);
		initView(activity);
	}
	public void initParam(Point point) {
		this.point = point;
		xSideLeft = (int) (point.x * (1 - WIDTH_RATE));
		sideWidth = (int) (point.x * WIDTH_RATE);
		xMoveSideLeft = xSideLeft;
	}
	public void initView(Activity activity) {
		mainView = new Main(this.getContext(), this);
		sideView = new Side(activity, this.getContext(), this);
		this.addView(mainView.getView());
		this.addView(sideView.getView());
		mainView.getView().setVisibility(View.VISIBLE);
		sideView.getView().setVisibility(View.GONE);
	}
	
	public void moveIn() {
		TranslateAnimation mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAnimation.setDuration(300);
		sideView.getView().setVisibility(View.VISIBLE);
		sideView.getView().startAnimation(mShowAnimation);
	}
	
	public void moveOut() {
		TranslateAnimation mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, 
				Animation.RELATIVE_TO_SELF, 0.0f);
		mHideAnimation.setDuration(300);
		sideView.getView().startAnimation(mHideAnimation);
		mHideAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				sideView.getView().setVisibility(View.GONE);
			}
		});
	}
	
	public void moving() {
		sideView.getView().layout(xMoveSideLeft, yMoveSideTop, (xMoveSideLeft + sideWidth), yMoveSideBottom);
	}
	
	public void moveHandler() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				if (View.VISIBLE == sideView.getView().getVisibility()) {
					if (isMoveOut) {
						// side收起来
						msg.what = 3;
						mHandler.sendMessage(msg);
						mHandler.removeCallbacks(this);
					} else if (isSliding) {
						// 拖动时
						msg.what = 5;
						mHandler.sendMessage(msg);
					}
				} else {
					// side出现
					msg.what = 1;
					mHandler.sendMessage(msg);
					mHandler.removeCallbacks(this);
				}
			};
		}, 0);
	}
	
	
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (View.VISIBLE == sideView.getView().getVisibility() && ev.getX() > xSideLeft) { // 只处理side收起来和side上触摸事件
			int action = ev.getAction();
			
			switch (action) {
				case MotionEvent.ACTION_DOWN:// 按下去
					yMoveSideTop = (int)sideView.getView().getY();
					yMoveSideBottom = sideView.getView().getBottom();
					xMoveStart = (int)ev.getX();
					yMoveStart = (int)ev.getY();
					isSliding = false;
					isMoveOut = false;
					break;
				case MotionEvent.ACTION_MOVE:// 拖动时
					if (!isSliding && Math.abs(ev.getX() - xMoveStart) >= Math.abs(ev.getY() - yMoveStart)) {
						isSliding = true;
					}
					if (isSliding) {		//拖动
//						Log.println(Log.ASSERT, "custom", "left:" + xSideLeft + ", sliding:" + xMoveSideLeft);
						xMoveSideLeft = (int) (xSideLeft + (ev.getX() - xMoveStart));
						if (xMoveSideLeft < xSideLeft) {
							xMoveSideLeft = xSideLeft + 1;  // +1 是为了防止由于计算误差而漏边界
						} else if (xMoveSideLeft > point.x) {
							xMoveSideLeft = point.x;
						}
						moveHandler();
					}
					break;
				case MotionEvent.ACTION_UP:// 放开时
					if (!isMoveOut && (ev.getX() - xMoveStart > 15)) {		// 15而不是0，是为了避免手指微小误差
						isMoveOut = true;
					}
					if (isMoveOut) {		//side收起来
						moveHandler();
					}
					super.dispatchTouchEvent(ev);
					return false;
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public SlidingViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mainView.getView().layout(0, 0, point.x, point.y);
		sideView.getView().layout((int) (point.x * (1 - WIDTH_RATE)), 0, point.x, point.y);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mainView.getView().measure(widthMeasureSpec, heightMeasureSpec);
		sideView.getView().measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	public boolean isSliding() {
		return isSliding;
	}
	public boolean isMoveOut() {
		return isMoveOut;
	}
	
}
