package com.my.abd;

import android.app.*;
import android.content.*;
import android.net.Uri;
import android.view.*;
import android.widget.*;

import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.my.abd.array.ArrayAmSQL;
import com.my.abd.sql.MyDatabase;

/**
 * 增加修改账目界面
 */
public class AddTypeActivity extends Activity {
	private EditText xianmu, gongjia;
	private MyDatabase mydatabase;
	private ArrayAmSQL myarrayam;
	
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhangmu);
		xianmu = (EditText) findViewById(R.id.zhangmuEditText1);
		gongjia = (EditText) findViewById(R.id.zhangmuEditText2);
		mydatabase = new MyDatabase(this);
		myarrayam = new ArrayAmSQL(this);

		//保存按键
		Button but1 = (Button) findViewById(R.id.zhangmuButton1);
		but1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String strXiangMu = xianmu.getText().toString();
				String strGongJia = gongjia.getText().toString();
				if (strXiangMu.equals("") || (strGongJia.equals(""))) {
					TiShi("请输入");
				} else {
					if (myarrayam.isXing(strXiangMu)) {
						TiShi("项目已经存在");
					} else {
						mydatabase.AmBaoCun(strXiangMu, strGongJia);
						TiShi("保存成功");
						TiaoZhuan();
					}
				}
			}
		});
		ImageButton butFanHui = (ImageButton) findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	public void TiaoZhuan() {
		Intent intent = new Intent();
		intent.setClass(AddTypeActivity.this, TypeActivity.class);
		setResult(2, intent);
		finish();
	}

	public void TiShi(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW,
				"AddType Page",
				Uri.parse("http://host/path"),
				Uri.parse("android-app://com.my.abd/http/host/path"));
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"AddType Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.my.abd/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}
