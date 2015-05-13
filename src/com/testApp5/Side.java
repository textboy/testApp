package com.testApp5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Side {
	private View sideView;
	private ListView listView;
	private View footer;
	final private int pageSize = 20;
	private List<String> sideData = new ArrayList<String>();
	private BaseAdapter adapter;
	private boolean canLoad = true;
	private AtomicInteger currentPage = new AtomicInteger(1);
	private SlidingViewGroup slidingViewGroup;
	
	public Side(final Activity activity, final Context context, final SlidingViewGroup slidingViewGroup) {
		setSlidingViewGroup(slidingViewGroup);
		sideView = LayoutInflater.from(context).inflate(R.layout.side, null);
		footer = LayoutInflater.from(context).inflate(R.layout.side_footer, null);
		sideData.addAll(getData(1));
		adapter = new ArrayAdapter<String>(context, R.layout.side_items,
				R.id.side_text_view, sideData);
		listView = (ListView) this.sideView.findViewById(R.id.side_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(context, "item_click", Toast.LENGTH_SHORT).show();
				initTongueResult(activity);
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					if (getSlidingViewGroup().isSliding()) return true;
					break;
				case MotionEvent.ACTION_UP:
					if (getSlidingViewGroup().isSliding()) return true;
					v.performClick();
			        break;
				default:
					break;
				}
				return false;
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					int firstVisiblePosition = view.getFirstVisiblePosition();
		            if (sideData.size() < getDataCount()) { // current accumulated items count < total items count
		            	canLoad = true;
		            } else {
		            	canLoad = false;
		            }
		            
		            if (firstVisiblePosition >= currentPage.get() * pageSize - 15) {	// 离页底还有15条数据时就准备load data
			            int maxPage = getDataCount() / pageSize;
			            if (currentPage.get() + 1 <= maxPage && canLoad) {
	                        /* 每次翻页前添加页脚 */
	                        listView.addFooterView(footer);
	                        listView.setAdapter(adapter);
	                        /* 创建子线程，执行翻页 */
	                        new Thread(new Runnable() {
	                            public void run() {
	                                try {
	                                    Thread.sleep(3000);
	                                } catch (InterruptedException e) {
	                                    e.printStackTrace();
	                                }
	                                List<String> sideDataByPage = getData(currentPage.get() + 1);
	                                mHandler.sendMessage(mHandler.obtainMessage(1, sideDataByPage));	//add new items
	                            }
	                        }).start();
	                    } else if (canLoad) {
	                    	List<String> sideDataByPage = getData(currentPage.get() + 1);
	                    	mHandler.sendMessage(mHandler.obtainMessage(2, sideDataByPage));	//add last items
	                    }
		            }
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					/* 页脚显示完就删掉 */
					if (listView.getFooterViewsCount() > 0)
						listView.removeFooterView(footer);
					sideData.addAll((List<String>) msg.obj);
					adapter.notifyDataSetChanged();
					currentPage.getAndIncrement();
					break;
				case 2:
					sideData.addAll((List<String>) msg.obj);
					adapter.notifyDataSetChanged();
					currentPage.getAndIncrement();
					break;
				default:
					break;
			}
		}
	};
	
	/*
	 * Refer NavigationHeader.java > goBack
	 */
	public void initTongueResult(Activity activity) {
		final String TONGUE_RESULT = "tongueResultFragment";
		FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
		// hide掉当前的tongue
		Fragment tongueFragment = activity.getFragmentManager().findFragmentById(R.id.content);
		if (tongueFragment instanceof TongueFragment) {
			transaction.hide(tongueFragment);
		}
		// 创建或显示tongueResult
		Fragment tongueResultFragment = activity.getFragmentManager().findFragmentByTag(TONGUE_RESULT);
		if (tongueResultFragment != null && tongueResultFragment instanceof TongueResultFragment) {
			transaction.show(tongueResultFragment);
		} else {
			tongueResultFragment = new TongueResultFragment();
			transaction.add(R.id.content, tongueResultFragment, TONGUE_RESULT);
		}
		transaction.commit();
	}
	
	public List<String> getData(int currentPage) {
		List<String> sideDataByPage = new ArrayList<String>();
		int maxPosition = currentPage * pageSize;
		if (maxPosition > getDataCount()) {
			maxPosition = getDataCount();
		}
		for (int position = (currentPage - 1) * pageSize; position < maxPosition; position++) {
			sideDataByPage.add(mStrings[position].substring(0, (mStrings[position].length() < 12)?mStrings[position].length():12));  // 防止字符过长换行
		}
		return sideDataByPage;
	}
	public int getDataCount() {
		// total items count
		return mStrings.length;
	}
	
	public View getView() {
		return sideView;
	}
	
	public ListView getListView() {
		return listView;
	}
	
	private String[] mStrings = {  
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",  
            "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",  
            "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",  
            "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",  
            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh",  
            "Anthoriro", "Appenzell", "Aragon", "Ardi Gasna", "Ardrahan",  
            "Armenian String", "Aromes au Gene de Marc", "Asadero", "Asiago",  
            "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss", "Babybel",  
            "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal",  
            "Banon", "Barry's Bay Cheddar", "Basing", "Basket Cheese",  
            "Bath Cheese", "Bavarian Bergkase", "Baylough", "Beaufort",  
            "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",  
            "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir",  
            "Bierkase", "Bishop Kennedy", "Blarney", "Bleu d'Auvergne",  
            "Bleu de Gex", "Bleu de Laqueuille", "Bleu de Septmoncel",  
            "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",  
            "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini",  
            "Bocconcini (Australian)", "Boeren Leidenkaas", "Bonchester",  
            "Bosworth"};

	public SlidingViewGroup getSlidingViewGroup() {
		return slidingViewGroup;
	}

	public void setSlidingViewGroup(SlidingViewGroup slidingViewGroup) {
		this.slidingViewGroup = slidingViewGroup;
	}
}
