package com.my.abd.sql;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.my.abd.cun.Cup;
/**用户数据库曾删改查类
 */
public class UserSql
{
	Context context;
	MySQLite mySqlite;
	public UserSql(Context con)
	{
		context=con;
		mySqlite=new MySQLite(context);
	}

	/**
	 * 方法作用：保存用户名
	 * @param yonghu 用户名称
	 */
	public void save(String yonghu)
	{   
		SQLiteDatabase db =mySqlite.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("yonghu",yonghu);
		db.insert(MySQLite.TABLE_USER, null, cv);
		db.close();
	}
	public void ShanChu(String id)
	{    //删除数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = {id};
		db.delete(MySQLite.TABLE_USER, where, whereValue);
		db.close();
	}
	public void XiuGai(String id,String yonghu)
	{   //修改数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = {id};
		ContentValues cv = new ContentValues();
		cv.put("yonghu",yonghu);
		db.update(MySQLite.TABLE_USER, cv, where, whereValue);
		db.close();
	}
	public String[] getStringList()
	{
		ArrayList<Cup> list=getArray();
		String[] str=new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			str[i]=list.get(i).getData();
		}
		return str;
	}
	//返回指定数据的数组
	public ArrayList<Cup> getArray()
	{
		SQLiteDatabase db =mySqlite.getWritableDatabase();
		Cursor cursor = db.query(mySqlite.TABLE_USER,null,null,null, null, null,null);
	    ArrayList<Cup> array=new ArrayList<Cup>();
		try
		{
			if (cursor.moveToFirst())
			{   

				for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) 
				{
					Cup cup=new Cup();
					int id=cursor.getInt(0);
					String user=cursor.getString(1);
					cup.setId(id);
					cup.setData(user);
					array.add(cup);
				}
			}
		}
		catch (Exception e)
		{}
		db.close();
		cursor.close();
		return array;
	}
}
