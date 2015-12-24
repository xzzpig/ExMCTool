package com.github.xzzpig.exmctool.logutil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import com.github.xzzpig.exmctool.clients.Client;

public class LogAppender
  extends AbstractAppender
{
  private SimpleDateFormat date;
  
  public LogAppender()
  {
    super("ExMCTool", null, null);
    this.date = new SimpleDateFormat("HH:mm:ss");
    start();
  }
  
  public void append(LogEvent event)
  {
    StringBuilder builder = new StringBuilder();
    Throwable ex = event.getThrown();
    
    String message = event.getMessage().getFormattedMessage();
    
    builder.append(this.date.format(Long.valueOf(event.getMillis())));
    builder.append("-[");
    builder.append(event.getLevel().name());
    builder.append("]-");
    builder.append(message);
    if (ex != null)
    {
      StringWriter writer = new StringWriter();
      ex.printStackTrace(new PrintWriter(writer));
      builder.append(writer);
    }
	for(Client c :Client.clients)
		c.sendData(builder.toString().getBytes());	
  }
}
