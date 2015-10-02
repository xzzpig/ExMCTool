package com.github.xzzpig.exmctool.loginexam;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import com.github.xzzpig.BukkitTools.MD5;
import com.github.xzzpig.exmctool.Vars;

public class LoginPlayer {
	private static HashMap<String, LoginPlayer> LPlayer = new HashMap<String, LoginPlayer>();
	private static HashMap<Socket, LoginPlayer> LPlayer2 = new HashMap<Socket, LoginPlayer>();
	private HashMap<String, Boolean> pass = new HashMap<String, Boolean>();
	public boolean examfinished = false;
	public String name = "",password = "";
	private Socket s;
	
	public LoginPlayer(String name,Socket s) {
		this.name = name;
		this.s = s;
		if(Vars.passwords.containsKey(name)){
			this.password = Vars.passwords.get(name);
		}
		LPlayer.put(name, this);
		LPlayer2.put(s, this);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSocket() {
		return s;
	}
	public void setSocket(Socket s) {
		this.s = s;
	}
	public boolean getPass(String key) {
		if(!this.pass.containsKey(key))
			return false;
		return pass.get(key);
	}
	public void setPass(String key,boolean value) {
		this.pass.put(key, value);
		System.out.println("[ExMCTool]玩家"+this.name+"通过"+key+"："+value);
	}
	
	public static void New(String player,Socket s){
		if(LPlayer.containsKey(player))
			LPlayer.remove(player);
		if(LPlayer2.containsKey(s))
			LPlayer2.remove(s);
		new LoginPlayer(player, s);
	}
	
	public void Rebulid(String player,Socket s){
		this.name = player;
		this.s = s;
		LPlayer.put(player, this);
		LPlayer2.put(s, this);
		if(Vars.passwords.containsKey(name)){
			this.password = Vars.passwords.get(name);
		}
	}
	
	public static LoginPlayer Get(String player){
		if(!LPlayer.containsKey(player))
			return null;
		return LPlayer.get(player);
	}	
	public static LoginPlayer Get(Socket s){
		if(!LPlayer2.containsKey(s)){
			return null;}
		return LPlayer2.get(s);
	}
	
	public boolean IsPassed(){
		while(!this.examfinished);
		return (this.getPass("player")&&this.getPass("key")&&this.getPass("password"));
	}
	public boolean IsPassed(String key){
		while(!(this.examfinished||this.pass.containsKey(key)));
		return (this.getPass(key));
	}
	
	public static boolean IsExist(String name){
		if(!LPlayer.containsKey(name)){
			return false;
		}
		return true;
	}
	
	public void SendData(String data){
		try {
			this.s.getOutputStream().write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("发送给"+this.name+"失败");
		}
	}
	
	public void Destory(){
		try {
			this.s.close();
		} catch (IOException e) {
			System.out.println(this.name+"断开连接失败");
			e.printStackTrace();
		}
		LoginPlayer.LPlayer.remove(this.name);
		LoginPlayer.LPlayer2.remove(this.s);		
	}
	
	public static void SolveData(LoginPlayer lp ,String key,String value){
		switch(key) {
		case "player" :
			if(value.equals(MD5.GetMD5Code(lp.getName()))){
				lp.setPass(key, true);}			
			else{
				lp.examfinished = true;
			}
			break;
		case "key" :
			String keys = Vars.key;
			if(value.equals(MD5.GetMD5Code(keys))){
				lp.setPass(key, true);
			}
			else
				lp.examfinished = true;
			break;
		case "password" :
			String password = lp.password;
			if(value.equals(password)){
				lp.setPass(key, true);
				lp.examfinished = true;
			}
			else
				lp.examfinished = true;
			break;
		}
	}
}
