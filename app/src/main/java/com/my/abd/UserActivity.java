package com.my.abd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.my.abd.cun.Cup;
import com.my.abd.sql.MyDatabase;
import com.my.abd.sql.UserSql;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *    用户新增界面
 */
public class UserActivity extends Activity implements OnClickListener 
{

	private ListView listView;
	private String[] strYongHu;
	private MyDatabase myDatabase;

	private UserSql myuser;

	private ArrayList<Cup> listUser;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userlist);
		myDatabase=new MyDatabase(this);
		myuser=new UserSql(this);
		listView = (ListView) findViewById(R.id.userlistListView1);
		list();

		Button btnZengJia=(Button) findViewById(R.id.userlistButton1);
		btnZengJia.setOnClickListener(this);
		ImageButton butFanHui=(ImageButton)findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(this);
	}
	//按钮事件
	public void onClick(View p1)
	{
		switch (p1.getId())
		{
			case R.id.userlistButton1:
				addUser();
				break;
			case R.id.ImageButton1:
				finish();
				break;
		}
	}
	//列表
	public void list()
	{
		strYongHu=myuser.getStringList();
		listUser=myuser.getArray();
		ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < strYongHu.length; i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();		
			map.put("yonghu",strYongHu[i]);
			mList.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this,
												  mList,
												  R.layout.list,
												  new String[]{"yonghu",}, 
												  new int[]{ R.id.listitem_pic});
		listView.setAdapter(adapter);

        //列表点击事件
		listView.setOnItemClickListener(new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					updataUser(strYongHu[position],listUser.get(position).getId());
				}
			});
		//列表长点击事件
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
			{
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
				{
					//YiDongXiangMu(position);
					return true;
				}
			});
	}
	//修改用户
	private void updataUser(final String name,final int id)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textView = factory.inflate(R.layout.shuru1, null);
		//builder.setIcon(R.drawable.qq);
		builder.setTitle("编辑用户");
		builder.setView(textView);
		final EditText ediUser=(EditText)textView.findViewById(R.id.shuruEditText1);
		ediUser.setText(name);
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

				}
			});
		builder.setNeutralButton("删除",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					myDatabase.NameShanChuYongHu(name);
					myuser.ShanChu(""+id);
					list();
				}
			});
		builder.setNegativeButton("修改", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					String s=ediUser.getText().toString();
					if (s.length()>=1)
					{
						myDatabase.NameXiuGai(name,s);
						myuser.XiuGai(""+id,s);
						list();
					}
				}
			});
		builder.show();
	}
	//新增用户
	private void addUser()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textView = factory.inflate(R.layout.shuru1, null);
		//builder.setIcon(R.drawable.qq);
		builder.setTitle("新增用户");
		builder.setView(textView);
		final EditText ediUser=(EditText)textView.findViewById(R.id.shuruEditText1);
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

				}
			});
//		builder.setNeutralButton("修改",new DialogInterface.OnClickListener()
//			{
//				public void onClick(DialogInterface dialog, int whichButton)
//				{
//					String strXiangMu=ediXiangMu.getText().toString();
//					String strGongJia=ediGongJia.getText().toString();
//					if (strXiangMu.equals("") || (strGongJia.equals("")))
//					{
//						TiShi("请输入");											
//					}
//					else
//					{
//						myDatabase.AmXiuGai(array.get(p).getId(),strXiangMu,strGongJia);
//						myDatabase.NameXiuGaiA(array.get(p).getXing(),strXiangMu);
//						TiShi("更改成功");
//					}
//					list();
//				}
//			});
		builder.setNegativeButton("新增", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					String name=ediUser.getText().toString();
					if (name.length()>=1)
					{
						myuser.save(name);
						list();
					}
				}
			});
		builder.show();
	}
}
