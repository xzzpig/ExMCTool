package com.github.xzzpig.exmctool.event;
import org.bukkit.event.*;

import com.github.xzzpig.exmctool.clients.Client_Player;

public class PlayerDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();
	
	private Client_Player pcilent;
	
	public PlayerDataReachEvent(Client_Player c,byte[] data) {
		super(c,data);
		this.pcilent = c;
	}
	
	public Client_Player getPlayerCilent(){
		return pcilent;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
