package com.github.xzzpig.exmctool.clients;

import com.github.xzzpig.exmctool.event.*;

import org.bukkit.*;

public class Client_Chatable extends Client
{
	private Client_Chatable self = this;
	public Client superc;
	
	public Client_Chatable(){}
	public Client_Chatable(Client client){
		super(client.s);
		superc = client;
		this.types.add(ClientType.Chatable);
		client.subcilent.put(ClientType.Chatable,this);
	}

	public static Client_Chatable valueOf(Client client){
		for(Client c:clients){
			if(!(c instanceof Client_Chatable))
				continue;
			if(client.s == c.s)
				return (Client_Chatable)c;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public String getName(){
		String sayer = "未知来源<"+s.getInetAddress().getHostName()+">";
		if(superc.isType(ClientType.Player)){
			sayer = ((Client_Player)superc.getSubCilet(ClientType.Player)).getName();
			if(Bukkit.getPlayer(sayer)!=null)
				return Bukkit.getPlayer(sayer).getName();
			sayer = "[远程消息]"+sayer;
		}
		return sayer;
	}

	@Override
	protected void readdata(){
		Bukkit.getPluginManager().callEvent(new ChatDataReachEvent(self,data));
	}

	@Override
	public void renew(Client c){
		new Client_Chatable(c);
	}
}
