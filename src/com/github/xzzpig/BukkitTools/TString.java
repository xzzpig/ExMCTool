package com.github.xzzpig.BukkitTools;

import java.io.*;
import java.util.*;
import org.bukkit.entity.*;

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
		int e = s.indexOf(suf,f);
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
	public static String getRandomCH(int len){
        String ret="";
		for(int i=0;i<len;i++){
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176+Math.abs(random.nextInt(39))); //获取高位值
			lowPos = (161+Math.abs(random.nextInt(93))); //获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try{
				str = new String(b,"GBk"); //转成中文
			}
			catch(UnsupportedEncodingException ex){
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
    }
}
