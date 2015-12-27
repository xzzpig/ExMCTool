package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.clients.*;
import com.github.xzzpig.exmctool.event.*;

import org.bukkit.event.*;

public class AlwaysDataListener implements Listener
{
	
	
	@EventHandler
	public void onGetType(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("type"))
			return;
		event.getClient().setType(ClientType.valueOf(data[1]));
		System.out.println("[ExMCTool]"+event.getClient().getSocket().getRemoteSocketAddress()+"的类型设为"+data[1]);
	}

	@EventHandler
	public void onGetName(PlayerDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("name"))
			return;
		if(((Client_Player)event.getClient()).setName(data[1]))
			System.out.println("[ExMCTool]"+event.getClient().getSocket().getRemoteSocketAddress()+"的指向玩家设为"+data[1]);
	}

	@EventHandler
	public void onChangePass(PlayerDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=4||!data[0].equalsIgnoreCase("changepassword"))
			return;
		Client_Player client = (Client_Player)event.getClient();
		if(!data[1].equalsIgnoreCase(MD5.GetMD5Code(Vars.adminkey))){
			event.getClient().sendData("changepassword result fail adminkey".getBytes());
			System.out.println("[ExMCTool]"+client.getName()+"改密失败(管理密码错误)");
		}
		else if(client.password.equalsIgnoreCase("null")||
				client.password.equalsIgnoreCase(data[2])){
			client.password = data[3];
			client.sendData("changepassword result success".getBytes());
			System.out.println("[ExMCTool]"+client.getName()+"改密成功");
		}
		else{
			client.sendData("changepassword result fail password".getBytes());
			System.out.println("[ExMCTool]"+client.getName()+"改密失败(原密码错误)");
		}
		TConfig.saveConfig("ExMCTool","login.yml","login.password."+client.getName(),client.password);
	}

	
	
	
}
