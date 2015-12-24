package com.github.xzzpig.exmctool.clients;
import com.github.xzzpig.BukkitTools.*;

import java.io.*;

import org.bukkit.*;
import org.bukkit.entity.*;

import com.github.xzzpig.exmctool.LoginError;
import com.github.xzzpig.exmctool.Vars;
import com.github.xzzpig.exmctool.event.*;

public class Client_Player extends Client
{
	public String name,password;
	public String uncheckpass,uncheckkey,uncheckplayer;
	public boolean loginexam,login;
	private Client_Player self = this;
	public Client superc;
	public Client_Player(){}
	
	public Client_Player(Client client){
		super(ClientType.Player,client);
		superc = client;
		askForName();
		this.password = TConfig.getConfigFile("ExMCTool","login.yml").getString("login.password."+name,"null");
		new Client_Chatable(client);
	}
	
	public static Client_Player valueOf(Player player){
		for(Client c:clients){
			if(!(c.types.contains(ClientType.Player)))
				continue;
			if(((Client_Player)c).getPlayer() == player)
				return (Client_Player)c;
		}
		return null;
	}
	public static Client_Player valueOf(String player){
		for(Client c:clients){
			if(!(c.types.contains(ClientType.Player)))
				continue;
			if(((Client_Player)c).getName().equalsIgnoreCase(player))
				return (Client_Player)c;
		}
		return null;
	}
	
	protected void askForName(){
		try{
			s.getOutputStream().write("player".getBytes());
		}
		catch(IOException e){
			System.out.println("[ExMCTool]获取玩家ID请求发送失败");
		}
	}
	
	public boolean setName(String name){
		this.name = name;
		this.password = TConfig.getConfigFile("ExMCTool","login.yml").getString("login.password."+name,"null");
		return true;
	}
	public String getName(){
		return name;
	}
	
	@SuppressWarnings("deprecation")
	public Player getPlayer(){
		return Bukkit.getPlayer(name);
	}
	
	public void startLoginExam(){
		uncheckkey = null;
		uncheckpass = null;
		uncheckplayer = null;
		loginexam = true;
		sendData("login player".getBytes());
		sendData("login key".getBytes());
		sendData("login password".getBytes());
	}
	
	public LoginError isLoginPassed(){
		if(!loginexam)
			return LoginError.UnStartLogin;
		if(login)
			return null;
		Thread t = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						Thread.sleep(1000);
					}
					catch(InterruptedException e){}
				}
		});
		t.start();
		while(t.isAlive()&&(uncheckpass==null||uncheckkey==null||uncheckplayer==null)){}
		if(uncheckpass==null||uncheckkey==null||uncheckplayer==null)
			return LoginError.NoData;
		if(password.equalsIgnoreCase("null"))
			uncheckpass = "null";
		if(!password.equalsIgnoreCase(uncheckpass))
			return LoginError.DifferentPass;
		if(!MD5.GetMD5Code(Vars.loginkey).equalsIgnoreCase(uncheckkey))
			return LoginError.DifferentKey;
		if(!MD5.GetMD5Code(name).equalsIgnoreCase(uncheckplayer))
			return LoginError.DifferentID;
		login = true;
		return null;
	}

	@Override
	protected void readdata(){
		Bukkit.getPluginManager().callEvent(new PlayerDataReachEvent(self,data));
	}

	@Override
	public void renew(Client c){
		new Client_Player(c);
	}
	
	
}
