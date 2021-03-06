package com.alljf.jf.activity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Application.SysApplication;
import com.adapter.BuyCartAdapter;
import com.alljf.jf.R;
import com.bean.BuyCartBean;
import com.example.sportsdialogdemo.dialog.SpotsDialog;
import com.jsonParser.BuyCartJsonP;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.other.InternerIsConnection;
import com.other.NetReceiver;

public class BuyActivity extends Activity implements OnClickListener{

	private TextView mBack;//返回按钮
	private TextView mHome;//主页按钮
	private ImageView mNameXuanDing;//选定名称
	private TextView mChange;//编辑
	private TextView mCName;//公司名称
	private ListView mListview;
	private TextView mQuanxuan;//全选
	private TextView mTotalPrice;//总价
	private TextView mJiesuan;//结算
	private List<BuyCartBean> list=new ArrayList<BuyCartBean>();
	
	private String CallBackString;//网络请求回的数据
	BuyCartAdapter adapter;
	private CheckBox im_quanzuantubiao;
	
	private boolean flag = true;		//全选或全取消
	/**
	 * 进度条
	 */
	private SpotsDialog mdialog;
	/**
	 * 网络管理对象
	 */
	private boolean isconnection;
	private ConnectivityManager manager;//网络管理器对象
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy);
		SysApplication.getInstance().addActivity(this);
		initview();
		isconnecions();
		initmanager();
		if(isconnection){
			getdata();
		}else{
			Toast.makeText(BuyActivity.this,"请检查网络设置", 1).show();
		}
		
		
		mChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mChange.getText().equals("编辑")){
					mChange.setText("完成");
					mNameXuanDing.setBackgroundResource(R.drawable.quanquan);
					//在这里第二步，通知listview发生改变
					BuyCartAdapter.isshow();
					adapter.notifyDataSetChanged();
					//全选按钮需要显示出来
					mQuanxuan.setVisibility(View.VISIBLE);
					im_quanzuantubiao.setVisibility(View.VISIBLE);
					
				}else{
					mChange.setText("编辑");
					mNameXuanDing.setBackgroundResource(R.drawable.gougou);
					BuyCartAdapter.unshow();
					adapter.notifyDataSetChanged();
					mQuanxuan.setVisibility(View.GONE);
					im_quanzuantubiao.setVisibility(View.GONE);
				}
				
			}
		});
		
	}
	/**
	 * @author JZKJ-LWC
	 * @date : 2015-12-27 下午9:49:45
	 * 用于实时检测网络是否连接
	 */  
	private void isconnecions() {
		// TODO Auto-generated method stub
		NetReceiver mReceiver = new NetReceiver();
	    IntentFilter mFilter = new IntentFilter();
		 mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}
	/**
	 * @author JZKJ-LWC
	 * @date : 2015-12-27 下午9:11:36
	 * 初始化网络管理对象
	 */  
	private void initmanager() {
		// TODO Auto-generated method stub
		manager =(ConnectivityManager) BuyActivity.this.getSystemService(BuyActivity.this.CONNECTIVITY_SERVICE);//获得网络连接的管理者对象
		isconnection=InternerIsConnection.network(manager,BuyActivity.this);
	}
	//获取用户名
	private String getUserName() {
			return SysApplication.getInstance().getUserInfo().getName();
			}
	//获取数据
	private void getdata() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		// 只包含字符串参数时默认使用BodyParamsEntity，
		params.addBodyParameter("id", "8d7d8ee069cb0cbbf816bbb65d56947e");
		params.addBodyParameter("key", "71d1dd35b75718a722bae7068bdb3e1a");
		params.addBodyParameter("type", "order");
		params.addBodyParameter("part", "cart_list_nokey");
		params.addBodyParameter("user_name",getUserName());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,"http://www.91jf.com/api.php",params,new RequestCallBack<String>() {

		        @Override
		        public void onStart() {
		        	//开始请求
		        	mdialog.show();
		        }

		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		            if (isUploading) {
		            } else {
		            }
		        }
		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		        	//请求成功
		        	mdialog.dismiss();
		        	CallBackString=responseInfo.result;
		        	Log.i("解析前网络请求有数据吗？",CallBackString);
		        	list=BuyCartJsonP.getlist(CallBackString);
		        	//准备适配器
		        	//解析
		    		adapter=new BuyCartAdapter(list, BuyActivity.this,mTotalPrice);
		        	mListview.setAdapter(adapter);
		        	mListview.setEmptyView(findViewById(R.id.layout_empty));
		        	TextView tv=(TextView) findViewById(R.id.empty_add);
		        	tv.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent=new Intent(BuyActivity.this,AllProductActivity.class);
							startActivity(intent);
						}
					});
		        	mChange.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub
		    				if(mChange.getText().equals("编辑")){
		    					mChange.setText("完成");
		    					mNameXuanDing.setBackgroundResource(R.drawable.quanquan);
		    					//在这里第二步，通知listview发生改变
		    					BuyCartAdapter.isshow();
		    					adapter.notifyDataSetChanged();
		    					//全选按钮需要显示出来
		    					mQuanxuan.setVisibility(View.VISIBLE);
		    					im_quanzuantubiao.setVisibility(View.VISIBLE);
		    					
		    				}else{
		    					double total=0;
			    				List<BuyCartBean> listss=new ArrayList<BuyCartBean>();
			    				for(int i=0;i<list.size();i++){
			    					if(BuyCartAdapter.getIsSelected().get(i)){
			    						listss.add(list.get(i));
			    						double price=Double.parseDouble(list.get(i).getGoods_price());
			    						double num=Double.parseDouble(list.get(i).getGoods_num());
			    						total=total+price*num;
			    						Log.i("现在价格是多少liss",total+"");
			    					}
			    				}
			    				Log.i("现在总价格是多少liss",total+"");
			    				mTotalPrice.setText(total+"");
		    					mChange.setText("编辑");
		    					mNameXuanDing.setBackgroundResource(R.drawable.gougou);
		    					BuyCartAdapter.unshow();
		    					adapter.notifyDataSetChanged();
		    					mQuanxuan.setVisibility(View.GONE);
		    					im_quanzuantubiao.setVisibility(View.GONE);
		    				}
		    				
		    			}
		    		});
		        	//全选或全取消
		        	im_quanzuantubiao.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							for(int i=0;i<list.size();i++){
				    			BuyCartAdapter.getIsSelected().put(i, flag);
				    		}
				    		adapter.notifyDataSetChanged();
				    		flag=!flag;
						}
					});
		        	
		        	mJiesuan.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub
		    				List<BuyCartBean> listss=new ArrayList<BuyCartBean>();
		    				for(int i=0;i<list.size();i++){
		    					if(BuyCartAdapter.getIsSelected().get(i)){
		    						listss.add(list.get(i));
		    					}
		    				}
		    				if(mChange.getText().toString().equals("编辑")&&listss.size()>0){
		    					//首先，这里需要拿到购物车中被选中的商品的数据
			    				
			    				Log.i("现在拿到选定的数据了吗liss",listss.size()+"");
			    				//跳转到结算界面
			    				Intent intent=new Intent(BuyActivity.this,PayMoneyActivity.class);
			    				intent.putExtra("price", mTotalPrice.getText().toString());
			    				intent.putExtra("list", (Serializable)listss);
			    				startActivity(intent);
			    			}else{
			    				Toast.makeText(BuyActivity.this,"请先完成商品选择",1).show();
			    			}
		    				}
		    				
		    		});
		        	
		        }
		      

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
	}


	/**
	 * @author JZKJ-LWC
	 * @date : 2015-12-7 下午1:49:29
	 * 
	 */
	public void initview(){
		SpotsDialog.TAG=R.style.SpotsDialogDefault;
		mdialog=new SpotsDialog(BuyActivity.this);
		mdialog.setCanceledOnTouchOutside(false);
		mBack=(TextView) findViewById(R.id.buyactivity_top_textview_back);
		mHome=(TextView) findViewById(R.id.buyactivity_top_textview_home);
		mNameXuanDing=(ImageView) findViewById(R.id.buyactivity_imageview_gongsimingchengxuanding);
		mChange=(TextView) findViewById(R.id.buyactivity_textview_change);
		mCName=(TextView) findViewById(R.id.buyactivity_textview_gongsimingcheng);
		mListview=(ListView) findViewById(R.id.butactivity_listview);
		mQuanxuan=(TextView) findViewById(R.id.buyactivity_radiobutton_quanxuan);
		mTotalPrice=(TextView) findViewById(R.id.buyactivity_textview_totalprice);
		mJiesuan=(TextView) findViewById(R.id.buyacttivity_textview_jiesuan);
		im_quanzuantubiao=(CheckBox) findViewById(R.id.image_quanxuan);
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		mHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BuyActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	//定义一个方法，在adapter中，当radiobuutton的状态发生改变的时候，都调用一次该方法，并且将该item的价格传递过来
	public static void setdata(double d){
		
	}
	
	private long mExitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "再点一次，退出程序",
							Toast.LENGTH_SHORT).show();
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
					SysApplication.getInstance().exit();
				}
			}
		return true;
	
	}


}
