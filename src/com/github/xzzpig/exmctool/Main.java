package com.github.xzzpig.exmctool;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.BukkitTools.logutil.*;
import com.github.xzzpig.exmctool.listener.*;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable(){
		getLogger().info(getName()+"已加载");
		startRedirectConsoleLog();
		//getLogger().addHandler(new LogHandler().setLevel2(Level.ALL));
		saveDefaultConfig();
		Vars.config = getConfig();
		Vars.TcpThread = new Thread(new TcpServer());
		Vars.TcpThread.start();
		getServer().getPluginManager().registerEvents(new AlwaysDataListener(),this);
		if(Vars.enable_loginexam)
			getServer().getPluginManager().registerEvents(new LoginListener(),this);
		if(Vars.enable_chat)
			getServer().getPluginManager().registerEvents(new ChatListener(),this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable(){
		getLogger().info(getName()+"已停载");
		
		try{
			TcpServer.server.close();
			Vars.TcpThread.stop();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	  private void startRedirectConsoleLog()
	  {
		  Debuger.print("开始读取log");
	    try
	    {
	      Class.forName("org.apache.logging.log4j.LogManager");
	      LogRedirectWithLog4j.regist();;
	      getLogger().info("Start redirect console log with log4j.");
	    }
	    catch (NoClassDefFoundError|Exception e)
	    {
	      LogHandler handler = new LogHandler();
	      handler.setLevel(Level.ALL);
	      getServer().getLogger().addHandler(handler);
	      getLogger().info("Start redirect console log with Java Logging API.");
	    }
	  }
}
