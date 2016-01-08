package com.github.xzzpig.exmctool.clients;
import java.net.*;
import java.util.*;
import java.io.*;

import org.bukkit.*;

import com.github.xzzpig.exmctool.Debuger;
import com.github.xzzpig.exmctool.event.*;

public class Client
{
	public static List<Client> clients = new ArrayList<Client>();

	private Client self = this;
	protected Socket s;
	protected byte[] data;
	protected List<ClientType> types = new ArrayList<ClientType>();
	protected HashMap<ClientType,Client> subclient = new HashMap<ClientType,Client>();
	private boolean read = true,acceptlog;

	public Client(){}
	public Client(ClientType type,Client superc){
		superc.subclient.put(type,this);
		this.types.add(type);
		this.s = superc.s;
	}
	public Client(Socket s){
		clients.add(this);
		types.add(ClientType.Basic);
		this.s = s;
		readdata();
		askForType();
		removeUnConnect();
	}

	public static Client valueOf(Socket s){
		for(Client c:clients)
			if(c.s==s)
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
				public void run(){
					try{
						Thread.sleep(5000);
					}
					catch(InterruptedException e){e.printStackTrace();}
					if(types.size()==1){
						types.add(ClientType.Unknown);
						System.out.println("[ExMCTool]"+s.getRemoteSocketAddress()+"的类型设为"+ClientType.Unknown);
					}
				}
			}).start();
		while(this.types.size()==1){}
	}

	public Client setAcceptLog(boolean acceptlog){
		this.acceptlog = acceptlog;
		return this;
	}
	public boolean isAcceptLog(){
		return acceptlog;
	}

	public String getPossibleName(){
		if(this.isType(ClientType.Player))
			return ((Client_Player)getSubCliet(ClientType.Player)).getName();
		return "未知来源<"+s.getInetAddress().getHostName()+">";
	}
	
	public boolean isType(ClientType type){
		return types.contains(type);
	}

	public Client setType(ClientType type){
		this.types.add(type);
		try{
			Client cli = (Client) Class.forName("com.github.xzzpig.exmctool.clients.Client_"+type).newInstance();
			cli.renew(this);
		}
		catch(Exception e){e.printStackTrace();}
		return this;
	}

	public Socket getSocket(){
		return s;
	}

	public Client getSubCliet(ClientType type){
		if(subclient.containsKey(type))
			return subclient.get(type);
		return null;
	}

	public void sendData(byte[] data){
		try{
			s.getOutputStream().write(data);
			s.getOutputStream().write(new byte[]{1,1,1,1});
		}
		catch(Exception e){
			this.remove();
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
							data = Arrays.copyOf(buf,length);
							for(String d : new String(data).split("###")){
								Debuger.print(d);
								Bukkit.getPluginManager().callEvent(new DataReachEvent(self,d.getBytes()));
								for(ClientType cy:ClientType.values()){
									if(subclient.containsKey(cy)){
										Client cli = subclient.get(cy);
										cli.data = d.getBytes();
										cli.readdata();
									}
								}
							}
						}
						catch(Exception e){remove();Client.removeUnConnect();return;}
					}
				}
			}).start();
	}

	public void remove(){
		System.out.println("[ExMCTool]客户端"+getPossibleName()+"已移除");
		clients.remove(this);
		read = false;
		try {
			for(Client cli:subclient.keySet().toArray(new Client[0])){
				clients.remove(cli);
				cli.read = false;
			}
		} catch (Exception e) {}
		
	}

	public static void removeUnConnect(){
		for(Client c:clients)
			if(c.getSocket().isClosed())
				c.remove();
	}

	public void renew(Client c){}
}
