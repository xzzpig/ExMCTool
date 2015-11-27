package com.github.xzzpig.exmctool;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"已加载");
	saveDefaultConfig();
	//getServer().getPluginManager().registerEvents(new LoginExam(), this);
	
	}
	@Override
	public void onDisable() {
	getLogger().info(getName()+"已停载");
	}

}
