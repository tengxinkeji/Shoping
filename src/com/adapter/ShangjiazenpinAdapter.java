package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alljf.jf.R;
import com.bean.ShangjiazengpingBean;
import com.lidroid.xutils.BitmapUtils;

public class ShangjiazenpinAdapter extends BaseAdapter {
	
	private List<ShangjiazengpingBean> list;
	private Context context;
	
	public ShangjiazenpinAdapter(List<ShangjiazengpingBean> list,Context context){
		
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
		ShangjiazengpingBean bean=list.get(position);
		viewholder vh=null;
		if(convertView==null){
			vh=new viewholder();
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.shangjiazengpin_listview_item, null);
			vh.title=(TextView) convertView.findViewById(R.id.shangjiazenpin_title);
			vh.im=(ImageView) convertView.findViewById(R.id.shangjiazengpin_image);
			vh.price=(TextView) convertView.findViewById(R.id.shangjiazenpin_jiage);
			vh.weight=(TextView) convertView.findViewById(R.id.shangjiazenppin_zhongliang);
			vh.cb=(ImageView) convertView.findViewById(R.id.shangjiazenpin_tubiao);
			convertView.setTag(vh);
		}else{
			vh=(viewholder) convertView.getTag();
		}
		vh.title.setText(list.get(position).getGoods_name());
		vh.price.setText("价格"+"￥:"+list.get(position).getGoods_price());
		vh.weight.setText("重量"+":"+list.get(position).getGoods_weight());
		if(list.get(position).isIschecked()){
			vh.cb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));
		}else{
			vh.cb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
		}
		BitmapUtils bm=new BitmapUtils(context);
		bm.display(vh.im,list.get(position).getGoods_image());
		
		return convertView;
	}
	
	
	static class viewholder{
		TextView title;
		ImageView im;
		TextView price;
		TextView weight;
		ImageView cb;
	}
	

}
