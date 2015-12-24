package com.github.xzzpig.exmctool.cilents;
import java.net.*;
import java.util.*;
import java.io.*;

import org.bukkit.*;

import com.github.xzzpig.exmctool.Debuger;
import com.github.xzzpig.exmctool.event.*;

public class Cilent
{
	public static List<Cilent> cilents = new ArrayList<Cilent>();
	
	private Cilent self = this;
	protected Socket s;
	protected byte[] data;
	protected List<CilentType> types = new ArrayList<CilentType>();
	protected HashMap<CilentType,Cilent> subcilent = new HashMap<CilentType,Cilent>();
	private boolean read = true;
	
	public Cilent(){}
	public Cilent(CilentType type,Cilent superc){
		superc.subcilent.put(type,this);
		this.types.add(type);
		this.s = superc.s;
	}
	public Cilent(Socket s){
		cilents.add(this);
		types.add(CilentType.Basic);
		this.s = s;
		readdata();
		askForType();
		removeUnConnect();
	}
	
	public static Cilent valueOf(Socket s){
		for(Cilent c:cilents)
		if(c.s == s)
			return c;
		return null;
	}
	private void askForType(){
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
				if(types.size() == 1){
					types.add(CilentType.Unknown);
					System.out.println("[ExMCTool]"+s.getRemoteSocketAddress()+"的类型设为"+CilentType.Unknown+self);
				}
			}
		}).start();
		while(this.types.size() == 1){}
	}
	
	public boolean isType(CilentType type) {
		return types.contains(type);
	}

	public Cilent setType(CilentType type) {
		this.types.add(type);
		try{
			Cilent cil = (Cilent) Class.forName("com.github.xzzpig.exmctool.cilents.Cilent_"+type).newInstance();
			cil.renew(this);
		}
		catch(Exception e){e.printStackTrace();}
		return this;
	}
	
	public Socket getSocket(){
		return s;
	}
	
	public Cilent getSubCilet(CilentType type){
		if(subcilent.containsKey(type))
			return subcilent.get(type);
		return null;
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
							Debuger.print(new String(data));
							Bukkit.getPluginManager().callEvent(new DataReachEvent(self,data));
							for(CilentType cy:CilentType.values()){
								if(subcilent.containsKey(cy)){
									Cilent cil = subcilent.get(cy);
									cil.data = data;
									cil.readdata();
								}
							}
						} catch (Exception e) {Cilent.removeUnConnect();}
					}
				}
			}).start();
	}
	
	public void remove(){
		for(Cilent cil:cilents){
			if(cil.s != s)
				continue;
			cilents.remove(cil);
			cil.read = false;
		}
	}
	
	public static void removeUnConnect(){
		for(Cilent c:cilents)
			if(c.getSocket().isClosed())
				c.remove();
	}
	
	public void renew(Cilent c){}
}
