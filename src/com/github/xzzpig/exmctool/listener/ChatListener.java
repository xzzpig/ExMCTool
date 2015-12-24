package com.github.xzzpig.exmctool.listener;

import com.github.xzzpig.exmctool.Debuger;
import com.github.xzzpig.exmctool.event.*;

import java.io.*;
import java.text.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import com.github.xzzpig.exmctool.clients.*;

public class ChatListener implements Listener
{
	public static List<String> chats = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(PlayerChatEvent event){
		sendChatMessageToClient(event.getPlayer().getName(),event.getMessage());
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onGetRemoteChatMessage(ChatDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("chat"))
			return;
		String sayer = event.getChatableClient().getName(),
			message = data[1],
			broadmessage;
		if(event.getChatableClient().superc.isType(ClientType.Player)){
			if(Bukkit.getPlayer(sayer)!=null){
				Bukkit.getPlayer(sayer).sendMessage(message);
				sendChatMessageToClient(sayer,message);
				return;
			}
		}
		broadmessage = sayer+":"+message;
		Bukkit.broadcastMessage(broadmessage);
		sendChatMessageToClient(sayer,message);
	}

	@EventHandler
	public void onAskChatMessage(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(!data[0].equalsIgnoreCase("getchat"))
			return;
		Debuger.print("getchat");
		Date ddate=new Date();
		DateFormat format=new SimpleDateFormat("yyyy_MM_dd");
		int amount = -1;
		Client client = event.getClient();
		String date = format.format(ddate);
		try{
			amount = Integer.valueOf(data[1]);
			date = data[2];
		}catch(Exception exp){}
		File logfile = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/chat/"+date+".log");
		List<String> chatlogs = new ArrayList<String>();
		try{
			FileInputStream fin = new FileInputStream(logfile);
			Scanner scanner = new Scanner(fin);
			while(scanner.hasNextLine()){
				String message = scanner.nextLine();
				if(amount == -1)
					client.sendData(message.getBytes());
				else if(chatlogs.size()>=amount)
					chatlogs.remove(0);
				chatlogs.add(message);
			}
			if(amount != -1)
				for(String message:chatlogs)
					client.sendData(message.getBytes());
			scanner.close();fin.close();
		}
		catch(Exception e){}
	}
	
	private void sendChatMessageToClient(String sayer,String message){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		String time=format.format(date);
		message = 
			"chat "+
			"<Player>"+sayer+"<Player/>"+
			"<Message>"+message+"<Message/>"+
			"<Time>"+time+"<Time/>";
		for(Client client:Client.clients){
			client.sendData(message.getBytes());
		}
		File dir = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/chat"),
			outfile = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/chat/"+time.split(" ")[0]+".log");
		dir.mkdirs();
		try{
			FileOutputStream fout = new  FileOutputStream(outfile,true);
			fout.write(("\n"+message).getBytes());
			fout.close();
		}
		catch(Exception e){
			Bukkit.getLogger().warning("[ExMCTool]聊天记录保存失败");
		}
	}
}
