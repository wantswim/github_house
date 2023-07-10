package com.rental;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.http.AjaxParams;
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
import com.pony.model.ResponseEntry;
import com.pony.util.ToastUtil;

public class PswdForgetActivity extends BaseActivity {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private Button mSubmit;

	private EditText metPswdTwo;
	private EditText metPhone;
	private EditText metPswd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_forget);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;

		case R.id.mSubmit:
			
			if (TextUtils.isEmpty(metPhone.getText().toString())) {
				ToastUtil.ShowCentre(PswdForgetActivity.this, "请输入手机号码");
				return;
			}
			
			if (!isPhone(metPhone.getText().toString())) {
				ToastUtil.ShowCentre(PswdForgetActivity.this, "你输入的手机号码格式不正确");
				return;
			}

			
			if (TextUtils.isEmpty(metPswd.getText().toString())) {
				ToastUtil.ShowCentre(PswdForgetActivity.this, "请输入新密码");
				return;
			}
			
			if (TextUtils.isEmpty(metPswdTwo.getText().toString())) {
				ToastUtil.ShowCentre(PswdForgetActivity.this, "请输入再次输入新密码");
				return;
			}
			if(!metPswd.getText().toString().equals(metPswdTwo.getText().toString())){
				ToastUtil.ShowCentre(PswdForgetActivity.this, "新密码与确认密码不一样");
				return;
			}
			createTopicPost(true);

			break;

		}
	}
	public static boolean isPhone(String tel) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(tel); // 此处参数为String的字符串
		return m.matches();
	}
	@Override
	public void initWidget() {

		metPswdTwo = (EditText) findViewById(R.id.metPswdTwo);
		metPhone = (EditText) findViewById(R.id.metPhone);
		metPswd = (EditText) findViewById(R.id.metPswd);

		mSubmit = (Button) findViewById(R.id.mSubmit);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("找回密码");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);

	}

	@Override
	public void initData() {

	}

	private void createTopicPost(boolean isShow) {

		AjaxParams params = new AjaxParams();
		params.put("action_flag", "forgetPswdMessage");
		params.put("upswd", metPswd.getText().toString());
		params.put("uphone", metPhone.getText().toString());
		httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultCode, isShow, "正在注册...");

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		ToastUtil.show(PswdForgetActivity.this, entry.getRepMsg());
		new Handler().postDelayed(new Runnable() {
			//
			@Override
			public void run() {
				finish();
			}
		}, 2000);
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(PswdForgetActivity.this, strMsg);

	}
}
