package com.github.xzzpig.exmctool.clients;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.event.*;
import java.net.*;
import org.bukkit.*;

public class Client_App extends Client
{
	private Client_App self = this;
	public Client superc;
	public Client_Player cp;
	
	public Client_App(){}
	public Client_App(Client client){
		super(ClientType.App,client);
		superc = client;
		cp = new Client_Player(superc);
		cp.startLoginExam();
		cp.uncheckkey = MD5.GetMD5Code(Vars.loginkey);
	}
	
	public static Client_App valueOf(Socket s){
		for(Client c:clients){
			if(!(c instanceof Client_App))
				continue;
			if(c.s == s)
				return (Client_App)c;
		}
		return null;
	}
	
	public Client_Player getPlayerClient(){
		return cp;
	}
	
	@Override
	protected void readdata(){
		Bukkit.getPluginManager().callEvent(new AppDataReachEvent(self,data));
	}

	@Override
	public void renew(Client c){
		new Client_App(c);
	}
}
