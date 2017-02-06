package com.my.abd.array;
import java.util.ArrayList;
import com.my.abd.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.my.abd.cun.Cuf;
import com.my.abd.tongji.GongZi;
//明细列表
public class MiXiList extends Fragment implements Runnable
{
	private ListView list;
	private View view;
//	开始日期和结束日期
	private int kai,jie;
//	用户名称
	private String yongfu;
//    线程更新明细列表
	Handler myHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==1)
			{
				MiXiListAdapter mixi=new MiXiListAdapter(getActivity(),array);
				list.setAdapter(mixi);
			}
		}
	};
	//明细数据列表
	private ArrayList<Cuf> array;
    //线程更新数据
	public void run()
	{
		GongZi gongzi = new GongZi(getActivity(), kai, jie,yongfu);
		array = gongzi.getArray();
		myHandler.sendEmptyMessage(1);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.detait_dapter, null);
//		接收传递过来的日期
		Bundle b=getArguments();
//		三个数据，开始日期、结束日期、用户名称
		kai=b.getInt("kai",0);
		jie=b.getInt("jie",0);
		yongfu=b.getString("yong","");
//		列表
		list = (ListView)view.findViewById(R.id.detaitdapterListView1);
//		新建线程
		new Thread(this).start();
		return view;
	}
}
