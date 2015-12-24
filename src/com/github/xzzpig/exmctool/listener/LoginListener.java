package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.clients.Client_Player;
import com.github.xzzpig.exmctool.event.*;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.github.xzzpig.exmctool.*;

public class LoginListener implements Listener
{
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event){
		Client_Player client = Client_Player.valueOf(event.getName());
		if(client == null){
			event.getLoginResult();
			event.disallow(Result.KICK_OTHER,"[ExMCTool]验证失败\nReason:"+LoginError.NoClient);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+LoginError.NoClient);
			return;
		}
		if(!client.getSocket().getInetAddress().getHostAddress().equalsIgnoreCase(event.getAddress().getHostAddress())){
			event.getLoginResult();
			event.disallow(Result.KICK_OTHER,"[ExMCTool]验证失败\nReason:"+LoginError.DifferentAddress);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+LoginError.DifferentAddress);
			client.sendData("login deny".getBytes());
			return;
		}
		client.startLoginExam();
		LoginError error = client.isLoginPassed();
		if(error == null)
			event.allow();
		else{
			event.getLoginResult();
			event.disallow(Result.KICK_OTHER,"[ExMCTool]验证失败\nReason:"+error);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+error);
			client.sendData("login pass".getBytes());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLoginData(PlayerDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=3||!data[0].equalsIgnoreCase("login"))
			return;
		String key=data[1],value=data[2];
		Client_Player client = event.getPlayerClient();
		if(!client.loginexam)
			return;
		switch(key) {
			case "player" :
				client.uncheckplayer = value;
				break;
			case "key" :
				client.uncheckkey = value;
				break;
			case "password" :
				client.uncheckpass = value;
				break;
			case "callevent":
				Bukkit.getPluginManager().callEvent(new AsyncPlayerPreLoginEvent(client.getName(),client.getSocket().getInetAddress()));
				break;
		}
	}
}
