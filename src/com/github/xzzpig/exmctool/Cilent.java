package com.github.xzzpig.exmctool;
import java.net.*;
import java.util.*;
import java.io.*;
import org.bukkit.*;
import com.github.xzzpig.exmctool.event.*;

public class Cilent
{
	private static List<Cilent> cilents = new ArrayList<Cilent>();
	
	private Socket s;
	private byte[] data;
	private String name;
	
	public Cilent(Socket s){
		cilents.add(this);
		this.s = s;
		readdata();
		askForName();
	}
	
	public static Cilent valueOf(Socket s){
		for(Cilent c:cilents)
		if(c.s == s)
			return c;
		return null;
	}
	
	private void askForName(){
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
	
	public Socket getSocket(){
		return s;
	}
	
	private void readdata(){
		new Thread(new Runnable(){
				@Override
				public void run(){
					while(true){
						if(s.isClosed()){
							cilents.remove(valueOf(s));
							return;
						}
						byte[] buf = new byte[1024*1024];
						int length = 0;
						try{
							length = s.getInputStream().read(buf);
						}
						catch(IOException e){}
						data = Arrays.copyOf(buf,length);
						Bukkit.getPluginManager().callEvent(new DataReachEvent(valueOf(s),data));
					}
				}
			}).start();
	}

}
