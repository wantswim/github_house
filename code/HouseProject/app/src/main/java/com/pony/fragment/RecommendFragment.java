package com.pony.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.tsz.afinal.http.AjaxParams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.pony.adapter.LookHouseAdapter;
import com.pony.adapter.LookListAdapter;
import com.pony.base.BaseFragment;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.HouseModel;
import com.pony.model.LeaveHouseModel;
import com.pony.model.ResponseEntry;
import com.pony.observable.LookObservable;
import com.pony.util.ToastUtil;
import com.rental.HouseMessageActivity;
import com.rental.R;


public class RecommendFragment extends BaseFragment implements Observer{
	// 获取view
	private View rootView;
	// 获取控件
	private ListView mListMessage;
	private LinearLayout mllNomessage;
	private List<HouseModel> list_result = new ArrayList<HouseModel>();

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
		initWidget();
		initData();
		return rootView;
	}

	@Override
	public void initWidget() {
		
		mllNomessage = (LinearLayout) rootView.findViewById(R.id.mllNomessage);
		mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initData() {
		listPhoneMessage(true);
		mListMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				Intent intent = new Intent(getActivity(), HouseMessageActivity.class);
				intent.putExtra("msg", list_result.get(pos));
				getActivity().startActivity(intent);
			}
		});
	}

	private void listPhoneMessage(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listHourseRecommendMessage");
		params.put("userId", MemberUserUtils.getUid(getActivity()));
		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);

				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result.clear();
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<HouseModel>>() {
					}.getType());
					LookListAdapter noticeAdapter = new LookListAdapter(getActivity(), list_result);
					mListMessage.setAdapter(noticeAdapter);
					mllNomessage.setVisibility(View.GONE);
					mListMessage.setVisibility(View.VISIBLE);
				} else {
					mllNomessage.setVisibility(View.VISIBLE);
					mListMessage.setVisibility(View.GONE);
				}
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(getActivity(), strMsg);

	}
	@Override
	public void onResume() {
		super.onResume();
		LookObservable.getInstance().addObserver(RecommendFragment.this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LookObservable.getInstance().deleteObserver(RecommendFragment.this);

	}

	@Override
	public void update(Observable observable, Object data) {
		listPhoneMessage(true);
	}

}
