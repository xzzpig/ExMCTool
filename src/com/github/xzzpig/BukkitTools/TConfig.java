package com.github.xzzpig.BukkitTools;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class TConfig {
	public static FileConfiguration getConfigFile(String pl,String filename){
		File file;
		FileConfiguration config;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+filename);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	
	public static Object getConfig(FileConfiguration configfile,String arg0){
		return configfile.get(arg0);
	}
	
	public static Object getConfig(String pl,String filename,String arg0){
		return getConfig(getConfigFile(pl,filename),arg0);
	}
	
	public static void saveConfig(String pl,FileConfiguration configfile,String arg0,Object arg1){
		/*
		TConfig.getConfigFile("MoreRPG", "Skills.yml").set("a","b");
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin("MoreRPG").getDataFolder().toString() + "/Skills.yml");
		try {
			TConfig.getConfigFile("MoreRPG", "Skills.yml").save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
		configfile.set(arg0, arg1);
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+configfile.getName());
		try {
			configfile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveConfig(String pl,String filename,String arg0,Object arg1){
		FileConfiguration configfile = getConfigFile(pl,filename);
		configfile.set(arg0, arg1);
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+filename);
		try {
			configfile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
