package com.github.xzzpig.exmctool.event;
import org.bukkit.event.*;

import com.github.xzzpig.exmctool.clients.Client_Player;

public class PlayerDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();
	
	private Client_Player pclient;
	
	public PlayerDataReachEvent(Client_Player c,byte[] data) {
		super(c,data);
		this.pclient = c;
	}
	
	public Client_Player getPlayerClient(){
		return pclient;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
