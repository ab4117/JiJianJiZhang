package com.my.abd;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
/**菜单界面
 */
public class MainActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState)
	{           
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.caidan);		
//		记录，明细，项目，设置四个按钮
		Button btnJiLu =(Button) findViewById(R.id.but1);
		Button btnMingXi =(Button) findViewById(R.id.but2);
		Button btnXingXu =(Button) findViewById(R.id.but3);
		Button btnSheZhi =(Button) findViewById(R.id.but4);
		btnJiLu.setOnClickListener(listener);
		btnMingXi.setOnClickListener(listener);
		btnXingXu.setOnClickListener(listener);
		btnSheZhi.setOnClickListener(listener);
//		返回按钮
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

				case R.id.but1:
//					跳转记录界面
					Intent(MainActivity.this,InputActivity.class);
//					overridePendingTransition(android.R.anim.slide_in_left,android. R.anim.slide_out_right);					
					break;
				case R.id.but2:
//					跳转明细界面
					Intent(MainActivity.this,HuaPing.class);
					break;
				case R.id.but3:
//					跳转添加项目界面
					Intent(MainActivity.this, TypeActivity.class);
					break;
				case R.id.but4:
//					跳转设置界面
					Intent(MainActivity.this, InstallActivity.class);
					break;
				case R.id.ImageButton1 :
//					返回
					finish();
					break;
			}
		}
	};
	//退出
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{   
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{  
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
				exitTime = System.currentTimeMillis();   
			}
			else
			{

				System.exit(0);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}
	//activity跳转 1 Context 2class
	public void Intent(Context c,java.lang.Class<?> cls)
	{
		Intent intent1 = new Intent();
		intent1.setClass(c, cls);
		startActivity(intent1);
	}
	//退出
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

	}
}

