package com.rental;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;
import com.pony.util.LoadingDialog;
import com.pony.util.ToastUtil;

import net.tsz.afinal.http.AjaxParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XieYiActivity extends BaseActivity {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mIvStu;

	private TextView metjiafang;
	private TextView metyifang;
	HouseModel noticeModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xieyi);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;

		case R.id.mIvStu:
			if (TextUtils.isEmpty(metjiafang.getText().toString())) {
				ToastUtil.ShowCentre(XieYiActivity.this, "请输入甲方姓名");
				return;
			}
			
			if (TextUtils.isEmpty(metyifang.getText().toString())) {
				ToastUtil.ShowCentre(XieYiActivity.this, "请输入乙方姓名");
				return;
			}

			addXieyi(true);

			break;

		}
	}

	@Override
	public void initWidget() {

		metjiafang = (TextView) findViewById(R.id.metjiafang);
		metyifang = (TextView) findViewById(R.id.metyifang);

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("租赁买卖协议");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);

		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setText("确定");
		mIvStu.setVisibility(View.VISIBLE);
		mIvStu.setOnClickListener(this);
	}

	@Override
	public void initData() {
		noticeModel = (HouseModel) this.getIntent().getSerializableExtra("msg");
		metjiafang.setText(MemberUserUtils.getName(this));
		metyifang.setText(noticeModel.getUserName());
	}

	private void addXieyi(boolean isShow) {

		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addXieyi");
		params.put("agreementJiaName", metjiafang.getText().toString());
		params.put("agreementYiName", metyifang.getText().toString());
		params.put("agreementHouseId", noticeModel.getHouseId());
		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在注册...");

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		Intent intent = new Intent(this, PayMessageActivity.class);
		intent.putExtra("agreementId",entry.getRepMsg());
		intent.putExtra("msg",noticeModel);
		startActivity(intent);
		finish();
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(XieYiActivity.this, strMsg);

	}
}
