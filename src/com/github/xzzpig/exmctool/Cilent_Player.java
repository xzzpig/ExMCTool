package com.github.xzzpig.exmctool;
import java.io.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Cilent_Player extends Cilent
{
	private String name;
	
	public Cilent_Player(Cilent cilent){
		super(cilent.s);
		cilent.remove();
		askForName();
	}
	
	public static Cilent_Player valueOf(Player player){
		for(Cilent c:cilents){
			if(c.type != CilentType.Player)
				continue;
			if(((Cilent_Player)c).getPlayer() == player)
				return (Cilent_Player)c;
		}
		return null;
	}
	public static Cilent_Player valueOf(String player){
		for(Cilent c:cilents){
			if(c.type != CilentType.Player)
				continue;
			if(((Cilent_Player)c).getName().equalsIgnoreCase(player))
				return (Cilent_Player)c;
		}
		return null;
	}
	
	protected void askForName(){
		try{
			s.getOutputStream().write("player".getBytes());
		}
		catch(IOException e){
			System.out.println("[ExMCTool]获取玩家ID请求发送失败");
		}
	}
	
	public boolean setName(String name){
		this.name = name;
		return true;
	}
	public String getName(){
		return name;
	}
	public Player getPlayer(){
		return Bukkit.getPlayer(name);
	}
}
