package com.github.xzzpig.exmctool.event;
import com.github.xzzpig.exmctool.clients.*;

import org.bukkit.event.*;

public class ChatDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();

	private Client_Chatable pclient;

	public ChatDataReachEvent(Client_Chatable c,byte[] data) {
		super(c,data);
		this.pclient = c;
	}

	public Client_Chatable getChatableClient(){
		return pclient;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
