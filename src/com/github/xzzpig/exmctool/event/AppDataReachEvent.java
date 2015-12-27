package com.github.xzzpig.exmctool.event;
import com.github.xzzpig.exmctool.clients.*;
import org.bukkit.event.*;

public class AppDataReachEvent extends DataReachEvent
{
	private static final HandlerList handlers = new HandlerList();

	private Client_App aclient;

	public AppDataReachEvent(Client_App c,byte[] data) {
		super(c,data);
		this.aclient = c;
	}

	public Client_App getAppClent(){
		return aclient;
	}
	
	public Client_Player getPlayerClient(){
		return aclient.getPlayerClient();
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
