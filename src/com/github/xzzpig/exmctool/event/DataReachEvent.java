package com.github.xzzpig.exmctool.event;

import org.bukkit.event.*;

import com.github.xzzpig.exmctool.clients.Client;
import com.github.xzzpig.exmctool.clients.*;

public class DataReachEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
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
	public Client getBasicClient(){
		for(Client c:Client.clients)
			if(c.isType(ClientType.Basic))
				return c;
		return null;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
