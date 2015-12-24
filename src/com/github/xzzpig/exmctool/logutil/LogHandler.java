package com.github.xzzpig.exmctool.logutil;

import java.text.*;
import java.util.logging.*;

import com.github.xzzpig.exmctool.clients.Client;

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
		StringBuilder builder = new StringBuilder();
		String message =record.getMessage();

		builder.append(this.date.format(Long.valueOf(record.getMillis())));
		builder.append("-[");
		builder.append(record.getLevel().getName());
		builder.append("]-");
		builder.append(message);
		for(Client c :Client.clients)
			c.sendData(builder.toString().getBytes());
	}
	
	public synchronized LogHandler setLevel2(Level newLevel) throws SecurityException {
		super.setLevel(newLevel);
		return this;
	}
}
