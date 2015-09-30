package com.github.xzzpig.exmctool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.bukkit.Bukkit;

import com.github.xzzpig.exmctool.tcp.ClientListener;

public class TcpServer implements Runnable{
	private static HashMap<String, String> Ip = new HashMap<String, String>();	
	private static HashMap<String, Socket> Client = new HashMap<String, Socket>();
	public static HashMap<Socket, Boolean> login = new HashMap<Socket, Boolean>();
	
	@SuppressWarnings("resource")
	public void run() {
		ServerSocket ss = null;
		int port = Bukkit.getPluginManager().getPlugin("ExMCTool").getConfig().getInt("port", 10727);
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[ExMCTool]Wrong:端口已占用，TCP所用失败");
			return;
		}
		System.out.println("[ExMCTool]TCP服务端已启动");
		while(true){
			Socket s = null;
			try {s = ss.accept();} catch (IOException e) {}
			Client.put(s.getInetAddress().getHostName(), s);
			ClientListener.NewListener(s);
			System.out.println("[ExMCTool]客户端"+s.getInetAddress().getHostName()+"已连入");
			new Thread(new GetIp(s, Ip)).start();
		}
	}

	public static Socket getClient(String iP) {
		if(!Client.containsKey(iP))
			Client.put(iP, null);
		return Client.get(iP);
	}
	public static HashMap<String, Socket> getClient() {
		return Client;
	}
	public static String getIp(String player) {
		if(!Ip.containsKey(player))
			Ip.put(player, null);
		return Ip.get(player);
	}
}




class GetIp implements Runnable{
	private Socket s;
	private HashMap<String, String> Ip;	
	GetIp(Socket s ,HashMap<String, String> ip){
		this.s = s;
		this.Ip = ip;
	}

	public void run() {
		try {
			s.getOutputStream().write("login player".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			length = s.getInputStream().read(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String player = new String(buf).substring(0, length);
		Ip.put(player, s.getInetAddress().getHostName());
		System.out.println("[ExMCTool]客户端"+player+"("+s.getInetAddress().getHostName()+")已记录");
	}
}
