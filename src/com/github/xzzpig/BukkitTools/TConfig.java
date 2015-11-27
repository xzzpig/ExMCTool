package com.github.xzzpig.BukkitTools;

import java.io.File;
import java.io.IOException;
import java.util.Set;

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
				e.printStackTrace();
			}
		config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	public static void delConfigFile(String pl,String filename){
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+filename);
		if (!file.exists())
			return;
		file.delete();
	}
	
	public static Object getConfig(FileConfiguration configfile,String arg0){
		return configfile.get(arg0);
	}
	public static Object getConfig(String pl,String filename,String arg0){
		return getConfig(getConfigFile(pl,filename),arg0);
	}
	
	public static String[] getConfigPath(String pl,String filename,String arg0) throws Exception{
		FileConfiguration config = getConfigFile(pl, filename);
		Set<String> key = config.getConfigurationSection(arg0).getKeys(false);
		if(key == null)
			return null;
		return key.toArray(new String[0]);
	}
	
	public static void saveConfig(String pl,FileConfiguration configfile,String filename,String arg0,Object arg1){
		configfile.set(arg0, arg1);
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+filename);
		try {
			configfile.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveConfig(String pl,FileConfiguration configfile,String filename){
		File file;
		file = new File(Bukkit.getPluginManager().getPlugin(pl).getDataFolder().toString() + "/"+filename);
		try {
			configfile.save(file);
		} catch (IOException e) {
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
			e.printStackTrace();
		}
	}
}
