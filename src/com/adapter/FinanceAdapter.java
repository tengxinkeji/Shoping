package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alljf.jf.R;
import com.bean.FinanceBean;

public class FinanceAdapter extends BaseAdapter {
	
	private List<FinanceBean> list;
	private Context context;
	
	public FinanceAdapter(List<FinanceBean> list,Context context){
		
		this.list=list;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FinanceBean bean=list.get(position);
		viewholder vh=null;
		if(convertView==null){
			vh=new viewholder();
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.finan_item, null);
			vh.title=(TextView) convertView.findViewById(R.id.f_item_state);
			vh.price=(TextView) convertView.findViewById(R.id.f_item_money);
			vh.content=(TextView) convertView.findViewById(R.id.f_item_content);
			vh.time=(TextView) convertView.findViewById(R.id.f_item_time);
			convertView.setTag(vh);
		}else{
			vh=(viewholder) convertView.getTag();
		}
		
		vh.time.setText(bean.getLog_time());
		vh.title.setText(bean.getLog_type());
		vh.price.setText(bean.getLog_price());
		vh.content.setText(bean.getLog_typename());
		
		return convertView;
	}
	
	
	static class viewholder{
		TextView title;
		TextView time;
		TextView price;
		TextView content;
	}
	

}
