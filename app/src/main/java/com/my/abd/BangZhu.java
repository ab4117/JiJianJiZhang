package com.my.abd;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
/* 作者：天下
*  类的说明：帮助界面
*  创建日期：2017/2/11 21:19
*  修改日期：2017/2/11 21:19
*/
public class BangZhu extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bangzhu);	
		ImageButton butFanHui=(ImageButton)findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(listener);
    }
	//按钮事件
	private OnClickListener listener = new OnClickListener() 
	{
		public void onClick(View v)
		{
			switch (v.getId())
			{				
				case R.id.ImageButton1 :
					finish();
					break;
			}
		}
	};
}
