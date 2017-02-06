package com.my.abd.shijian;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**封装记录时间
*/
public class MyDate
{
	private int[] int_shijian=new int[3];
    public final static int KAISHI=1;
	public final static int JIESHU=2;
	Calendar calendar;
	//格式化日期字符串
	DateFormat format1 = new SimpleDateFormat(  
		"yyyy-MM-dd EE");
	//格式化日期字符串
	DateFormat format2 = new SimpleDateFormat(  
		"yyyyMMdd");
	public MyDate()
	{
		//获取日期
		calendar=Calendar.getInstance();
	}
	public MyDate(int i)
	{
		//获取日期
		calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,i);
	}
	//设置日期
	public void setShiJian(int a,int b,int c)
	{	
		calendar.set(Calendar.YEAR,a);
		calendar.set(Calendar.MONTH,b);
		calendar.set(Calendar.DAY_OF_MONTH,c);
	}
	//设置日期
	public void setShiJian(int[] a)
	{
		calendar.set(Calendar.YEAR,a[0]);
		calendar.set(Calendar.MONTH,a[1]);
		calendar.set(Calendar.DAY_OF_MONTH,a[2]);
		
	}
	//返回日期数组
	public int[] getShiJian()
	{
		int_shijian[0]=calendar.get(Calendar.YEAR);
		int_shijian[1] = calendar.get(Calendar.MONTH);
		int_shijian[2] = calendar.get(Calendar.DAY_OF_MONTH);
		return int_shijian;
	}

	//返回日期字符串
	public String getString()
	{		 
        return format1.format(calendar.getTime()); 		
	}
	//返回日期int值
	public int getInt()
	{  
        return Integer.parseInt(format2.format(calendar.getTime())); 						
	}
	//获取Calendar
	public Calendar getCalendar()
	{
		return calendar;
	}
}
