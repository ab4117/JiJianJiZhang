package com.my.abd.array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.my.abd.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.my.abd.cun.Cuc;
import com.my.abd.tongji.GongZi;

public class TongJiList extends Fragment implements Runnable
{
	private int kai,jie;
	private String yongfu;
	
	private View view;
	private ListView list;
	//收入 出勤 平均
	private TextView txvShou,txvChu,txvPing;
	private GongZi gongji;
	DecimalFormat df=new DecimalFormat("##0.00");
	Handler myHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==11)
			{
				setAdapter();
			}
		}
	};
	private ArrayList<Cuc> arraycuna;
	public void run()
	{
		gongji = new GongZi(getActivity(),kai,jie,yongfu);		
		arraycuna=gongji.getTongJi();
		myHandler.sendEmptyMessage(11);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Bundle b=getArguments();
		kai=b.getInt("kai",0);
		jie=b.getInt("jie",0);
		yongfu=b.getString("yong","");
		view = inflater.inflate(R.layout.statistics_dapter, null);
		list=(ListView)view.findViewById(R.id.list);
		txvShou=(TextView)view.findViewById(R.id.statisticsdapterTextView1);
		txvChu=(TextView)view.findViewById(R.id.statisticsdapterTextView2);
		txvPing=(TextView)view.findViewById(R.id.statisticsdapterTextView3);
		new Thread(this).start();
		return view;
	}
	//设置数据
	private void setAdapter()
	{
		//列表
		ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		int size=arraycuna.size();
		for (int i = 0; i < size; i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
            Cuc cuna=arraycuna.get(i);
			map.put("PIC", cuna.getXing());		
			map.put("TITLE", cuna.getShu()+"");
			map.put("CONTENT",df.format(cuna.getGong()) + "元");
			mList.add(map);

		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(),
												  mList,
												  R.layout.listitem,		// 自定义布局格式
												  new String[] { "PIC", "TITLE", "CONTENT"}, 
												  new int[] { R.id.listitem_pic, R.id.listitem_title, R.id.listitem_content }
												  );
		list.setAdapter(adapter);
		txvShou.setText(gongji.gongzi()+"元");
		txvChu.setText(gongji.TianShu()+"天");
		txvPing.setText(gongji.PingJun()+"元");
	}
}
