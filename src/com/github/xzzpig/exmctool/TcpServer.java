package com.github.xzzpig.exmctool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.Bukkit;

import com.github.xzzpig.exmctool.cilents.Cilent;

public class TcpServer implements Runnable{	
	public static ServerSocket server;
	public void run() {
		ServerSocket ss = null;
		int port = Bukkit.getPluginManager().getPlugin("ExMCTool").getConfig().getInt("port", 10727);
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[ExMCTool]Wrong:端口("+port+")已占用，TCP所用失败");
			return;
		}
		server = ss;
		System.out.println("[ExMCTool]TCP服务端("+port+")已启动");
		while(true){
			Socket s = null;
			try {s = ss.accept();} catch (IOException e) {}
			System.out.println("[ExMCTool]客户端"+s.getInetAddress().getHostName()+"已连入");
			new Cilent(s);
			}
	}
}
