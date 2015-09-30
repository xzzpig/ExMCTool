package com.github.xzzpig.exmctool;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.exmctool.loginexam.LoginExam;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
	getLogger().info(getName()+"插件已被加载");
	saveDefaultConfig();
	getServer().getPluginManager().registerEvents(new LoginExam(), this);
	new Thread(new TcpServer()).start();
	}
	
	//插件停用函数
	@Override
	public void onDisable() {
	getLogger().info(getName()+"插件已被停用 ");
	}

}
