package com.github.xzzpig.BukkitTools;

import java.util.List;

import org.bukkit.entity.Player;

public class TString {
	public static final String s = "§";
	
	private TString(){}
	public static String Prefix(String prefix){
		return "§6["+prefix+"]§f";
	}
	
	public static String Prefix(String prefix,int colorid){
		return "§6["+prefix+"]"+Color(colorid);
	}
	
	public static String Prefix(String prefix,String colorid){
		return "§6["+prefix+"]"+Color(colorid);
	}
	
	public static String Color(int colorid){
		return "§"+colorid;
	}
	
	public static String Color(String colorid){
		return "§"+colorid;
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
	
	public static String sub(String s,String pre,String suf)
	{
		int f = s.indexOf(pre);
		int e = s.indexOf(suf);
		return s.substring(f+pre.length(),e);
	}
	
	public static String toUnicodeString(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			}
			else {
				sb.append("\\u"+Integer.toHexString(c));
			}
		}
		return sb.toString();
	}
}
