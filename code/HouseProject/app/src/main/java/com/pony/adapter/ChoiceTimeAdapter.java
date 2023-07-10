package com.pony.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pony.model.CategoryModel;
import com.rental.R;

import java.util.List;

public class ChoiceTimeAdapter extends BaseAdapter {
	private Context mContext;
	private String[] list_result;

	public ChoiceTimeAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public ChoiceTimeAdapter(Context mContext, String[] list_msg) {
		super();
		this.mContext = mContext;
		this.list_result = list_msg;
		notifyDataSetChanged();
	}

	public String[] setData(String[] list_str) {
		return list_result = list_str;

	}

	@Override
	public int getCount() {
		return list_result.length;
	}

	@Override
	public Object getItem(int position) {
		return list_result.length;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.add_item, null);
			vh = new ViewHolder();
			vh.zoom_msg = (TextView) convertView.findViewById(R.id.zoom_msg);

			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.zoom_msg.setText(list_result[position]);
		return convertView;
	}

	class ViewHolder {
		TextView zoom_msg;
	}
}