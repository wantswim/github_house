package com.rental;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.reflect.TypeToken;
import com.pony.adapter.ShopSendAdapter;
import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.listview.IsSendListner;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MyFaBuListActivity extends BaseActivity implements IsSendListner {

    // 标题
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    private TextView mIvStu;
    private ListView mListMessage;
    private List<HouseModel> list_result = new ArrayList<HouseModel>();
    private String state;
    private LinearLayout mllNomessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        initWidget();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mivCreateMessage:
                break;
        }
    }

    @Override
    public void initWidget() {

        mIvStu = (TextView) findViewById(R.id.mIvStu);
        mIvStu.setText("添加");
        mIvStu.setVisibility(View.GONE);
        mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
        mListMessage = (ListView) findViewById(R.id.mListMessage);

        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        state = this.getIntent().getStringExtra("state");
        mTvTitle.setText("我的发布");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mIvStu.setOnClickListener(this);
    }

    @Override
    public void initData() {
        listShopPhoneUserMessage(true);
        mListMessage.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MyFaBuListActivity.this, HouseMessageActivity.class);
                intent.putExtra("msg", list_result.get(position));
                startActivity(intent);
            }
        });
    }

    private void listShopPhoneUserMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listFbuUserMessage");
        params.put("userId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }


    private void deleteShop(boolean isShow, HouseModel shopModel) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "deletehouse");
        params.put("houseId", shopModel.getHouseId());
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }

    ShopSendAdapter listAdapter;

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
                        listAdapter = new ShopSendAdapter(this, list_result, this);
                        mListMessage.setAdapter(listAdapter);
                        mllNomessage.setVisibility(View.GONE);
                    } else {
                        mllNomessage.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case Consts.actionId.resultCode:
                list_result.remove(posIndex);
                listAdapter.notifyDataSetChanged();
                break;

        }

    }

    private int posIndex;

    @Override
    public void setIsSend(int pos, HouseModel shopModel) {
        posIndex = pos;
        deleteShop(false, shopModel);

    }

    @Override
    public void setIsUpdate(int pos, HouseModel shopModel) {
//        Intent intent = new Intent(this, UpdateShopActivity.class);
//        intent.putExtra("msg", shopModel);
//        startActivity(intent);
    }





}
