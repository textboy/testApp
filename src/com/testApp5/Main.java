package com.testApp5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main {
	
	private Context context;
	private View mainView;
	private ListView listView;
	
	public Main(final Context context, final SlidingViewGroup slidingViewGroup) {
		this.context = context;
		mainView = LayoutInflater.from(context).inflate(R.layout.main, null);
		listView = (ListView) this.mainView.findViewById(R.id.main_list);
		listView.setAdapter(new MainAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(context, "item_click", Toast.LENGTH_SHORT).show();
				slidingViewGroup.moveHandler();
			}
		});
	}
	
	public View getView() {
		return mainView;
	}
	
	public class MainAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMainStrings.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView t = new TextView(context);
			t.setTextSize(24);
			t.setPadding(0, 25, 0, 25);
			t.setText(mMainStrings[position]);
			return t;
		}
	}
	
	private String[] mMainStrings = {  
            "感冒", "咳嗽"};
}
