package com.github.xzzpig.exmctool.loginexam;

import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.exmctool.TcpServer;

public class LoginExam implements Listener{
	@EventHandler
	public void onLogin(PlayerLoginEvent event) throws Exception {
		System.out.println("here1");
		Player player = event.getPlayer();
		String ip = player.getAddress().getHostName();
		/*
		if(!TcpServer.getClient().containsKey(ip)){
			System.out.println("here2");
			event.disallow(null, "[ExMCTool]验证失败\nReason:"+LoginError.getError(1));
			return;
			}
		 */
		Socket s = TcpServer.getClient(ip);
		if(s == null||s.isClosed()){
			System.out.println(TString.Prefix("ExMCTool", 4)+player.getName()+"验证失败");
			event.disallow(null, "[ExMCTool]验证失败\nReason:"+LoginError.getError(1));
			System.out.println("here3");
			return;	
		}
		TcpServer.login.put(s, true);
		s.getOutputStream().write("login player".getBytes());
		byte[] buf = new byte[1024];
		int length = s.getInputStream().read(buf);
		String data = new String(buf).substring(0, length);
		if(!data.equalsIgnoreCase(player.getName())){
			System.out.println(TString.Prefix("ExMCTool", 4)+player.getName()+"验证失败");
			event.disallow(null, "[ExMCTool]验证失败\nReason:"+LoginError.getError(2));
			s.getOutputStream().write("login deny".getBytes());
			s.close();
			TcpServer.login.put(s, false);
			System.out.println("here4");
			return;
		}
		s.getOutputStream().write("login key".getBytes());
		buf = new byte[1024];
		length = s.getInputStream().read(buf);
		data = new String(buf).substring(0, length);
		String key = Bukkit.getPluginManager().getPlugin("ExMCTool").getConfig().getString("LoginExam.key", "");
		if(!data.equalsIgnoreCase(key)){
			System.out.println(TString.Prefix("ExMCTool", 4)+player.getName()+"验证失败");
			event.disallow(null, "[ExMCTool]验证失败\nReason:"+LoginError.getError(2));
			s.getOutputStream().write("login deny".getBytes());
			s.close();
			TcpServer.login.put(s, false);
			System.out.println("here5");
			return;
		}
		System.out.println(TString.Prefix("ExMCTool", 4)+player+"验证成功");
		s.getOutputStream().write("login pass".getBytes());
		event.allow();
		TcpServer.login.put(s, false);
		System.out.println("here6");
	}

	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		String ip = player.getAddress().getHostName();
		TcpServer.getClient().remove(ip);
	}
}
