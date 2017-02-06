package com.my.abd.files;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.my.abd.sql.MySQLite;
//数据备份类
public class BeiFen implements Runnable
{
	private Context context;
	private Handler mHandler;	
	//2f数据保存路径 3 Handler对象
    public BeiFen(Context c, Handler h)
	{
		this.context = c;
		this.mHandler = h;
	}
	public void run()
	{	
		Files mFiles = new Files(context);
		MySQLite mysqlite = new MySQLite(context);
		SQLiteDatabase db = mysqlite.getWritableDatabase();
		try
		{
			JSONObject obj=new JSONObject();
			//记录
			JSONArray jilu=getJiLu(db);
			obj.put("记录", jilu);
			//项目
			JSONArray xiangmu=getXiangMu(db);
			obj.put("项目",xiangmu);	
			//用户
			JSONArray yonghu=getYongHu(db);
			obj.put("用户",yonghu);	
            //保存
			mFiles.WriteFile(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//关闭对话框
		mHandler.sendEmptyMessage(1);
	}
	//记录的json
	private JSONArray getJiLu(SQLiteDatabase db)
	{
		JSONArray json=new JSONArray();
		Cursor cursor = db.query(MySQLite.TABLE_NAME, null, null, null, null, null, null);
		try
		{
			if (cursor.moveToFirst())
			{   
				for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) 
				{
					JSONArray jilu=new JSONArray();
					jilu.put(cursor.getString(1));// + "," + cursor.getString(2) + "," + cursor.getString(3) + "," +cursor.getString(4)+"," + cursor.getInt(5) + "#";
					jilu.put(cursor.getString(2));
					jilu.put(cursor.getString(3));
					jilu.put(cursor.getString(4));
					jilu.put(cursor.getString(5));
					json.put(jilu);
				}
			}
		}
		catch (Exception e)
		{}
		return json;
	}
	//项目的json
	private JSONArray getXiangMu(SQLiteDatabase db)
	{
		JSONArray json=new JSONArray();
		Cursor cursorAm=db.query(MySQLite.TABLE_am, null, null, null, null, null, null);
		try
		{
			if (cursorAm.moveToFirst())
			{   
				for (cursorAm.moveToFirst();!(cursorAm.isAfterLast());cursorAm.moveToNext()) 
				{
					//zhifu = zhifu + cursorAm.getString(1) + "," + cursorAm.getString(2) + "#";
					JSONArray xiangmu=new JSONArray();
					xiangmu.put(cursorAm.getString(1));
					xiangmu.put(cursorAm.getString(2));
					json.put(xiangmu);			
				}
			}
		}
		catch (Exception e)
		{}	
		return json;
	}
	//用户的json
	private JSONArray getYongHu(SQLiteDatabase db)
	{
		JSONArray json=new JSONArray();
		Cursor cursorUser=db.query(MySQLite.TABLE_USER, null, null, null, null, null, null);	
		try
		{
			if (cursorUser.moveToFirst())
			{   
				for (cursorUser.moveToFirst();!(cursorUser.isAfterLast());cursorUser.moveToNext()) 
				{
					JSONArray yonghu=new JSONArray();
					yonghu.put(cursorUser.getString(1));
					json.put(yonghu);
				}
			}
		}
		catch (Exception e)
		{}	
		return json;
	}
}
