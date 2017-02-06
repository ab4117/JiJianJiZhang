package com.my.abd.files;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.my.abd.sql.MyDatabase;
import com.my.abd.sql.MySQLite;
import com.my.abd.sql.UserSql;
/**数据还原类

 */
public class HuanYuan implements Runnable
{
	private Context context;
	private Handler mHandler;
	private MySQLite mysqlite;
	private MyDatabase mydatabase;

	private UserSql myuser;	
	//2f数据保存路径 3 Handler对象
    public HuanYuan(Context c, Handler h)
	{
		this.context = c;
		this.mHandler = h;
		mysqlite = new MySQLite(context);
		mydatabase = new MyDatabase(context);
		myuser = new UserSql(context);
	}
	public void run()
	{		
		try
		{
			Files mFiles = new Files(context);
			shanchubiao();
			String an=mFiles.ReadFile();
			Log.e("eee",an);
			JSONObject obj=new JSONObject(an);
			//记录
			JSONArray jilu=obj.getJSONArray("记录");
			for (int i=0;;i++)
			{
				if (!jilu.isNull(i))
				{
					JSONArray json=jilu.getJSONArray(i);
					ContentValues cv = new ContentValues();
					cv.put("data", json.getString(0));
					cv.put("xing", json.getString(1));
					cv.put("shu", json.getString(2));
					cv.put("riqi", json.getInt(4));
					cv.put("yonghu", json.getString(3));
					mydatabase.NameBaoCun(cv);
				}
				else
				{
					break;
				}
			}
			//项目
			JSONArray xiangmu=obj.getJSONArray("项目");
			for (int i=0;;i++)
			{
				if (!xiangmu.isNull(i))
				{
					JSONArray json=xiangmu.getJSONArray(i);
					mydatabase.AmBaoCun(json.getString(0), json.getString(1));
				}
				else
				{
					break;
				}
			}
			//用户
			JSONArray yonghu=obj.getJSONArray("用户");
			for (int i=0;;i++)
			{
				if (!yonghu.isNull(i))
				{
					JSONArray json=yonghu.getJSONArray(i);
					Log.e("eee",jilu.toString());
					myuser.save(json.getString(0));
				}
				else
				{
					break;
				}
			}
		}
		catch (Exception e)
		{
			//Toast.makeText(getApplication(), "还原错误", Toast.LENGTH_SHORT).show();
		}
		mHandler.sendEmptyMessage(2);
		//Toast.makeText(getApplication(), "还原成功", Toast.LENGTH_SHORT).show();

	}
	public void shanchubiao()
	{
		SQLiteDatabase db = mysqlite.getWritableDatabase();
		db.execSQL("DROP TABLE " + MySQLite.TABLE_NAME);
		db.execSQL("DROP TABLE " + MySQLite.TABLE_am);
		db.execSQL("DROP TABLE " + MySQLite.TABLE_USER);
		//新建表
		String sql = "CREATE TABLE " + MySQLite.TABLE_am + " (_id INTEGER primary key autoincrement,xing text,gong text)";
		db.execSQL(sql);
		String sql1 = "CREATE TABLE " + MySQLite.TABLE_NAME + " (_id INTEGER primary key autoincrement,data text,xing text,shu text,yonghu text,riqi integer)";
		db.execSQL(sql1);
		String sql2 = "CREATE TABLE " + MySQLite.TABLE_USER + " (_id INTEGER primary key autoincrement,yonghu text)";
		db.execSQL(sql2);
		db.close();
	}
}
