package com.my.abd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.my.abd.array.ArrayAmSQL;
import com.my.abd.cun.Cud;
import com.my.abd.sql.MyDatabase;

/**项目界面类
 */
public class TypeActivity extends Activity implements OnClickListener
{
	private ListView listView;
	private MyDatabase myDatabase;

	private ArrayList<Cud> array;
	private ArrayAmSQL myArrayAm;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mulist);
		listView=(ListView) findViewById(R.id.mulistListView1);
		myDatabase=new MyDatabase(this);
		list();

		Button btnZengJia=(Button) findViewById(R.id.mulistButton1);
		btnZengJia.setOnClickListener(this);
		ImageButton butFanHui=(ImageButton)findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(this);
	}
	//按钮事件
	public void onClick(View p1)
	{
		switch(p1.getId())
		{
			case R.id.mulistButton1:
				Intent intent2=new Intent();
				intent2.setClass(TypeActivity.this,AddTypeActivity.class);
				startActivityForResult(intent2,2);
				break;
			case R.id.ImageButton1:
				finish();
				break;
		}
	}
	//列表
	public void list()
	{
		myArrayAm=new ArrayAmSQL(this);
		array=myArrayAm.getArray();
		ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for(int i = 0; i<array.size(); i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();		
			map.put("xing",array.get(i).getXing());
			map.put("gong",array.get(i).getGong()+" (元/小时)");
			mList.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this,
												  mList,
												  R.layout.list,
												  new String[]{"xing", "gong" }, 
												  new int[]{ R.id.listitem_pic, R.id.listitem_title });
		listView.setAdapter(adapter);

        //列表点击事件
		listView.setOnItemClickListener(new OnItemClickListener()
			{

				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					DianJiXiangMu(position);
				}
			});
		//列表长点击事件
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
			{

				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
				{
					YiDongXiangMu(position);
					return true;
				}
			});
	}
	//点击项目
	private void DianJiXiangMu(final int p)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(TypeActivity.this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textView = factory.inflate(R.layout.shuru2,null);
		//builder.setIcon(R.drawable.qq);
		builder.setTitle("项目修改");
		builder.setView(textView);
		final EditText ediXiangMu=(EditText)textView.findViewById(R.id.shuru2EditText1);
		ediXiangMu.setText(array.get(p).getXing());
		final EditText ediGongJia=(EditText)textView.findViewById(R.id.shuru2EditText2);
		ediGongJia.setText(""+array.get(p).getGong());
		builder.setPositiveButton("取消",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

				}
			});
		builder.setNeutralButton("修改",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					String strXiangMu=ediXiangMu.getText().toString();
					String strGongJia=ediGongJia.getText().toString();
					if (strXiangMu.equals("") || (strGongJia.equals("")))
					{
						TiShi("请输入项目名称或工价");
					}
					else
					{
						myDatabase.AmXiuGai(array.get(p).getId(),strXiangMu,strGongJia);
						myDatabase.NameXiuGaiA(array.get(p).getXing(),strXiangMu);
						TiShi("更改成功");
					}
					list();
				}
			});
		builder.setNegativeButton("删除",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					ShanChuXiangMu(String.valueOf((array.get(p).getId())),array.get(p).getXing());
				}
			});
		builder.show();
	}

	/**
	 * 删除项目
	 * @param id 项目在数据库的id
	 * @param xiangmu 项目名称
     */
	private void ShanChuXiangMu(final String id, final String xiangmu)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(TypeActivity.this);
		builder.setTitle("提示");
		builder.setMessage("删除\""+xiangmu+"\"项目所有数据");
		builder.setPositiveButton("是",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					myDatabase.NameShujuku(xiangmu);
					myDatabase.AmShanChu(id);
					list();
					Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
				}
			});
		builder.setNegativeButton("否",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{

				}
			});
		builder.show();
	}	
	//移动项目
	private void YiDongXiangMu(final int p)
	{
		String[] strList ={"项目上移","项目下移"};
		AlertDialog.Builder builder = new AlertDialog.Builder(TypeActivity.this);
		builder.setTitle("请选择");
		builder.setItems(strList,new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int item)
				{				
					switch(item)
					{
						case 0:
							ShangYi(p);
							break;
						case 1:
							XiaYi(p);
							break;
					}
					list();
				}

			});
		builder.show();
	}
	//项目上移
    public void ShangYi(int p)	
	{		
		if(p>0)
		{
			Cud yuan=array.get(p);
			int id1=yuan.getId();
			String xing1=yuan.getXing();
			String gong1 =yuan.getGongString();
			Cud xiu=array.get(p-1);
			int id2=xiu.getId();
			String xing2=xiu.getXing();
			String gong2 =xiu.getGongString();
			myDatabase.AmXiuGai(id2,xing1,gong1);
			myDatabase.AmXiuGai(id1,xing2,gong2);
		}
	}
	//项目下移
	public void XiaYi(int p)	
	{
        int i=array.size()-1;		
		if(p<i)
		{
			Cud yuan=array.get(p);
			int id1=yuan.getId();
			String xing1=yuan.getXing();
			String gong1 =yuan.getGongString();
			Cud xiu=array.get(p+1);
			int id2=xiu.getId();
			String xing2=xiu.getXing();
			String gong2 =xiu.getGongString();
			myDatabase.AmXiuGai(id2,xing1,gong1);
			myDatabase.AmXiuGai(id1,xing2,gong2);
		}
	}
	//回调函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		list();
	}
	public void TiShi(String str)
	{
		Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
	}
}
