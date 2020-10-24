package com.github.xzzpig.exmctool.loginexam;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginExam implements Listener{
	@EventHandler
	public void Login(AsyncPlayerPreLoginEvent a) throws Exception {
		String player = a.getName();
		if(!LoginPlayer.IsExist(player)){
      a.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
      a.setKickMessage("[ExMCTool]验证失败\nReason:"+LoginError.getError(1));
			System.out.println("[ExMCTool]"+player+"验证失败");
			return;
		}
		LoginPlayer lp = LoginPlayer.Get(player);
		lp.SendData("login player");
		if(!LoginPlayer.Get(player).IsPassed("player")){
			a.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
      a.setKickMessage("[ExMCTool]验证失败\nReason:"+LoginError.getError(2));
			System.out.println("[ExMCTool]"+player+"验证失败");
			lp.SendData("login deny");
			lp.Destory();
			return;
		}
		lp.SendData("login key");
		if(!LoginPlayer.Get(player).IsPassed("key")){
			a.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
      a.setKickMessage("[ExMCTool]验证失败\nReason:"+LoginError.getError(2));
			System.out.println("[ExMCTool]"+player+"验证失败");
			lp.SendData("login deny");
			lp.Destory();
			return;
		}
		if(LoginPlayer.Get(player).IsPassed()){
			lp.SendData("login pass");
			System.out.println("[ExMCTool]"+player+"验证通过");
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		LoginPlayer.Get(player.getName()).Destory();
	}
}
