package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.*;

import java.text.*;
import java.util.*;

import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class ChatListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(PlayerChatEvent event){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		String message = "chat "+
			"<Player>"+event.getPlayer().getName()+"<Player/>"+
			"<Message>"+event.getMessage()+"<Message/>"+
			"<Time>"+time+"<Time/>";
		for(Cilent cilent:Cilent.cilents){
			cilent.sendData(message.getBytes());
		}
	}
}
