package com.my.abd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.my.abd.array.ArrayAmSQL;
import com.my.abd.shijian.MyDate;
import com.my.abd.sql.MyDatabase;
import com.my.abd.sql.UserSql;

/**
 * 记录界面
 */
public class JiLuActivity extends Activity {
	static final int DATE_DIALOG_ID = 0;

	private MyDate mydate;
	private ArrayAmSQL myarrayam;
	private MyDatabase mydatabase;
	private Button date;
	private Spinner spinner;
	private EditText edi_shuru;
	private String[] xing;
	private Spinner userSpinner;
	private String[] strYongHu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jishu);
//		LinearLayout linear = (LinearLayout) findViewById(R.id.jishuLinearLayout2);

		mydatabase = new MyDatabase(this);
		mydate = new MyDate();// 时间封装类
		myarrayam = new ArrayAmSQL(this);// 项目封装类
		// 数量输入框
		edi_shuru = (EditText) findViewById(R.id.jishuEditText1);
		// 保存和记录和日期 按键
		Button baocun = (Button) findViewById(R.id.jishuButton2);
		Button jilu = (Button) findViewById(R.id.jishuButton3);
		date = (Button) findViewById(R.id.jishuButton1);
		date.setText(mydate.getString());

		baocun.setOnClickListener(listener);
		date.setOnClickListener(listener);
		jilu.setOnClickListener(listener);
		ImageButton butFanHui = (ImageButton) findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(listener);

		// 项目下拉列表
		spinner = (Spinner) findViewById(R.id.jishuSpinner1);
		xing = myarrayam.getString();// 下拉列表数组
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.mspinner, xing);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);
		// 获取用户数组数据
		UserSql myuser = new UserSql(this);
		strYongHu = myuser.getStringList();
		// 用户下拉列表
		userSpinner = (Spinner) findViewById(R.id.jishuSpinner2);
		ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this,
				R.layout.mspinner, strYongHu);
		userAdapter
				.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		userSpinner.setAdapter(userAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	// 按钮事件
	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.jishuButton1:
				dateDialog();
				break;
			case R.id.jishuButton2:
				save();
				TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
						1000);
				animation.setDuration(1000);
				v.startAnimation(animation);

				break;
			case R.id.jishuButton3:
				Intent intent = new Intent();
				Bundle mBundle = new Bundle();
				intent.setClass(JiLuActivity.this, HuaPing.class);
				mBundle.putIntArray("riqi", mydate.getShiJian());
				mBundle.putIntArray("riqi1", mydate.getShiJian());
				// 压入数据
				intent.putExtras(mBundle);
				startActivity(intent);
				break;
			case R.id.ImageButton1:
				finish();
				break;
			}
		}
	};

	// 保存
	public void save() {
		String dateString = date.getText().toString();
		String shuString = edi_shuru.getText().toString();
		String xingString = xing[spinner.getSelectedItemPosition()];
		String yong_huString = strYongHu[userSpinner.getSelectedItemPosition()];

		if (!shuString.equals("")) {
			ContentValues cv = new ContentValues();
			cv.put("data", dateString);
			cv.put("xing", xingString);
			cv.put("shu", shuString);
			cv.put("riqi", mydate.getInt());
			cv.put("yonghu", yong_huString);
			mydatabase.NameBaoCun(cv);
			Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT)
					.show();
			edi_shuru.setText("");
		} else {
			Toast.makeText(getApplicationContext(), "请输入数量", Toast.LENGTH_SHORT)
					.show();
		}
	}
//	日期控件
	public void dateDialog(){
		int[] int_shijian = mydate.getShiJian();
		new DatePickerDialog(JiLuActivity.this,
				mDateSetListener, int_shijian[0],
				int_shijian[1], int_shijian[2]).show();
	}

	// 日期控件接口实现类
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mydate.setShiJian(year, monthOfYear, dayOfMonth);
			date.setText(mydate.getString());
		}
	};
}
