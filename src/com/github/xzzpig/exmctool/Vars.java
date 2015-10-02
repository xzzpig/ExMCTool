package com.github.xzzpig.exmctool;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Vars {
	public static Plugin pl;	
	public static String key;
	public static String adminkey;
	public static FileConfiguration PlayerConfigs;
	public static HashMap<String, String> passwords = new HashMap<String, String>();
}
