package com.github.xzzpig.exmctool.extevent;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.event.*;

public class DataReachEvent extends Event
{
	private Client client;
	private byte[] data;

	public DataReachEvent(Client c,byte[] data) {
		this.client = c;
		this.data = data;
	}

	public byte[] getData(){
		return data;
	}
	public String getStringData(){
		return new String(data);
	}

	public Client getClient(){
		return client;
	}
}
