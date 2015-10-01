package com.github.xzzpig.exmctool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.bukkit.Bukkit;

import com.github.xzzpig.exmctool.loginexam.LoginPlayer;
import com.github.xzzpig.exmctool.tcp.ClientListener;

public class TcpServer implements Runnable{	
	@SuppressWarnings("resource")
	public void run() {
		ServerSocket ss = null;
		int port = Bukkit.getPluginManager().getPlugin("ExMCTool").getConfig().getInt("port", 10727);
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[ExMCTool]Wrong:端口("+port+")已占用，TCP所用失败");
			return;
		}
		System.out.println("[ExMCTool]TCP服务端("+port+")已启动");
		while(true){
			Socket s = null;
			try {s = ss.accept();} catch (IOException e) {}
			LoginPlayer.New(s.getInetAddress().getHostName(), s);
			try {
				s.getOutputStream().write("player".getBytes());
			} catch (IOException e) {}
			ClientListener.NewListener(s);
			System.out.println("[ExMCTool]客户端"+s.getInetAddress().getHostName()+"已连入");
		}
	}
}