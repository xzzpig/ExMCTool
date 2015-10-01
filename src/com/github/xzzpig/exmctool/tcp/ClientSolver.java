package com.github.xzzpig.exmctool.tcp;

import java.net.Socket;

import com.github.xzzpig.exmctool.loginexam.LoginPlayer;

public class ClientSolver implements Runnable{
	Socket s;
	String[] data;
	
	public ClientSolver(Socket s,String data) {
		this.s = s;
		this.data = data.split(" ");
	}
	
	public void run() {
		String  data2 = "";
		for(String s:data)
			data2 = data2+s+" ";
		System.out.println("[ExMCTool]"+LoginPlayer.Get(s).getName()+":"+data2);
		Solve();
	}

	private void Solve() {
		switch(GetArry(0)){
		case "name" :
			LoginPlayer.Get(s).Rebulid(GetArry(1),s);
			System.out.println("[ExMCTool]成功获取玩家名称:"+LoginPlayer.Get(s).getName());
			break;
		case "login" :
			LoginPlayer.SolveData(LoginPlayer.Get(s), GetArry(1),GetArry(2));
			break;
		}
	}
	
	private String GetArry(int i) {
		String r = "";
		if(data.length<i){
			r = "";
		}
		r = data[i];
		return r;
	}
}
