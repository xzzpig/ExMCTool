package com.github.xzzpig.exmctool.event;
import com.github.xzzpig.exmctool.cilents.*;
import org.bukkit.event.*;

public class ChatDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();

	private Cilent_Chatable pcilent;

	public ChatDataReachEvent(Cilent_Chatable c,byte[] data) {
		super(c,data);
		this.pcilent = c;
	}

	public Cilent_Chatable getChatableCilent(){
		return pcilent;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
