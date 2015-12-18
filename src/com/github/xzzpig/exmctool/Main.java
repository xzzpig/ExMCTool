package com.github.xzzpig.exmctool;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.exmctool.listener.AlwaysDataListener;
import com.github.xzzpig.exmctool.listener.*;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable(){
		getLogger().info(getName()+"已加载");
		saveDefaultConfig();
		Vars.config = getConfig();
		Vars.TcpThread = new Thread(new TcpServer());
		Vars.TcpThread.start();
		getServer().getPluginManager().registerEvents(new AlwaysDataListener(),this);
		if(Vars.enable_loginexam)
			getServer().getPluginManager().registerEvents(new LoginListener(),this);
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

}
