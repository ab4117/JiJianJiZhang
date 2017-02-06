package com.my.abd.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**数据库
 */
public class MySQLite extends SQLiteOpenHelper
{
	public final static String DATABASE_NAME = "todo_db";//数据库
	public final static int DATABASE_VERSION = 1;
	public final static String TABLE_NAME = "todo_table";//表名
	public final static String TABLE_am = "todo_am";//表名
	public final static String TABLE_USER = "todo_user";//用户表名

	public MySQLite(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db)
	{

		String sql = "CREATE TABLE " + TABLE_am + " (_id INTEGER primary key autoincrement,xing text,gong text)";
		db.execSQL(sql);
		String sql1 = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER primary key autoincrement,data text,xing text,shu text,yonghu text,riqi integer)";
		db.execSQL(sql1);
		db.execSQL("insert into todo_am (xing,gong) values ('项目','0.1')");
		String sql2 = "CREATE TABLE " + TABLE_USER + " (_id INTEGER primary key autoincrement,yonghu text)";
		db.execSQL(sql2);
		db.execSQL("insert into todo_user (yonghu) values ('张三')");
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//如果表存在就删除
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
}
