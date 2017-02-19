package com.my.abd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
/* 作者：天下
*  类的说明：关于界面
*  创建日期：2017/2/10 22:57
*  修改日期：2017/2/10 22:57
*/
public class GuanYu extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guanyu);				
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
