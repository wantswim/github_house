package com.rental;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pony.adapter.ChoiceTimeAdapter;
import com.pony.adapter.PractitionersAdapter;
import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.CategoryModel;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;
import com.pony.model.UserModel;
import com.pony.util.ToastUtil;
import com.pony.view.DialogListMsg;

import net.tsz.afinal.http.AjaxParams;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PayMessageActivity extends BaseActivity  {


    // 标题
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    private TextView mtvPrice;
    private Button mPay;
    private RadioGroup mrdChoice;
    private RadioButton mrbWeiXin = null;
    private RadioButton mrbPay = null;

    private TextView mtvName;

    HouseModel noticeModel;



    private EditText zuName;
    private TextView applyTime;


    private List<String> list_result = new ArrayList<String>();
    private String[] timeArr = new String[]{"一个月","三个月","六个月","一年","两年"};
    private DialogListMsg dialogListMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_message);
        initWidget();
        initData();
    }

    private int choiceType = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mPay:
                OrderAction(true);


                break;

            case R.id.applyTime:
                dialogListMsg.Show();
                break;



        }
    }

    @Override
    public void initWidget() {

        dialogListMsg = new DialogListMsg(this);
        dialogListMsg.setTitle().setText("请选择租赁时间");
        ChoiceTimeAdapter practitionersAdapter = new ChoiceTimeAdapter(this);
        practitionersAdapter.setData(timeArr);
        dialogListMsg.show_listview().setAdapter(practitionersAdapter);


        applyTime = (TextView) findViewById(R.id.applyTime);
        applyTime.setOnClickListener(this);

        mtvName = (TextView) findViewById(R.id.mtvName);
        mPay = (Button) findViewById(R.id.mPay);
        mtvPrice = (TextView) findViewById(R.id.mtvPrice);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("租赁信息");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mPay.setOnClickListener(this);

        zuName = (EditText) findViewById(R.id.zuName);


    }

    int timeMgs;
    @Override
    public void initData() {

        noticeModel = (HouseModel) this.getIntent().getSerializableExtra("msg");
        mtvPrice.setText(noticeModel.getHouseName());
        mtvName.setText(noticeModel.getHouseMoney());


        dialogListMsg.show_listview().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                applyTime.setText(timeArr[position]);
                dialogListMsg.Close();

            }
        });

        dialogListMsg.submit_no().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogListMsg.Close();
            }
        });
    }

    /**
     * 订单的添加
     *
     * @param isShow
     */
    private void OrderAction(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "addAply");
        params.put("applyHouseId", noticeModel.getHouseId() + "");
        params.put("applyHouseName", noticeModel.getHouseName());
        params.put("applyHouseMoney", noticeModel.getHouseMoney());
        params.put("applyUserId", MemberUserUtils.getUid(this));
        params.put("applyUserName",MemberUserUtils.getName(this));
        params.put("applyHouseUserId", noticeModel.getUserId());

        params.put("applyZLRealName", zuName.getText().toString());
        params.put("applyZLTime",applyTime.getText().toString());
        params.put("agreementId", this.getIntent().getStringExtra("agreementId"));
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在预约...");
    }

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);
        ToastUtil.show(PayMessageActivity.this, entry.getRepMsg() + "，可在我的预约查看");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PayMessageActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    protected void callBackAllFailure(String strMsg, int actionId) {
        super.callBackAllFailure(strMsg, actionId);
        ToastUtil.show(PayMessageActivity.this, strMsg);

    }
}
