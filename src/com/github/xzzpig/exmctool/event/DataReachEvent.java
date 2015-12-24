package com.github.xzzpig.exmctool.event;

import org.bukkit.event.*;

import com.github.xzzpig.exmctool.clients.Client;

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
	
	public Client getCilent(){
		return client;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
