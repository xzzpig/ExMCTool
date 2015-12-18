package com.github.xzzpig.exmctool;
import com.github.xzzpig.BukkitTools.*;
import org.bukkit.configuration.file.*;

public class Vars
{
	public static Thread TcpThread;
	public static FileConfiguration config;
	public static String adminkey = TConfig.getConfigFile("ExMCTool","config.yml").getString("adminkey","madebyhz");
}
