package com.github.xzzpig.BukkitTools;

import java.util.List;

import org.bukkit.entity.Player;

public class TString {
	private TString(){}
	public static String Prefix(String prefix){
		return "¡ì6["+prefix+"]¡ìf";
	}
	
	public static String Prefix(String prefix,int colorid){
		return "¡ì6["+prefix+"]"+Color(colorid);
	}
	
	public static String Prefix(String prefix,String colorid){
		return "¡ì6["+prefix+"]"+Color(colorid);
	}
	
	public static String Color(int colorid){
		return "¡ì"+colorid;
	}
	
	public static String Color(String colorid){
		return "¡ì"+colorid;
	}
	
	public static void Print(String message){
		System.out.println(message);
	}
	
	public static void Print(String message,Player player){
		Print(message);
		player.sendMessage(message);
	}
	
	public static void Print(String message,String player){
		Print(message);
		Player player2 = TEntity.toPlayer(player);
		player2.sendMessage(message);
	}
	
	public static void Print(List<String> messages,String player){
		String message = "";
		if(messages != null){
			for(String temp:messages){
				message = message+"\n"+temp;
			}
			Print(message,player);
		}
		else
			Print("null",player);
	}
	
	public static void Print(List<String> messages,Player player){
		Print(messages,player.getName());
	}
}
