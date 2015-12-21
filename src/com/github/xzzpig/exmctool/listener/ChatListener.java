package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.event.*;

import java.text.*;
import java.util.*;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class ChatListener implements Listener
{
	public static List<String> chats = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(PlayerChatEvent event){
		sendChatMessageToCilent(event.getPlayer().getName(),event.getMessage());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onGetRemoteChatMessage(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("chat"))
			return;
		String sayer = "未知来源<" + event.getCilent().getSocket().getInetAddress().getHostName()+">",
		message = data[1],
		broadmessage;
		if(event.getCilent().getType() == CilentType.Player){
			sayer = ((Cilent_Player)event.getCilent()).getName();
			if(Bukkit.getPlayer(sayer) != null){
				Bukkit.getPlayer(sayer).sendMessage(message);
				sendChatMessageToCilent(sayer,message);
				return;
			}
			sayer = "[远程消息]"+sayer;
		}
		broadmessage = sayer +":"+message;
		Bukkit.broadcastMessage(broadmessage);
		sendChatMessageToCilent(sayer,message);
	}
	
	private void sendChatMessageToCilent(String sayer,String message){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		String time=format.format(date);
		message = 
			"chat "+
			"<Player>"+sayer+"<Player/>"+
			"<Message>"+message+"<Message/>"+
			"<Time>"+time+"<Time/>";
		for(Cilent cilent:Cilent.cilents){
			cilent.sendData(message.getBytes());
		}
		
	}
}
