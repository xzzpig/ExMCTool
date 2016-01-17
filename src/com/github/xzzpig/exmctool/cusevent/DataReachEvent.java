package com.github.xzzpig.exmctool.cusevent;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.utils.*;
import java.io.*;

public class DataReachEvent extends Event
{
	private byte[] data;
	public DataReachEvent(byte[] data){
		this.data = data;
	}
	
	public byte[] getData(){
		return data;
	}
	public String getStringData(){
		try{
			return new String(data,"utf-8");
		}
		catch(UnsupportedEncodingException e){}
		return new String(data);
	}
	public Object getObjectData(){
		return ObjectFile.load(data);
	}
	
}
