package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.cilents.CilentType;
import com.github.xzzpig.exmctool.cilents.Cilent_Player;
import com.github.xzzpig.exmctool.event.*;

import org.bukkit.event.*;

public class AlwaysDataListener implements Listener
{
	@EventHandler
	public void onGetType(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("type"))
			return;
		event.getCilent().setType(CilentType.valueOf(data[1]));
		System.out.println("[ExMCTool]"+event.getCilent().getSocket().getRemoteSocketAddress()+"的类型设为"+data[1]);
	}
	@EventHandler
	public void onGetName(PlayerDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=2||!data[0].equalsIgnoreCase("name"))
			return;
		if(((Cilent_Player)event.getCilent()).setName(data[1]))
			System.out.println("[ExMCTool]"+event.getCilent().getSocket().getRemoteSocketAddress()+"的指向玩家设为"+data[1]);
	}

	@EventHandler
	public void onChangePass(PlayerDataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length!=4||!data[0].equalsIgnoreCase("changepassword"))
			return;
		Cilent_Player cilent = (Cilent_Player)event.getCilent();
		if(!data[1].equalsIgnoreCase(MD5.GetMD5Code(Vars.adminkey))){
			event.getCilent().sendData("changepassword result fail adminkey".getBytes());
			System.out.println("[ExMCTool]"+cilent.getName()+"改密失败(管理密码错误)");
		}
		else if(cilent.password.equalsIgnoreCase("null")||
				cilent.password.equalsIgnoreCase(data[2])){
			cilent.password = data[3];
			cilent.sendData("changepassword result success".getBytes());
			System.out.println("[ExMCTool]"+cilent.getName()+"改密成功");
		}
		else{
			cilent.sendData("changepassword result fail password".getBytes());
			System.out.println("[ExMCTool]"+cilent.getName()+"改密失败(原密码错误)");
		}
		TConfig.saveConfig("ExMCTool","login.yml","login.password."+cilent.getName(),cilent.password);
	}
}
