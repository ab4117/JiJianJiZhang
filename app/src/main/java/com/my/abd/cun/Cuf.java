package com.my.abd.cun;
/**
*明细用
*变量：id data xing shu gong
*/
public class Cuf extends Cua
{
	public Cuf(int id, String data, String xing, double shu)
	{
		this.id=id;
		this.data=data;
		this.xing=xing;
		this.shu=shu;
	}
	public Cuf(String data,double gong)
	{
		this.id=-1;
		this.data=data;
		this.gong=gong;
	}
}
