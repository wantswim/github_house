package com.pony.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pony.config.Consts;
import com.pony.listview.IsSendListner;
import com.pony.model.HouseModel;
import com.rental.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopSendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<HouseModel> list_result;
    private int posIndex;
    private Context mContext;
    private IsSendListner misSendListner;

    public ShopSendAdapter(Context context, List<HouseModel> list_result, IsSendListner isSendListner) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.list_result = list_result;
        this.misSendListner = isSendListner;
    }

    @Override
    public int getCount() {
        return list_result.size();
    }

    @Override
    public Object getItem(int position) {
        return list_result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.looksend_item, null);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
            holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
            holder.mtvTime = (TextView) convertView.findViewById(R.id.mtvTime);
            holder.mivShop = (ImageView) convertView.findViewById(R.id.mivShop);
            holder.mtvState = (TextView) convertView.findViewById(R.id.mtvState);
            holder.mtvUpdate = (TextView) convertView.findViewById(R.id.mtvUpdate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.mTvTitle.setText(list_result.get(position).getHouseName());
            holder.mTvMoney.setText("房源价位："+list_result.get(position).getHouseMoney()+"元/月");
            holder.mtvTime.setText("房源户型："+list_result.get(position).getHouseCategoryName());

            holder.mtvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    misSendListner.setIsSend(position, list_result.get(position));
                }
            });

            holder.mtvUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    misSendListner.setIsUpdate(position, list_result.get(position));
                }
            });


            if (!TextUtils.isEmpty(list_result.get(position).getHouseImage())) {
                Picasso.with(mContext).load(Consts.URL_IMAGE_LOCAL + list_result.get(position).getHouseImage())
                        .placeholder(R.drawable.default_drawable_show_pictrue).into(holder.mivShop);
            }


        } catch (Exception e) {
        }

        return convertView;

    }

    private class ViewHolder {
        private TextView mTvTitle, mtvState, mtvUpdate;
        private TextView mTvMoney;
        private TextView mtvTime;
        private ImageView mivShop;
    }

    public void setPos(int pos) {
        posIndex = pos;
    }

}
