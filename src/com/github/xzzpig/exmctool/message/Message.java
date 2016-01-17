package com.github.xzzpig.exmctool.message;

import java.util.*;

public abstract class Message
{
	@Override
	public abstract String toString();
	
	public String getColorableMessage(){
		return match(toString());
	}
	
	public static String match(String arg){
		List<Character> s = new ArrayList<Character>();
		boolean skip = false;
		for(char c:arg.toCharArray()){
			if(c=='§'){
				skip = true;
				continue;
			}
			if(skip){
				skip = false;
				for(char cc:("</font><font color='"+BukkitColor.get(c).get()+"'>").toCharArray())
					s.add(cc);
				continue;
			}
			s.add(c);
		}
		char[] cs = new char[s.size()];
		for(int i=0;i<s.size();i++)
			cs[i] = s.get(i);
		return (new String(cs).replaceFirst("</font>","")+"</font>");
	}
}

enum BukkitColor
{
	BLACK('0',"black"),
    DARK_BLUE('1',"blue"),
	GREEN('2',"green"),
	BLUE('3',"blue"),
    RED('4',"red"),
	PURPLE('5',"blue"),
    GOLD('6',"#FFD700"),
    GRAY('7',"gray"),
	DARK_GRAY('8',"gray"),
	LIGHT_GREEN('a',"green"),
    DARK_GREEN('b',"green"),
    Dark_RED('c',"red"),
	PINK('d',"pink"),
    YELLOW('e',"yellow"),
    RESET('f',"black");

	char id;
	String color;

	BukkitColor(char id,String color){
		this.id = id;
		this.color = color;
	}

	public static BukkitColor get(char c){
		for(BukkitColor bc:values()){
			if(bc.id == c)
				return bc;
		}
		return RESET;
	}

	public String get(){
		return color;
	}
}
