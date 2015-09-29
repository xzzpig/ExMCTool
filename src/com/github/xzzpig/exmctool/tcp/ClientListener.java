package com.github.xzzpig.exmctool.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import com.github.xzzpig.exmctool.TcpServer;

public class ClientListener implements Runnable{
	private static HashMap<Socket, Thread> thread = new HashMap<Socket, Thread>();
	
	Socket s;
	
	private ClientListener(Socket s){
		this.s = s;
		System.out.println("[ExMCTool]"+s.getInetAddress().getHostName()+"已加入监听");
	}
	
	public static Thread getThread(Socket s) {
		return thread.get(s);
	}
	
	public static Thread getTread(String ip){
		return thread.get(TcpServer.getClient(ip));
	}

	public static void NewListener(Socket s){
		Thread t = new Thread(new ClientListener(s));
		thread.put(s, t);
		t.start();
	}
	
	@Override
	public void run() {
		try {
			Listen();
		} catch (IOException e) {
			System.out.println("[ExMCTool]客户端监听创建失败");
		}
	}
	
	private void Listen() throws IOException{
		while(true){
			if(this.s.isClosed()){
				thread.remove(this.s);
				TcpServer.getClient().remove(this.s.getInetAddress().getHostName());
				return;
			}
			while(TcpServer.login.get(s));
			byte[] buf = new byte[1024];
			int length = s.getInputStream().read(buf);
			String data = new String(buf).substring(0, length);
			new Thread(new ClientSolver(s,data)).start();
		}
	}
}
