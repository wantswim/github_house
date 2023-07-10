package com.rental;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.ApplyModel;
import com.pony.model.ResponseEntry;
import com.pony.util.ToastUtil;


public class OrderMessageActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回 
	private ImageView mIvBack;

	private ApplyModel orderModel;
	
	private TextView mtttYuyue;
	private TextView userName;
	private TextView userPhone;
	private TextView mtvTime;
	private TextView houseName;
	private TextView houseMoney;
	
	private Button mbtnOk;
	private Button mbtnNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_message);
		initWidget();
		initData();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
			
		case R.id.mbtnOk:
			MessageAction(false, "2");
			break;
			
		case R.id.mbtnNo:
			MessageAction(false, "3");
			break;
		}
	}

	@Override
	public void initWidget() {
		
		mbtnOk = (Button) findViewById(R.id.mbtnOk);
		mbtnNo = (Button) findViewById(R.id.mbtnNo);
		
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mtttYuyue = (TextView) findViewById(R.id.mtttYuyue);
		
		userName = (TextView) findViewById(R.id.userName);
		userPhone = (TextView) findViewById(R.id.userPhone);
		mtvTime = (TextView) findViewById(R.id.mtvTime);
		
		houseName = (TextView) findViewById(R.id.houseName);
		houseMoney = (TextView) findViewById(R.id.houseMoney);
		

		mIvBack.setVisibility(View.VISIBLE);
		mbtnOk.setOnClickListener(this);
		mbtnNo.setOnClickListener(this);
		mIvBack.setOnClickListener(this);
		mTvTitle.setText("预约详情");
	}

	@Override
	public void initData() {
		try {
			orderModel = (ApplyModel) this.getIntent().getSerializableExtra("msg");
			houseName.setText(orderModel.getApplyHouseName());
			houseMoney.setText(orderModel.getApplyHouseMoney()+"元/月");
			userName.setText(orderModel.getUserMessage().getUname());
			userPhone.setText(orderModel.getUserMessage().getUphone());
			mtvTime.setText(orderModel.getApplyTime());
			mtttYuyue.setText(orderModel.getReviewMessage().getReviewMessage());
			

			if (orderModel.getApplyState().equals("3")) {
				mbtnOk.setVisibility(View.GONE);
				mbtnNo.setBackgroundResource(R.drawable.btn_no_hui);
				mbtnNo.setFocusable(false);
				mbtnNo.setClickable(false);
				mbtnNo.setText("已经拒绝租客请求");
			} else if (orderModel.getApplyState().equals("2")) {
				mbtnNo.setVisibility(View.GONE);
				mbtnOk.setBackgroundResource(R.drawable.btn_no_hui);
				mbtnOk.setFocusable(false);
				mbtnOk.setClickable(false);
				mbtnOk.setText("已经同意租客请求");
			}
		} catch (Exception e) {
		}

		
		
	}
	private void MessageAction(boolean isShow,String stateMsg) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "updateOrderState");
		params.put("applyState", stateMsg);
		params.put("applyId", orderModel.getApplyId());
		params.put("applyHouseUserId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
	}
	
	
	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultCode:
			ToastUtil.show(OrderMessageActivity.this, entry.getRepMsg());

			break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(OrderMessageActivity.this, strMsg);

	}

	
}
