package com.my.abd.array;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.my.abd.HuaPing;
/**
 *划屏适配器
 */
public class PaddleAdapter extends FragmentStatePagerAdapter
{
	//标题列表
	private ArrayList<String> titleList;
	private HuaPing huaping;
//日期格式化类
	DecimalFormat df = new DecimalFormat("##0.00");
//	开始和结束日期
    private int kai,jie;
// 用户名
	private String yonghu;
	
	private Context context;
	public PaddleAdapter(FragmentManager fm, Context context, int kai, int jie, String yonghu)
	{
		super(fm);
		this.context = context;
		this.kai = kai;
		this.jie = jie;
		this.yonghu = yonghu;
		huaping = (HuaPing)context;
		// 每个页面的Title数据  
		titleList = new ArrayList<String>();
        titleList.add("明细");  
        titleList.add("统计");
	}
	@Override  
	public int getCount()
	{  
		return titleList.size();  
	}

	@Override
	public Fragment getItem(int p1)
	{
		Bundle b=new Bundle();
		Fragment ment=null;
		switch (p1)
		{
			case 0:
				ment = new MiXiList();
				break;
		    case 1:
				ment = new TongJiList();
				break;
		}
		b.putInt("kai", kai);
		b.putInt("jie", jie);
		b.putString("yong", yonghu);
		ment.setArguments(b);
		return ment;
	}
	@Override  
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;  
	}  

	@Override  
	public CharSequence getPageTitle(int position)
	{  
        //直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。  
		return titleList.get(position);

	}  
//
	@Override  
	public Object instantiateItem(ViewGroup container, int position)
	{
		return super.instantiateItem(container, position);  
	}
	//刷新数据
	public void ShuaXin(int kai, int jie, String yonghu)
	{
		this.kai = kai;
		this.jie = jie;
		this.yonghu = yonghu;
		notifyDataSetChanged();
	}
}
