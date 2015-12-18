package com.github.xzzpig.exmctool.listener;
import org.bukkit.event.*;

import com.github.xzzpig.exmctool.event.*;

public class AlwaysDataListener implements Listener
{
	@EventHandler
	public void onGetType(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length !=2||!data[0].equalsIgnoreCase("type"))
			return;
		if(event.getCilent().setType(data[1]));
		System.out.println("[ExMCTool]"+event.getCilent().getSocket().getRemoteSocketAddress()+"的类型设为"+event.getCilent().getType());
	}
	@EventHandler
	public void onGetName(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(data.length !=2||!data[0].equalsIgnoreCase("name"))
			return;
		if(event.getCilent().setName(data[1]))
			System.out.println("[ExMCTool]"+event.getCilent().getSocket().getRemoteSocketAddress()+"的指向玩家设为"+data[1]);
	}
}
