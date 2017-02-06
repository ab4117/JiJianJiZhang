package com.my.abd.array;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.my.abd.cun.Cub;
import com.my.abd.cun.Cuc;
import com.my.abd.sql.MySQLite;
/**读取指定时间的数据类
*/
public class ArraySQL
{
	Context context;
	MySQLite mySqlite;
	public ArraySQL(Context con)
	{
		context=con;
		mySqlite=new MySQLite(context);			
	}
	//返回指定数据的数组
	public ArrayList<Cub> getArray(int kai,int jie,String yonghu)
	{
		SQLiteDatabase db =mySqlite.getWritableDatabase();
		Cursor cursor = db.query(mySqlite.TABLE_NAME, new String[]{"_id","data","xing","shu","riqi"}, "riqi>=? AND riqi<=? AND yonghu==?", new String[]{String.valueOf(kai),String.valueOf(jie),yonghu}, null, null, "riqi ASC");
	    ArrayList<Cub> array=new ArrayList<Cub>();
		try
		{
			if (cursor.moveToFirst())
			{   

				for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) 
				{
					int id=cursor.getInt(0);
					String data=cursor.getString(1);
					String xing=cursor.getString(2);
					double shu=Double.parseDouble(cursor.getString(3));
//					int riqi=cursor.getInt(4);
					Cub cunb=new Cub(id,data,xing,shu);
					array.add(cunb);
				}
			}
		}
		catch (Exception e)
		{}
		db.close();
		cursor.close();
		return array;
	}
	//单项目统计
	public Cuc getTongJi(int kai,int jie,String xing,String yonghu)
	{
		SQLiteDatabase db =mySqlite.getWritableDatabase();
    	Cursor cursor = db.query(mySqlite.TABLE_NAME, new String[]{"_id","data","xing","shu","riqi"}, "riqi>=? AND riqi<=? AND xing==? AND yonghu==?", new String[]{String.valueOf(kai),String.valueOf(jie),xing,yonghu}, null, null, "riqi ASC");
		double dshu=0.0;
		try
		{
			if (cursor.moveToFirst())
			{  
				for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) 
				{
					dshu=dshu+Double.parseDouble(cursor.getString(3));
				}			
			}
		}
		catch (Exception e)
		{}
		db.close();
		cursor.close();
		ArrayAmSQL myarrayam=new ArrayAmSQL(context);
		double dGong= myarrayam.getGongZi(xing,dshu);
		Cuc cuns=new Cuc(xing,dshu,dGong);
		return cuns;
	}
}
