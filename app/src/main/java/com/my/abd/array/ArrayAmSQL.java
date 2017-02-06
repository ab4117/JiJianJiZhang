package com.my.abd.array;
import com.my.abd.cun.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.my.abd.sql.MySQLite;
import java.util.ArrayList;
/**项目数据数组
*/
public class ArrayAmSQL
{
	Context context;
	MySQLite mySqlite;
	private ArrayList<Cud> array;
	public ArrayAmSQL(Context con)
	{
		context=con;
		mySqlite=new MySQLite(context);	
		SQLiteDatabase db =mySqlite.getWritableDatabase();
		Cursor cursor = db.query(mySqlite.TABLE_am, null, null, null, null, null, null);		
		//获取项目数据
		array=new ArrayList<Cud>();
		try
		{
			if (cursor.moveToFirst())
			{   
				for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) 
				{
					int id=cursor.getInt(0);
					String xing=cursor.getString(1);
					double gongjia=Double.parseDouble(cursor.getString(2));
					Cud cud=new Cud(id,xing,gongjia);
					array.add(cud);
				}
			}
		}
		catch (Exception e)
		{}
		db.close();
		cursor.close();
	}
	//项目数组
	public ArrayList<Cud> getArray()
	{
		return array;
	}
	//查找项目 1 需要查找的项目S
	public boolean isXing(String s)
	{
		boolean is=false;
		for(int i=0;array.size()>i;i++)
		{
			//判断项目是否存在
			if(s.equals(array.get(i).getXing()))
			{
				is=true;
				break;
			}
		}
		return is;
	}
	//获项目工资计算， 1 项目 2 项目的数量
	public double getGongZi(String s,double d)
	{
		double gong=0;
		for(int i=0;i<array.size();i++)
		{	//获取工价
			if(s.equals(array.get(i).getXing()))
			{
				gong=array.get(i).getGong();
				break;
			}
		}
		return gong*d;
	}
	//项目名称数组
	public String[] getString()
	{
		String[] strXing=new String[array.size()];
		for(int i=0;array.size()>i;i++)
		{
			strXing[i]=array.get(i).getXing();
		}
		return strXing;
	}	
}
