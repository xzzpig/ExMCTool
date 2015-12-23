package com.github.xzzpig.exmctool;
import java.net.*;
import java.util.*;
import java.io.*;

import org.bukkit.*;

import com.github.xzzpig.exmctool.event.*;

public class Cilent
{
	public static List<Cilent> cilents = new ArrayList<Cilent>();
	
	protected Socket s;
	protected byte[] data;
	protected CilentType type;
	private boolean read = true;
	
	public Cilent(Socket s){
		cilents.add(this);
		this.s = s;
		readdata();
		if(this instanceof Cilent_Player){
			type = CilentType.Player;
			return;
		}
		askForType();
		if(type == CilentType.Player)
			new Cilent_Player(this);
		
		removeUnConnect();
	}
	
	public static Cilent valueOf(Socket s){
		for(Cilent c:cilents)
		if(c.s == s)
			return c;
		return null;
	}
	private void askForType(){
		this.type = null;
		try{
			s.getOutputStream().write("type".getBytes());
		}
		catch(IOException e){
			System.out.println("[ExMCTool]获取客户端类型请求发送失败");
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {e.printStackTrace();}
				if(type == null){
					type = CilentType.Unknown;
					System.out.println("[ExMCTool]"+s.getRemoteSocketAddress()+"的类型设为"+getType());
				}
			}
		}).start();
		while(this.type == null){}
	}
	
	public CilentType getType() {
		return type;
	}

	public boolean setType(CilentType type) {
		this.type = type;
		return true;
	}
	public boolean setType(String type) {
		this.type = CilentType.valueOf(type);
		if(this.type == null)
			this.type = CilentType.Unknown;
		return true;
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public void sendData(byte[] data){
		try{
			s.getOutputStream().write(data);
			Thread.sleep(5);
		}
		catch(Exception e){
			if(s.isConnected())
				System.out.println("[ExMCTool]Wrong!发送数据到"+s.getRemoteSocketAddress()+"失败");
			else {
				this.remove();
			}
		}
	}
	
	protected void readdata(){
		new Thread(new Runnable(){
				@Override
				public void run(){
					while(read){
						if(s.isClosed()){
							remove();
							return;
						}
						byte[] buf = new byte[1024*1024];
						int length = 0;
						try{
							length = s.getInputStream().read(buf);
						}
						catch(IOException e){}
						try {
							data = Arrays.copyOf(buf,length);
							Bukkit.getPluginManager().callEvent(new DataReachEvent(valueOf(s),data));
						} catch (Exception e) {Cilent.removeUnConnect();}
					}
				}
			}).start();
	}
	
	public void remove(){
		cilents.remove(this);
		read = false;
	}
	
	public static void removeUnConnect(){
		for(Cilent c:cilents)
			if(c.getSocket().isClosed())
				c.remove();
	}
}
