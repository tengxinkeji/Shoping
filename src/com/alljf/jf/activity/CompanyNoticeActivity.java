package com.alljf.jf.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.Application.SysApplication;
import com.adapter.CompanyNoticeAdapter;
import com.alljf.jf.R;
import com.bean.CompanyNoticeBean;
import com.example.sportsdialogdemo.dialog.SpotsDialog;
import com.jsonParser.CompanyNoticeJsonPaser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class CompanyNoticeActivity extends Activity {
	
	private ListView mListview;
	List<CompanyNoticeBean> list;
	private TextView mback,mhome;
	/**
	 * 进度条
	 */
	private SpotsDialog mdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_companynotice);
		SysApplication.getInstance().addActivity(this);

		initview();
		//准备数据源
		GetData();
		
	}
	private void GetData() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		// 只包含字符串参数时默认使用BodyParamsEntity，
		params.addBodyParameter("id", "8d7d8ee069cb0cbbf816bbb65d56947e");
		params.addBodyParameter("key", "71d1dd35b75718a722bae7068bdb3e1a");
		params.addBodyParameter("type", "article");
		params.addBodyParameter("part", "article_list_nokey");
		params.addBodyParameter("type_id", "1");
		params.addBodyParameter("limit", "20");
		params.addBodyParameter("limit_start", "1");
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
		        	String str=responseInfo.result;
		        	Log.i("公司公告网络请求下来的参数是",str);
		        	list=CompanyNoticeJsonPaser.GetBean(str);
		        	CompanyNoticeAdapter adapter=new CompanyNoticeAdapter(list, CompanyNoticeActivity.this);
		        	mListview.setAdapter(adapter);
		        	mListview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							//进行跳转，需要带上商品id
							Bundle bundle=new Bundle();
							bundle.putString("id",list.get(arg2).getArticle_id());
							Intent intent=new Intent(CompanyNoticeActivity.this,DynamicDetailsActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
		        	
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
	}
	private void initview() {
		// TODO Auto-generated method stub
		SpotsDialog.TAG=R.style.SpotsDialogDefault;
		mdialog=new SpotsDialog(CompanyNoticeActivity.this);
		mdialog.setCanceledOnTouchOutside(false);
		mListview=(ListView) findViewById(R.id.companynotice_listview);
		mback=(TextView) findViewById(R.id.companynotice_top_textview_back);
		mback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CompanyNoticeActivity.this.finish();
			}
		});
		mhome=(TextView) findViewById(R.id.companynotice_top_textview_home);
		mhome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CompanyNoticeActivity.this.finish();
				Intent intent=new Intent(CompanyNoticeActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}

}
