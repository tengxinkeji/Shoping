package com.jf.storeapp.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.Application.SysApplication;
import com.adapter.TuiHuoAdapter;
import com.bean.TuikuanSHBean;
import com.jf.storeapp.R;
import com.jsonParser.TuiHuoJsonpaser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2016-1-3 下午8:45:58 
 * 类说明   退款与售后界面
 */
public class TuiKuanYuSHActivity extends Activity {
	private ImageView mBack;
	private ImageView mHome;
	private ListView mListview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.antivity_tuikuanshouhou);
		initview();
		initdata();
	}

	/**
	 * @2016-1-3下午8:50:28
	 * 初始化数据
	 */
	private void initdata() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		// 只包含字符串参数时默认使用BodyParamsEntity，
		params.addBodyParameter("id", "8d7d8ee069cb0cbbf816bbb65d56947e");
		params.addBodyParameter("key", "71d1dd35b75718a722bae7068bdb3e1a");
		params.addBodyParameter("type", "order");
		params.addBodyParameter("part", "refund_list_nokey");
		params.addBodyParameter("user_name", SysApplication.getInstance().getUserInfo().getName());
		params.addBodyParameter("limit", "100");
		params.addBodyParameter("limit_start", "0");
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,"http://www.91jf.com/api.php",params,new RequestCallBack<String>() {

		        @Override
		        public void onStart() {
		        	//开始请求
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
		        	String str=responseInfo.result;
		        	Log.i("tixian请求下来的参数是",str);
		        	List<TuikuanSHBean> list=TuiHuoJsonpaser.getlist(str);
		        	Log.i("现在有数据吗",list.size()+"");
		        	TuiHuoAdapter adapter=new TuiHuoAdapter(list, TuiKuanYuSHActivity.this);
		        	mListview.setAdapter(adapter);
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
	}

	/**
	 * @2016-1-3下午8:47:55
	 * 初始化
	 */
	private void initview() {
		// TODO Auto-generated method stub
		mBack=(ImageView) findViewById(R.id.shouhoutuikuan_back);
		mHome=(ImageView) findViewById(R.id.shouhoutuikuan_home);
		mListview=(ListView) findViewById(R.id.tuikuanshouhou_listview);
	}

}
