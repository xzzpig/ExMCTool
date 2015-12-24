package com.github.xzzpig.exmctool.event;

import org.bukkit.event.*;
import java.util.logging.*;

public class LogPrintEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();

	String message;
	String level;
	String time;
	Throwable throwa;
	
	public LogPrintEvent(String time,String level,String mrssage,Throwable throwa) {
		this.message = mrssage;
		this.level = level;
		this.throwa = throwa;
		this.time = time;
	}
	
	public Throwable getThrowa(){
		return throwa;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getMessage(){
		return message;
	}

	public String getLevel(){
		return level;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
