package com.my.abd.tongji;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;

import com.my.abd.array.ArrayAmSQL;
import com.my.abd.array.ArraySQL;
import com.my.abd.cun.Cub;
import com.my.abd.cun.Cuc;
import com.my.abd.cun.Cud;
import com.my.abd.cun.Cuf;
/**工资计算类
 */
public class GongZi
{
	//指定时间的记录
	ArrayList<Cub> array;
	//每个项目统计
	ArrayList<Cuc> arraycuc;
	ArrayAmSQL myarrayam;
	ArraySQL myarray;
	Context context;
	DecimalFormat df = new DecimalFormat("##0.00");

	public GongZi(Context c, int kai, int jie,String yongfu)
	{
		context = c;
		myarray = new ArraySQL(context);
		myarrayam = new ArrayAmSQL(context);
		//指定时间的记录
		array = myarray.getArray(kai, jie,yongfu);
		JiSuan(kai, jie,yongfu);
	}
	//每个项目工资统计
	private void JiSuan(int kai, int jie,String yonghu)
	{        //项目数组		
		ArrayList<Cud> arrayam=myarrayam.getArray();
		//统计项目数组
		arraycuc = new ArrayList<Cuc>();
		for (int i=0;i < arrayam.size();i++)
		{   //循环获取单项目
			String xing=arrayam.get(i).getXing();
			Cuc cuc=myarray.getTongJi(kai, jie, xing,yonghu);	
			arraycuc.add(cuc);
		}
	}
	//项目统计列表
	public ArrayList<Cuc> getTongJi()
	{
		return arraycuc;
	}
	//总工资 减去福利扣费
	public String gongzi()
	{
		double dou_gongzi=0.0;
		for (int i=0;i < arraycuc.size();i++)
		{
			dou_gongzi = dou_gongzi + arraycuc.get(i).getGong();
		}
		return df.format(dou_gongzi);
	}
	//平均
	public String PingJun()
	{
		if (TianShu() == 0)
		{
			return "0";
		}
		else
		{
			return df.format(Double.parseDouble(gongzi()) / ((double)TianShu()));
		}
	}
	//天数 
	public int TianShu()
	{ 
		String mdata=""; 
		int tianShu=0;
		for (int i = 0; i < array.size(); i++)
		{   
		    String data=array.get(i).getData();
			if (!mdata.equals(data))
			{      
				mdata = data;
				tianShu++;
			}    
		}     
		return tianShu;
	} 
	/**
	 *明细列表数组
	 *算法：日期相同数据存放在临时数组，日期不同时将临时数组复制到明细列表数组。
	 */
	public ArrayList<Cuf> getArray()
	{   
		String strData="";
		/**
		 * 明细列表
		 */
		ArrayList<Cuf> arraylist=new ArrayList<Cuf>();
		//临时存放
		ArrayList<Cuf> marraylist=new ArrayList<Cuf>();
		int size=array.size();
		for (int i = 0; i < size; i++)
		{ 
		    int    id=array.get(i).getId();
		    String data=array.get(i).getData();//日期
			double shu=array.get(i).getShu();//数量
			String xing=array.get(i).getXing();//型号		
			double gong= myarrayam.getGongZi(xing, shu);//工资
			Cuf cuf=new Cuf(id, data, xing, shu);
			if (!strData.equals(data))
			{   //日期不相等 
				strData = data; 
				//复制临时元素到主列表1明细列表2临时列表
				CopyArray(arraylist, marraylist);
                //每日工资
				Cuf cufa=new Cuf(data, gong);
				arraylist.add(cufa);			
			} 
			else
			{    
				//每日工资累加
				arraylist.get((arraylist.size()) - 1).setGong(gong);
			}
			//数据存放在临时数组
			marraylist.add(cuf);
		} 
		//最后将临时数组复制到明细列表数组
		CopyArray(arraylist, marraylist);
		return arraylist;
	} 
	//数组复制方法 1存储复制的数组 2需要复制的数组
	public void CopyArray(ArrayList<Cuf> arraylist, ArrayList<Cuf> marraylist)
	{
		int size=marraylist.size();
		if (size > 0)
		{
			for (int i=0;i < size;i++)
			{
				arraylist.add(marraylist.get(i));
			}
			//移除所有临时元素
			marraylist.clear();
		}
	}
}
