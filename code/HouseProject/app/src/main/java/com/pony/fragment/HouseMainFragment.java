package com.pony.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.pony.adapter.ChoiceTimeAdapter;
import com.pony.adapter.LookListAdapter;
import com.pony.base.BaseFragment;
import com.pony.config.Consts;
import com.pony.model.HouseModel;
import com.pony.model.ResponseEntry;
import com.pony.observable.HouseObservable;
import com.pony.util.ToastUtil;
import com.pony.view.DialogListMsg;
import com.rental.HouseMessageActivity;
import com.rental.R;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class HouseMainFragment extends BaseFragment implements Observer {

    // 获取view
    private View rootView;

    // 获取控件
    private ListView mListMessage;
    View convertView;
    private LinearLayout mllNomessage;
    private List<HouseModel> list_result = new ArrayList<HouseModel>();
    private List<HouseModel> list_result_search = new ArrayList<HouseModel>();
    private List<HouseModel> list_result_update = new ArrayList<HouseModel>();
    // NoticeAdapter noticeAdapter;
    private TextView mtvTipMessage;

    private EditText metName;
    private TextView mtvSearch;

    private TextView mtv1;
    private TextView mtv2;
    private TextView mtv3;


    private int choiceFlag = 0;


    private String[] zoomArr = new String[]{"西安市碑林区", "西安市新城区", "西安市高新区", "西安市雁塔区"};
    private String[] moneyArr = new String[]{"500-1000", "1001-1500", "1501-2000", "2001-3000", "3000-8000"};
    private String[] mianjiArr = new String[]{"15-40 ", "41-50", "51-70", "71-90", "91-150"};
    private DialogListMsg dialogListMsg;
    private boolean isPrepared;// 预加载标志

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_selected, null);
        isPrepared = true;
        setlazyLoad();
        return rootView;
    }
    /**
     * 加载数据的方法，只要保证isPrepared和isVisible都为true的时候才往下执行开始加载数据
     */
    @Override
    protected void setlazyLoad() {
        super.setlazyLoad();

        if (!isPrepared || !isVisible) {
            return;
        }
        if (list_result.size() == 0) {
            initWidget();
            initData();
        }
    }
    @Override
    public void initWidget() {

        mtv1 = (TextView) rootView.findViewById(R.id.mtv1);
        mtv2 = (TextView) rootView.findViewById(R.id.mtv2);
        mtv3 = (TextView) rootView.findViewById(R.id.mtv3);

        mtv1.setOnClickListener(this);
        mtv2.setOnClickListener(this);
        mtv3.setOnClickListener(this);


        metName = (EditText) rootView.findViewById(R.id.metName);
        mtvSearch = (TextView) rootView.findViewById(R.id.mtvSearch);
        mtvSearch.setOnClickListener(this);

        mllNomessage = (LinearLayout) rootView.findViewById(R.id.mllNomessage);
        mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);
        mtvTipMessage = (TextView) rootView.findViewById(R.id.mtvTipMessage);


    }

    @Override
    public void initData() {


        dialogListMsg = new DialogListMsg(getActivity());


        dialogListMsg.show_listview().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (arrFlag.equals("1")) {
                    mtv1.setText(zoomArr[position]);
                } else if (arrFlag.equals("2")) {
                    mtv2.setText(moneyArr[position]);
                } else {
                    mtv3.setText(mianjiArr[position]);
                }

                listMessage(false);
                dialogListMsg.Close();

            }
        });

        dialogListMsg.submit_no().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (arrFlag.equals("1")) {
                    mtv1.setText("地区");
                } if (arrFlag.equals("2")) {
                    mtv2.setText("价格");
                } else {
                    mtv3.setText("面积");
                }
                listMessage(false);
                dialogListMsg.Close();
            }
        });

        listMessage(true);
//        listZoomMsg(false);
        mListMessage.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                if (choiceFlag == 0) {
                    Intent intent = new Intent(getActivity(), HouseMessageActivity.class);
                    intent.putExtra("msg", list_result.get(pos));
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), HouseMessageActivity.class);
                    intent.putExtra("msg", list_result_search.get(pos));
                    getActivity().startActivity(intent);
                }

            }
        });
        metName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                if (s.length() == 0) {
                    listMessage(false);
                } else {
                    listSearchMsg(true, metName.getText().toString());
                }
            }
        });
    }


    private String arrFlag;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mtv1:
                arrFlag = "1";
                dialogListMsg.setTitle().setText("请选择所在区域");
                ChoiceTimeAdapter zoomAdapter = new ChoiceTimeAdapter(getActivity());
                zoomAdapter.setData(zoomArr);
                dialogListMsg.show_listview().setAdapter(zoomAdapter);
                dialogListMsg.Show();
                break;

            case R.id.mtv2:
                arrFlag = "2";
                dialogListMsg.setTitle().setText("请选择价格区间");
                ChoiceTimeAdapter moneyAdapter = new ChoiceTimeAdapter(getActivity());
                moneyAdapter.setData(moneyArr);
                dialogListMsg.show_listview().setAdapter(moneyAdapter);
                dialogListMsg.Show();
                break;
            case R.id.mtv3:
                arrFlag = "3";
                dialogListMsg.setTitle().setText("请选择面积区间");
                ChoiceTimeAdapter mianjiAdapter = new ChoiceTimeAdapter(getActivity());
                mianjiAdapter.setData(mianjiArr);
                dialogListMsg.show_listview().setAdapter(mianjiAdapter);
                dialogListMsg.Show();
                break;
        }
    }

    private void listSearchMsg(boolean isShow, String searchmessage) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "queryMessage");
        params.put("searchmessage", searchmessage);
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    private void listZoomMsg(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listZoom");
        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }


    private void listMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listHourseMessage");


        if(mtv1.getText().toString().equals("地区")) {
            params.put("zoomInfor", "");
        }else {
            params.put("zoomInfor", mtv1.getText().toString());
        }



        if(mtv2.getText().toString().equals("价格")) {
            params.put("moneyStart", "");
            params.put("moneyEnd", "");
        }else {
            params.put("moneyStart", mtv2.getText().toString().split("-")[0]);
            params.put("moneyEnd", mtv2.getText().toString().split("-")[1]);
        }

        if(mtv3.getText().toString().equals("面积")) {
            params.put("mianjiStart", "");
            params.put("mianjiEnd", "");
        }else {
            params.put("mianjiStart", mtv3.getText().toString().split("-")[0]);
            params.put("mianjiEnd", mtv3.getText().toString().split("-")[1]);
        }

        params.put("houseType", "卖房");

        httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }


    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultCode:
                choiceFlag = 0;
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
            case Consts.actionId.resultFlag:
                choiceFlag = 1;
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);

                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_result_search.clear();
                        list_result_search = mGson.fromJson(entry.getData(), new TypeToken<List<HouseModel>>() {
                        }.getType());
                        LookListAdapter noticeAdapter = new LookListAdapter(getActivity(), list_result_search);
                        mListMessage.setAdapter(noticeAdapter);
                    } else {
                        mllNomessage.setVisibility(View.VISIBLE);
                    }
                }
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
        HouseObservable.getInstance().addObserver(HouseMainFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HouseObservable.getInstance().deleteObserver(HouseMainFragment.this);

    }

    @Override
    public void update(Observable observable, Object data) {
        listMessage(false);

    }

}
