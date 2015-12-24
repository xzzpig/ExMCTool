package com.github.xzzpig.exmctool.event;
import com.github.xzzpig.exmctool.clients.*;

import org.bukkit.event.*;

public class ChatDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();

	private Client_Chatable pcilent;

	public ChatDataReachEvent(Client_Chatable c,byte[] data) {
		super(c,data);
		this.pcilent = c;
	}

	public Client_Chatable getChatableCilent(){
		return pcilent;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
