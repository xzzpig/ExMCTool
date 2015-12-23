package com.github.xzzpig.exmctool.event;
import org.bukkit.event.*;
import com.github.xzzpig.exmctool.*;

public class PlayerDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();
	
	private Cilent_Player pcilent;
	
	public PlayerDataReachEvent(Cilent_Player c,byte[] data) {
		super(c,data);
		this.pcilent = c;
	}
	
	public Cilent_Player getPlayerCilent(){
		return pcilent;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
