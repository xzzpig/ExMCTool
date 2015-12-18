package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.event.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import com.github.xzzpig.exmctool.*;

public class LoginListener
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
		}
		if(!cilent.getSocket().getInetAddress().getHostAddress().equalsIgnoreCase(event.getAddress().getHostAddress())){
			event.disallow(event.getLoginResult().KICK_OTHER,"[ExMCTool]验证失败\nReason:"+LoginError.DifferentAddress);
			System.out.println("[ExMCTool]"+event.getName()+"验证失败,原因:"+LoginError.DifferentAddress);
		}
	}
}
