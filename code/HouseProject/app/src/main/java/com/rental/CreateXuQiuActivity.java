package com.rental;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.pony.adapter.ChoiceTimeAdapter;
import com.pony.adapter.LookListAdapter;
import com.pony.adapter.PractitionersAdapter;
import com.pony.base.BaseActivity;
import com.pony.config.Consts;
import com.pony.db.MemberUserUtils;
import com.pony.model.CategoryModel;
import com.pony.model.HouseModel;
import com.pony.model.LeaveHouseModel;
import com.pony.model.ResponseEntry;
import com.pony.model.UserModel;
import com.pony.model.XuQiuModel;
import com.pony.observable.LookObservable;
import com.pony.util.ToastUtil;
import com.pony.view.DialogListMsg;

public class CreateXuQiuActivity extends BaseActivity {

    // title
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    // 查询按钮
    private Button mbtnAdd;

    private Button btnMoney;
    private Button btnMianJi;
    private Button btnFanWei;


    private String[] zoomArr = new String[]{"西安市碑林区", "西安市新城区", "西安市高新区", "西安市雁塔区"};
    private String[] moneyArr = new String[]{"500-1000", "1001-1500", "1501-2000", "2001-3000", "3000-8000"};
    private String[] mianjiArr = new String[]{"15-40 ", "41-50", "51-70", "71-90", "91-150"};
    private DialogListMsg dialogListMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_zuke);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {

        btnMianJi = (Button) findViewById(R.id.btnMianJi);
        btnMianJi.setOnClickListener(this);

        btnMoney = (Button) findViewById(R.id.btnMoney);
        btnMoney.setOnClickListener(this);

        btnFanWei = (Button) findViewById(R.id.btnFanWei);
        btnFanWei.setOnClickListener(this);


        mbtnAdd = (Button) findViewById(R.id.mbtnAdd);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("添加需求信息");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mbtnAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mIvBack:
                CreateXuQiuActivity.this.finish();
                break;
            case R.id.btnMoney:
                arrFlag = "1";
                dialogListMsg.setTitle().setText("请选择价格区间");
                ChoiceTimeAdapter moneyAdapter = new ChoiceTimeAdapter(this);
                moneyAdapter.setData(moneyArr);
                dialogListMsg.show_listview().setAdapter(moneyAdapter);
                dialogListMsg.Show();
                break;

            case R.id.btnMianJi:
                arrFlag = "2";
                dialogListMsg.setTitle().setText("请选择面积区间");
                ChoiceTimeAdapter mianjiAdapter = new ChoiceTimeAdapter(this);
                mianjiAdapter.setData(mianjiArr);
                dialogListMsg.show_listview().setAdapter(mianjiAdapter);
                dialogListMsg.Show();
                break;

            case R.id.btnFanWei:
                arrFlag = "3";
                dialogListMsg.setTitle().setText("请选择所在区域");
                ChoiceTimeAdapter zoomAdapter = new ChoiceTimeAdapter(this);
                zoomAdapter.setData(zoomArr);
                dialogListMsg.show_listview().setAdapter(zoomAdapter);
                dialogListMsg.Show();
                break;


            case R.id.mbtnAdd:

                if (checkInfor.equals("1")) {
                    createTopicPost(true);
                } else {
                    updateXuQIU(true);
                }

                break;
        }
    }

    private String arrFlag;

    @Override
    public void initData() {

        dialogListMsg = new DialogListMsg(this);


        dialogListMsg.show_listview().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (arrFlag.equals("1")) {
                    btnMoney.setText(moneyArr[position]);
                } else if (arrFlag.equals("2")) {
                    btnMianJi.setText(mianjiArr[position]);
                } else {
                    btnFanWei.setText(zoomArr[position]);
                }

                dialogListMsg.Close();

            }
        });

        dialogListMsg.submit_no().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (arrFlag.equals("1")) {
                    btnMoney.setText("");
                } else if (arrFlag.equals("2")) {
                    btnMianJi.setText("");
                } else if (arrFlag.equals("3")) {
                    btnFanWei.setText("");
                }
                dialogListMsg.Close();
            }
        });

        checkXuQiuInfor(false);
    }

    private void createTopicPost(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "addXuQiu");
        params.put("xuqiuMoney", btnMoney.getText().toString());
        params.put("xuqiuMianJi", btnMianJi.getText().toString());
        params.put("xuqiuAddress", btnFanWei.getText().toString());
        params.put("xuqiuUserId", MemberUserUtils.getUid(this) + "");
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在加载...");

    }


    private void updateXuQIU(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateXuQIU");
        params.put("xuqiuMoney", btnMoney.getText().toString());
        params.put("xuqiuMianJi", btnMianJi.getText().toString());
        params.put("xuqiuAddress", btnFanWei.getText().toString());
        params.put("xuqiuId", xuQiuModel.getXuqiuId() + "");
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在加载...");

    }

    private void checkXuQiuInfor(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "checkXuQiuInfor");
        params.put("userId", MemberUserUtils.getUid(this) + "");
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");

    }

    XuQiuModel xuQiuModel;

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultFlag:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
                    checkInfor = "2";
                    xuQiuModel = mGson.fromJson(entry.getData(), XuQiuModel.class);
                    btnMoney.setText(xuQiuModel.getXuqiuMoney());
                    btnMianJi.setText(xuQiuModel.getXuqiuMianJi());
                    btnFanWei.setText(xuQiuModel.getXuqiuAddress());
                }
                break;

            case Consts.actionId.resultCode:

                ToastUtil.show(this, entry.getRepMsg());
                LookObservable.getInstance().notifyStepChange("ok");
                new Handler().postDelayed(new Runnable() {
                    //
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
                break;
        }


    }

    String checkInfor;

    @Override
    protected void callBackAllFailure(String strMsg, int actionId) {
        super.callBackAllFailure(strMsg, actionId);
        checkInfor = "1";
//        ToastUtil.show(CreateXuQiuActivity.this, strMsg);

    }

}
