package com.my.abd.files;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
/**file文件保存读取
 */
public class Files
{
	//目录路径
	private File sdCardDir=new File(Environment.getExternalStorageDirectory(), "GongZi");
	//文件路径
	private File saveFile=new File(sdCardDir, "保存.json");
	//文件IO类
	private FileOutputStream  outStream;
	private FileInputStream inStream;
    
	private Context context;
	public Files(Context c)
	{
		context = c;
		//目录不存在就新建
		if (!sdCardDir.exists()) sdCardDir.mkdir();
	}
	//保存 1需要保存的字符串
	public void WriteFile(String txt) throws Exception
	{
		//得到用户输入字符
		String Text_of_input = txt;
		outStream = new FileOutputStream(saveFile,false);
		//把内容写入文件
		outStream.write(Text_of_input.getBytes());			
		//关闭文件输出流
		outStream.close();
	}
    //读取
	public String ReadFile() throws FileNotFoundException, IOException
	{
		int len;
		//然后创建一个字节数组输出流
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String str="";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			//创建  初始化 流对象
			inStream = new FileInputStream(saveFile);
			while ((len = inStream.read(buffer)) != -1)
			{
				ostream.write(buffer, 0, len);
			}
			str = new String(ostream.toByteArray());						
		}
		return str;
	}
}
