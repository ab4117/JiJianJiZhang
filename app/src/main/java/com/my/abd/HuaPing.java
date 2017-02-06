package com.my.abd;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.my.abd.array.PaddleAdapter;
import com.my.abd.shijian.MyDate;
import com.my.abd.sql.UserSql;
public class HuaPing extends FragmentActivity
{   
	private Button btnDate1,btnDate2,btnDate3;
	private ViewPager pager;
	private PagerTabStrip pagerTabStrip;
	//日期
    private MyDate myDate1,myDate2;

	private PaddleAdapter paddle;

	private Spinner userSpinner;

	private String[] strYongHu;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huaping);
//		LinearLayout linear=(LinearLayout)findViewById(R.id.huapingLinearLayout1);
//		App.app(HuaPing.this,linear);
//		//调用方式3： 传入2个R.drawable资源，第二个为自定义轮盘收起按钮；
//        //收起按钮 会缩放到： (屏幕窄边)/6 * (屏幕窄边)/4, 即宽高比：2:3
//		IconsAd iconsAd=new IconsAd(this,new int[]{R.drawable.music, R.drawable.music_close });
//		iconsAd.loadAd(this);
		
		myDate1 = new MyDate(1);
		myDate2 = new MyDate();
		//日期1和2
		btnDate1 = (Button)findViewById(R.id.huapingButton1);
		btnDate1.setOnClickListener(listener);		
		btnDate2 = (Button)findViewById(R.id.huapingButton2);
		btnDate2.setOnClickListener(listener);
		//查询
		btnDate3 = (Button)findViewById(R.id.huapingButton3);
		btnDate3.setOnClickListener(listener);
		//用户名称数组
		UserSql myuser=new UserSql(this);
		strYongHu=myuser.getStringList();
		//用户下拉列表
		userSpinner=(Spinner) findViewById(R.id.huapingSpinner1);
		ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this, R.layout.mspinner,strYongHu);
		userAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		userSpinner.setAdapter(userAdapter);
		//划屏控件
		pager = (ViewPager) findViewById(R.id.viewpager);
	    paddle=new PaddleAdapter(getSupportFragmentManager(),HuaPing.this,myDate1.getInt(),myDate2.getInt(),strYongHu[userSpinner.getSelectedItemPosition()]);
        pager.setAdapter(paddle);
		//划屏标题
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab); 
		pagerTabStrip.setTabIndicatorColor(Color.YELLOW);  
        pagerTabStrip.setDrawFullUnderline(false); 
		// pagerTabStrip.setBackgroundColor(Color.GREEN); 
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		if(width!=0)
		{
			pagerTabStrip.setTextSpacing(width/4);
		}else
		{
			pagerTabStrip.setTextSpacing(100);
		}       
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		setText();
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		//ShuanXin();
	}
	//刷新明细 统计
	public void ShuanXin()
	{
		String yong_hu=strYongHu[userSpinner.getSelectedItemPosition()];	
		paddle.ShuaXin(myDate1.getInt(),myDate2.getInt(),yong_hu);
	}
	private OnClickListener listener = new OnClickListener() 
	{
		public void onClick(View v)
		{
			switch (v.getId())
			{
				//初始日期
				case R.id.huapingButton1:
					int[] date=myDate1.getShiJian();
					new DatePickerDialog(HuaPing.this,
						new DatePickerDialog.OnDateSetListener()
						{
							public void onDateSet(DatePicker view, int nian, int yue, int ri)
							{
								myDate1.setShiJian(nian, yue, ri);
								setText();
							}
						}, date[0] , date[1], date[2]).show();
					break;
			    //结束日期
				case R.id.huapingButton2:
					int[] date1=myDate2.getShiJian();
					new DatePickerDialog(HuaPing.this,
						new DatePickerDialog.OnDateSetListener()
						{
							public void onDateSet(DatePicker view, int nian, int yue, int ri)
							{
								myDate2.setShiJian(nian, yue, ri);
								setText();
							}
						}, date1[0] , date1[1], date1[2]).show();
					break;
				//查询
				case R.id.huapingButton3:
					ShuanXin();
					break;
			}
		}
	};
	
	//更新日期控件字符显示
	private void setText()
	{
		//日期时间时间比较
		if (myDate1.getInt() > myDate2.getInt())
		{
			int[] idate1=myDate1.getShiJian();
			int[] idate2=myDate2.getShiJian();
			myDate1.setShiJian(idate2);
			myDate2.setShiJian(idate1);
		}
		btnDate1.setText(myDate1.getString());
		btnDate2.setText(myDate2.getString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		ShuanXin();
	}
}

