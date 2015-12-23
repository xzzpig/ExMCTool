package com.github.xzzpig.exmctool.event;

import org.bukkit.event.*;

import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.cilents.Cilent;

public class DataReachEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private Cilent cilent;
	private byte[] data;
	
	public DataReachEvent(Cilent c,byte[] data) {
		this.cilent = c;
		this.data = data;
	}
	
	public byte[] getData(){
		return data;
	}
	public String getStringData(){
		return new String(data);
	}
	
	public Cilent getCilent(){
		return cilent;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
