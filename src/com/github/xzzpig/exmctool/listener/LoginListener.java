package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.event.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import com.github.xzzpig.exmctool.*;

public class LoginListener implements Listener
{
	@EventHandler
	public void onLogin(PlayerLoginEvent event){
		Bukkit.getPluginManager().callEvent(new AsyncPlayerPreLoginEvent(event.getPlayer().getName(),event.getAddress()));
	}
	
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event){
		Cilent_Player cilent = Cilent_Player.valueOf(event.getName());
		if(cilent == null){
			event.disallow(event.getLoginResult().KICK_OTHER,"[ExMCTool]验证失败\nReason:"+LoginError.NoCilent);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+LoginError.NoCilent);
			return;
		}
		if(!cilent.getSocket().getInetAddress().getHostAddress().equalsIgnoreCase(event.getAddress().getHostAddress())){
			event.disallow(event.getLoginResult().KICK_OTHER,"[ExMCTool]验证失败\nReason:"+LoginError.DifferentAddress);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+LoginError.DifferentAddress);
			cilent.sendData("login deny".getBytes());
			return;
		}
		cilent.startLoginExam();
		LoginError error = cilent.isLoginPassed();
		if(error == null)
			event.allow();
		else{
			event.disallow(event.getLoginResult().KICK_OTHER,"[ExMCTool]验证失败\nReason:"+error);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+error);
			cilent.sendData("login pass".getBytes());
		}
	}
	
	@EventHandler
	public void onLoginData(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=3||!data[0].equalsIgnoreCase("login")||event.getCilent().getType()!=CilentType.Player)
			return;
		String key=data[1],value=data[2];
		Cilent_Player cilent = (Cilent_Player)event.getCilent();
		if(!cilent.loginexam)
			return;
		switch(key) {
			case "player" :
				cilent.uncheckplayer = value;
				break;
			case "key" :
				cilent.uncheckkey = value;
				break;
			case "password" :
				cilent.uncheckpass = value;
				break;
			case "callevent":
				Bukkit.getPluginManager().callEvent(new AsyncPlayerPreLoginEvent(cilent.getName(),cilent.getSocket().getInetAddress()));
				break;
		}
	}
}
