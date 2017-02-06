package com.my.abd.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
/**数据库曾删改查类
*/
public class MyDatabase
{
	Context context;
	MySQLite mySqlite;
	public MyDatabase(Context con)
	{
		context=con;
		mySqlite=new MySQLite(context);
	}
	public void NameBaoCun(ContentValues cv)
	{   //增加数据
		SQLiteDatabase db =mySqlite.getWritableDatabase();
		db.insert(MySQLite.TABLE_NAME, null, cv);
		db.close();
	}
	public void NameShanChu(String id)
	{    //删除数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = { id};
		db.delete(MySQLite.TABLE_NAME, where, whereValue);
		db.close();
	}
	public void NameXiuGai(ContentValues cv)
	{   //修改数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = {cv.getAsString("_id")};
		db.update(MySQLite.TABLE_NAME, cv, where, whereValue);
		db.close();
	}
	//修改用户数据
	public void NameXiuGai(String yonghu,String newYonghu)
	{   //修改数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "yonghu=?";
		String[] whereValue = {yonghu};
		ContentValues cv = new ContentValues();
		cv.put("yonghu",newYonghu);
		db.update(MySQLite.TABLE_NAME, cv, where, whereValue);
		db.close();
	}
	//修改项目名称 1需修改的项目名称 2修改后的项目名称
	public void NameXiuGaiA(String xing, String text)
	{   //修改项目
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "xing=?";
		String[] whereValue = {xing};
		ContentValues cv = new ContentValues();
		cv.put("xing", text);
		db.update(MySQLite.TABLE_NAME, cv, where, whereValue);
		db.close();
	}
	public void NameShanChuYongHu(String yonghu)
	{    //删除特定用户的所有数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "yonghu=?";
		String[] whereValue = {yonghu};
		db.delete(MySQLite.TABLE_NAME, where, whereValue);
		db.close();
	}
	public void NameShujuku(String text)
	{    //删除特定项目的所有数据
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "xing=?";
		String[] whereValue = {text};
		db.delete(MySQLite.TABLE_NAME, where, whereValue);
		db.close();
	}
	
	public void AmBaoCun(String text, String text1)
	{   //增加项目和工价
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("xing", text);
		cv.put("gong", text1);
		db.insert(MySQLite.TABLE_am, null, cv);
		db.close();
	}
	public void AmShanChu(String id)
	{    //删除项目 id是项目在数据库的id
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = { id};
		db.delete(MySQLite.TABLE_am, where, whereValue);
		db.close();
	}
	public void AmXiuGai(int id, String text, String text1)
	{   //修改项目和工价1 项目id 2 修改后的项目名称 3修改后的项目工价
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = { String.valueOf(id) };
		ContentValues cv = new ContentValues();
		cv.put("xing", text);
		cv.put("gong", text1);
		db.update(MySQLite.TABLE_am, cv, where, whereValue);
		db.close();
	}
	public void AmXiuGai(int id,String text1)
	{   //修改工价 1 id 2 修改后的工价
		SQLiteDatabase db = mySqlite.getWritableDatabase();
		String where = "_id=?";
		String[] whereValue = { String.valueOf(id) };
		ContentValues cv = new ContentValues();
		cv.put("gong", text1);
		db.update(MySQLite.TABLE_am, cv, where, whereValue);
		db.close();
	}
}
