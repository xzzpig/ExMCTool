package com.github.xzzpig.BukkitTools.logutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.*;
import java.util.logging.*;

public class LogRedirectWithLog4j {
	public static void startRedirectConsoleLog(Plugin plugin){
	    try{
			Class.forName("org.apache.logging.log4j.LogManager");
			LogRedirectWithLog4j.regist();;
			plugin.getLogger().info("通过log4j获取log");
	    }
	    catch(NoClassDefFoundError|Exception e){
			LogHandler handler = new LogHandler();
			handler.setLevel(Level.ALL);
			plugin.getServer().getLogger().addHandler(handler);
			plugin.getLogger().info("通过Java Logging API获取log");
	    }
	}
	
	  public static void regist()
			    throws Exception
			  {
			    Logger logger = (Logger)LogManager.getRootLogger();
			    logger.addAppender(new LogAppender());
			  }

}
