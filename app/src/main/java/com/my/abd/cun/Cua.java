package com.my.abd.cun;
/**
*基本类
*/
public class Cua
{
	int    id;
	String data;
	String xing;
	double shu;
	double gong;
//	public Cua(int id, String data, String xing, double shu, double gong)
//	{
//		this.id=id;
//		this.data=data;
//		this.xing=xing;
//		this.shu=shu;
//		this.gong=gong;
//	}
	public void setId(int id)
	{
		this.id=id;
	}

	public int getId()
	{
		return id;
	}

	public void setData(String data)
	{
		this.data=data;
	}

	public String getData()
	{
		return data;
	}

	public void setXing(String xing)
	{
		this.xing=xing;
	}

	public String getXing()
	{
		return xing;
	}

	public void setShu(double shu)
	{
		this.shu=shu;
	}

	public double getShu()
	{
		return shu;
	}

	public void setGong(double gong)
	{
		this.gong=this.gong+gong;
	}

	public double getGong()
	{
		return gong;
	}
	public String getGongString()
	{
		return String.valueOf(gong);
	}
}
