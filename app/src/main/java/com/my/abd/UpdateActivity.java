package com.my.abd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
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
/**修改记录界面
 */
public class UpdateActivity extends Activity
{
	private  MyDate     mydate;
	private  ArrayAmSQL  myarrayam;
    private  MyDatabase mydatabase;
	private  Button     btnDate;    
	private  Spinner    spinner;
	private EditText edi_shuru;
	private String[] XiangMu;

	private String data,xing;
	private int id;
	private double shu;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{   
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xiugai);
//		LinearLayout linear=(LinearLayout)findViewById(R.id.xiugaiLinearLayout1);
		
		Bundle bundle = getIntent( ).getExtras( );
		id=bundle.getInt("id");
		shu=bundle.getDouble("shu");
		xing=bundle.getString("xing");
		data = bundle.getString("data") ; 
		mydatabase=new MyDatabase(this);
		
		String[] strData=data.split("-");
		int[] intData=new int[3];
		intData[0]=Integer.parseInt(strData[0]);
		intData[1]=Integer.parseInt(strData[1]);
		String[] strData1=strData[2].split(" ");
		intData[2]=Integer.parseInt(strData1[0]);
		intData[1]=intData[1]-1;
		mydate=new MyDate();//时间封装类
		mydate.setShiJian(intData);
		myarrayam=new ArrayAmSQL(this);//项目封装类		
		//数量输入框
		edi_shuru=(EditText)findViewById(R.id.xiugaiEditText1);
		edi_shuru.setText(""+shu);
		//保存和记录和日期 按键
		Button btnBaoCun = (Button)findViewById(R.id.xiugaiButton3);
		Button btnShanChu = (Button)findViewById(R.id.xiugaiButton2);
		btnDate=(Button) findViewById(R.id.xiugaiButton1);		
		btnDate.setText(mydate.getString());				

		btnBaoCun.setOnClickListener(listener);
		btnDate.setOnClickListener(listener);
        btnShanChu.setOnClickListener(listener);
		ImageButton butFanHui=(ImageButton)findViewById(R.id.xiugaiImageButton1);
		butFanHui.setOnClickListener(listener);

		//项目下拉列表
		spinner=(Spinner) findViewById(R.id.xiugaiSpinner1);
		XiangMu=myarrayam.getString();//下拉列表数组		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mspinner,XiangMu);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(xing));
    }
	@Override
	public void onStart()
	{
		super.onStart();
	}
	//按钮事件
	private OnClickListener listener = new OnClickListener()
	{
		public void onClick(View v)
		{
			switch(v.getId())
			{
				case R.id.xiugaiButton1:
					dateDialog();
					break;
				case R.id.xiugaiButton3:
					baocun();
					TranslateAnimation animation = new TranslateAnimation(0,0,0,1000);
					animation.setDuration(1000);
					v.startAnimation(animation);
					setResult(3);
					break;
				case R.id.xiugaiButton2:
					mydatabase.NameShanChu(""+id);	
					setResult(3);
					finish();
					break;
				case R.id.xiugaiImageButton1:
					setResult(3);
					break;
			}			
		}
	};
	//保存
	public void baocun()
	{
//		获取日期、数量、项目
		String sDate =btnDate.getText().toString();
		String sShuRu =edi_shuru.getText().toString();
		String sXiangMu =XiangMu[spinner.getSelectedItemPosition()];

		if(!sShuRu.equals(""))
		{
//			保存数据到数据库
			ContentValues cv = new ContentValues();
			cv.put("_id",id);
			cv.put("shu", sShuRu);
			cv.put("xing", sXiangMu);
			cv.put("data", sDate);
			cv.put("riqi",mydate.getInt());
			mydatabase.NameXiuGai(cv);
			Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
			finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(),"请输入数量",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 日期选择控件
	 */
	private void dateDialog()
	{
		int[] int_shijian=mydate.getShiJian();
		new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				mydate.setShiJian(year,monthOfYear,dayOfMonth);
				btnDate.setText(mydate.getString());
			}
		},int_shijian[0],int_shijian[1],int_shijian[2]).show();
	}
}
