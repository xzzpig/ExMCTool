package com.github.xzzpig.exmctool.tcp;

import java.net.Socket;

public class ClientSolver implements Runnable{
	Socket s;
	String data;
	
	public ClientSolver(Socket s,String data) {
		this.s = s;
		this.data = data;
	}
	
	public void run() {
		System.out.println("[ExMCTool]"+s.getInetAddress().getHostName()+":"+data);
		Solve();
	}

	private void Solve() {
		
	}
	

}
