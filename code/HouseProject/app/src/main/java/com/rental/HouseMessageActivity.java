package com.rental;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;
import com.pony.model.SelectImageItem;
import com.pony.photo.ui.ShowPictureActivity;
import com.pony.util.ToastUtil;
import com.pony.view.ListviewForScrollView;
import com.squareup.picasso.Picasso;

public class HouseMessageActivity extends BaseActivity  {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mIvStu;
	private TextView mtvMoney;
	private TextView mtvPhone;
	private TextView mtvTime;
	private TextView mtvName;
	private TextView metMessage;
	private HouseModel houseModel;
	private Button mbtnPay;
	private int checkFlag = 1;
	private String userName;
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<SelectImageItem> infos = new ArrayList<SelectImageItem>();
	private TextView mtvShopPrice;
	private ImageView infor_image;
	
	
	private TextView houseAddress;
	private TextView housePeiTao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_messages);
		initWidget();
		initData();
		
	
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mbtnPay:
			
			
			if (MemberUserUtils.getUid(this).equals("")) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				if (MemberUserUtils.getUid(this).equals(houseModel.getUserId())) {
					ToastUtil.ShowCentre(this, "这个房源是自己发布的哦!");
					return;
				}
				Intent mllGoBuy = new Intent(this, XieYiActivity.class);
				mllGoBuy.putExtra("msg", houseModel);
				startActivity(mllGoBuy);
			}
			
	
			break;
		case R.id.infor_image:
			Intent infor_image = new Intent(this, ShowPictureActivity.class);
			infor_image.putExtra("piction_path", Consts.URL_IMAGE_LOCAL + houseModel.getHouseImage());
			startActivity(infor_image);
			break;	
		}
	}

	@Override
	public void initWidget() {
		houseAddress = (TextView) findViewById(R.id.houseAddress);
		housePeiTao = (TextView) findViewById(R.id.housePeiTao);
		infor_image = (ImageView) findViewById(R.id.infor_image);
		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setText("收藏");
		mIvStu.setVisibility(View.GONE);
		mIvStu.setOnClickListener(this);
		mbtnPay = (Button) findViewById(R.id.mbtnPay);
		metMessage = (TextView) findViewById(R.id.metMessage);
		mtvName = (TextView) findViewById(R.id.mtvName);
		mtvMoney = (TextView) findViewById(R.id.mtvMoney);
		mtvPhone = (TextView) findViewById(R.id.mtvPhone);

		mtvTime = (TextView) findViewById(R.id.mtvTime);
		
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mtvShopPrice = (TextView) findViewById(R.id.mtvShopPrice);
		
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mbtnPay.setOnClickListener(this);
		infor_image.setOnClickListener(this);
		mTvTitle.setText("房屋详情");

	}

	@Override
	public void initData() {

		try {
			houseModel = (HouseModel) this.getIntent().getSerializableExtra("msg");
			mtvName.setText(houseModel.getHouseName());
			mtvMoney.setText(houseModel.getHouseMoney()+"元/月");
			mtvPhone.setText(houseModel.getHousePhone());
			mtvTime.setText(houseModel.getHouseCreatime());
			metMessage.setText(houseModel.getHouseMessage()+"平米");
			
			mtvShopPrice.setText(houseModel.getHouseMoney()+"元");
			
			Picasso.with(this).load(Consts.URL_IMAGE_LOCAL + houseModel.getHouseImage()).placeholder(R.drawable.default_drawable_show_pictrue)
			.into(infor_image);
			
			houseAddress.setText(houseModel.getHouseAddress());
			housePeiTao.setText(houseModel.getHousePeiTao());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {

		case Consts.actionId.resultCode:
			break;
			
		default:
			break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(this, strMsg);

	}


}
