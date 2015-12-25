package com.github.xzzpig.BukkitTools.logutil;

import java.io.*;
import java.text.*;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.bukkit.*;

public class LogAppender
extends AbstractAppender
{
	private SimpleDateFormat date;

	public LogAppender(){
		super("ExMCTool",null,null);
		this.date = new SimpleDateFormat("HH:mm:ss");
		start();
	}

	public void append(LogEvent event){
		StringBuilder builder = new StringBuilder();
		Throwable ex = event.getThrown();

		String message = event.getMessage().getFormattedMessage();

		builder.append(this.date.format(Long.valueOf(event.getMillis())));
		builder.append("-[");
		builder.append(event.getLevel().name());
		builder.append("]-");
		builder.append(message);
		if(ex!=null){
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			builder.append(writer);
		}
		Bukkit.getPluginManager().callEvent(new LogPrintEvent(this.date.format(Long.valueOf(event.getMillis())),event.getLevel().name(),event.getMessage().getFormattedMessage(),event.getThrown()));
		
	}
}
