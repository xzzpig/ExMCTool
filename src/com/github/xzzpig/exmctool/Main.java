package com.github.xzzpig.exmctool;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.xzzpig.BukkitTools.TConfig;
import com.github.xzzpig.exmctool.loginexam.LoginExam;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		getLogger().info(getName()+"插件已被加载");
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new LoginExam(), this);
		Vars.pl = (Plugin)this;
		GetConfigs();
		new Thread(new TcpServer()).start();
	}
	
	//插件停用函数
	@Override
	public void onDisable() {
		SaveConfigs();
		getLogger().info(getName()+"插件已被停用 ");
	}
	
	public static void GetConfigs(){
		Vars.key = Vars.pl.getConfig().getString("LoginExam.key", "madebyhz");
		Vars.adminkey = Vars.pl.getConfig().getString("Admin.key", "adminkey0123");
		Vars.PlayerConfigs = TConfig.getConfigFile(Vars.pl, "PlayerConfigs.yml");
		for(OfflinePlayer player:Vars.pl.getServer().getOfflinePlayers()){
			Vars.passwords.put(player.getName(),
					Vars.PlayerConfigs.getString("ExMCTool."+player.getName()+".password", "null"));
		}
		for(String key:Vars.PlayerConfigs.getStringList("ExMCTool")){
			Vars.passwords.put(key,
					Vars.PlayerConfigs.getString("ExMCTool."+key+".password", "null"));
		}
		Vars.pl.getLogger().info("玩家密码已加载");
	}
	public static void SaveConfigs(){
		Iterator<Entry<String, String>> ir = Vars.passwords.entrySet().iterator();
		while(ir.hasNext()){
			Entry<String, String> sub = ir.next();
			TConfig.saveConfig(Vars.pl, "PlayerConfigs.yml","ExMCTool."+sub.getKey()+".password",sub.getValue());
		}
		Vars.pl.getLogger().info("玩家密码已保存");
	}
}
