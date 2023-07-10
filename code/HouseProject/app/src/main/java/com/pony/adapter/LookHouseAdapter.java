package com.pony.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pony.model.LeaveHouseModel;
import com.pony.model.NoticeModel;
import com.rental.R;

public class LookHouseAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<LeaveHouseModel> list_result;
	private int posIndex;
	private Context mContext;

	public LookHouseAdapter(Context context, List<LeaveHouseModel> list_result) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		this.list_result = list_result;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_message_look, null);
			holder = new ViewHolder();
			holder.lookHouseUserName = (TextView) convertView.findViewById(R.id.lookHouseUserName);
			holder.lookHouseTime = (TextView) convertView.findViewById(R.id.lookHouseTime);
			holder.lookHouseTypeName = (TextView) convertView.findViewById(R.id.lookHouseTypeName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.lookHouseTime.setText("发布时间：" + list_result.get(position).getLookHouseTime());
		holder.lookHouseTypeName.setText(list_result.get(position).getLookHouseTypeName());
		holder.lookHouseUserName.setText(list_result.get(position).getLookHouseUserName()+"("+list_result.get(position).getLookHouseMoney()+"元/月)");
		return convertView;

	}

	private class ViewHolder {
		private TextView lookHouseUserName;
		private TextView lookHouseTime;
		private TextView lookHouseTypeName;
	}

	public void setPos(int pos) {
		posIndex = pos;
	}

}
