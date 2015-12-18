package com.github.xzzpig.exmctool;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.exmctool.listener.AlwaysDataListener;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"已加载");
	saveDefaultConfig();
	Vars.TcpThread = new Thread(new TcpServer());
	Vars.TcpThread.start();
	getServer().getPluginManager().registerEvents(new AlwaysDataListener(), this);
	
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		getLogger().info(getName()+"已停载");
		try {
			TcpServer.server.close();
			Vars.TcpThread.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
