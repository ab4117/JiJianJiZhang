package com.my.abd.array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.my.abd.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.abd.UpdateActivity;
import com.my.abd.cun.Cuf;
//明细列表数据适配器
public class MiXiListAdapter extends BaseAdapter
{

	private Context context;

	private ArrayList<Cuf> array;

	private LayoutInflater mInflater;
	DecimalFormat df = new DecimalFormat("##0.00");
	public MiXiListAdapter(Context c,ArrayList<Cuf> array)
	{
		this.context = c;
		this.array=array;
		mInflater = LayoutInflater.from(c);	
	}

	// 获取图片的个数
	public int getCount()
	{
		return array.size();
	}

	// 获取图片在库中的位置
	public Object getItem(int position)
	{
		return position;
	}

	// 获取图片ID
	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (array.get(position).getId() == -1)
		{
			convertView = mInflater.inflate(R.layout.listmingxi, null);
			GongZi(convertView, position);
		}
		else
		{
			convertView = mInflater.inflate(R.layout.listmingxi1, null);
			JiLu(convertView, position);
		}
		return convertView;
	}
	//每日工资
	private void GongZi(View v, int p)
	{
		TextView tevData=(TextView)v.findViewById(R.id.listmingxiTextView1);
		tevData.setText(array.get(p).getData());
		TextView tevGong=(TextView)v.findViewById(R.id.listmingxiTextView2);
		tevGong.setText(df.format(array.get(p).getGong()) + "元");
	}
	//每日记录
	private void JiLu(View v, final int p)
	{
		TextView tevXing=(TextView)v.findViewById(R.id.listmingxi1TextView1);
		tevXing.setText(array.get(p).getXing());
		TextView tevShu=(TextView)v.findViewById(R.id.listmingxi1TextView2);
		tevShu.setText(array.get(p).getShu() + "");
		LinearLayout li=(LinearLayout)v.findViewById(R.id.listmingxi1LinearLayout1);
		li.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					Cuf cuf = array.get(p);
					Intent intent  = new Intent();
					intent.setClass(context,UpdateActivity.class);  
					Bundle mBundle = new Bundle();
					mBundle.putInt("id",cuf.getId());
					mBundle.putDouble("shu",cuf.getShu());
					mBundle.putString("data",cuf.getData());
					mBundle.putString("xing",cuf.getXing());
					//压入数据      
					intent.putExtras( mBundle);
					((Activity)context).startActivityForResult(intent,3); 
				}
			});		
	}
}







