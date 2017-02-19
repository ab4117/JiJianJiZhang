package com.my.abd;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.my.abd.files.BeiFen;
import com.my.abd.files.HuanYuan;
/**设置界面
 */
public class SheZhiActivity extends Activity
{
	private ProgressDialog m_pDialog;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			m_pDialog.cancel();
			if(msg.what==1)
			{
				Toast.makeText(getApplication(), "备份成功", Toast.LENGTH_SHORT).show();
			}
			if(msg.what==2)
			{
				Toast.makeText(getApplication(), "还原成功", Toast.LENGTH_SHORT).show();
			}
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState)
	{   
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shezi);
		
		Button but1=(Button) findViewById(R.id.sheziButton1);
		Button but2=(Button) findViewById(R.id.sheziButton2);
		Button but3=(Button) findViewById(R.id.sheziButton3);
		Button but4=(Button) findViewById(R.id.sheziButton4);
		Button but5=(Button) findViewById(R.id.sheziButton5);
		Button but6=(Button) findViewById(R.id.sheziButton6);	
		
		ImageButton butFanHui=(ImageButton)findViewById(R.id.ImageButton1);
		butFanHui.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					finish();
				}
			});
		but1.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					po();//进度条对话框
					BeiFen beifen=new BeiFen(SheZhiActivity.this,mHandler);
					Thread newThread = new Thread(beifen);
					newThread.start();
				}
			});
		but2.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					po();//进度条对话框
					HuanYuan huanyuand=new HuanYuan(SheZhiActivity.this,mHandler);
					Thread	newThread = new Thread(huanyuand);					
					newThread.start();

				}
			});
		but3.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent=new Intent();
					intent.setClass(SheZhiActivity.this, BangZhu.class);
					startActivity(intent);
				}
			});
		but4.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					//发送 Email
					Uri uri = Uri.parse("mailto:779013176@qq.com");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					startActivity(it);
				}
			});
		but5.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent=new Intent();
					intent.setClass(SheZhiActivity.this, GuanYu.class);
					startActivity(intent);
				}
			});
		but6.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent=new Intent();
					intent.setClass(SheZhiActivity.this,UserActivity.class);
					startActivity(intent);
				}
			});
	}

	//进度条
	public void po()
	{
		m_pDialog = new ProgressDialog(this);

		// 设置进度条风格，风格为圆形，旋转的
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		// 设置ProgressDialog 标题
		m_pDialog.setTitle("提示");

		// 设置ProgressDialog 提示信息
		m_pDialog.setMessage("正在加载数据……");

		// 设置ProgressDialog 标题图标
		//	m_pDialog.setIcon(R.drawable.img1);

		// 设置ProgressDialog 的进度条是否不明确
		m_pDialog.setIndeterminate(false);

		// 设置ProgressDialog 是否可以按退回按键取消
		m_pDialog.setCancelable(false);

		// 让ProgressDialog显示
		m_pDialog.show();

	}

}
