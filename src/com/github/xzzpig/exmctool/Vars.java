package com.github.xzzpig.exmctool;
import com.github.xzzpig.BukkitTools.*;
import org.bukkit.configuration.file.*;

public class Vars
{
	public static Thread TcpThread;
	public static FileConfiguration config;
	public static String adminkey = TConfig.getConfigFile("ExMCTool","config.yml").getString("adminkey","madebyhz");
	public static String loginkey = TConfig.getConfigFile("ExMCTool","config.yml").getString("loginkey","madebyhz");
	public static String logkey = TConfig.getConfigFile("ExMCTool","config.yml").getString("logkey","madebyhz");
	public static boolean enable_loginexam = TConfig.getConfigFile("ExMCTool","config.yml").getBoolean("enable.loginexam",true);
	public static boolean enable_chat = TConfig.getConfigFile("ExMCTool","config.yml").getBoolean("enable.chat",true);
	public static boolean enable_log = TConfig.getConfigFile("ExMCTool","config.yml").getBoolean("enable.log",true);
}
