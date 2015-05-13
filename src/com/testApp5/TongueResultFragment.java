package com.testApp5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class TongueResultFragment extends Fragment implements OnClickListener {
	
	private View resultView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		initView();
		return resultView;
	}
	public void initView() {
		resultView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.result, null);
		NavigationHeader uiNavigationHeader = (NavigationHeader)resultView.findViewById(R.id.uiNavigationHeader);
		uiNavigationHeader.setActivity(getActivity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			// 设置返回事件
//			uiNavigationHeader.goBack(getActivity());
			break;
		default:
			break;
		}
		
	}
}
