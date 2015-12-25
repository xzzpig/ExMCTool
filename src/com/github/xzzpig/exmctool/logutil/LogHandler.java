package com.github.xzzpig.exmctool.logutil;

import java.text.*;
import java.util.logging.*;

import org.bukkit.*;
import com.github.xzzpig.exmctool.event.*;

public class LogHandler extends Handler
{
	private SimpleDateFormat date;

	public LogHandler(){
		this.date = new SimpleDateFormat("HH:mm:ss");
		setFilter(new Filter()
			{
				public boolean isLoggable(LogRecord record){
					return true;
				}
			});
	}
	public void close()
	throws SecurityException{}

	public void flush(){}

	public void publish(LogRecord record){
		Bukkit.getPluginManager().callEvent(new LogPrintEvent(this.date.format(Long.valueOf(record.getMillis())),record.getLevel().getName(),record.getMessage(),record.getThrown()));
	}
}
