package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.BukkitTools.logutil.*;
import com.github.xzzpig.exmctool.clients.*;
import com.github.xzzpig.exmctool.event.*;

import java.io.*;
import java.text.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.util.*;

import com.github.xzzpig.BukkitTools.*;
import com.github.xzzpig.exmctool.*;

public class LogListener implements Listener
{
	{
		System.out.println("[ExMCTool]LOG记录已开启");
	}
	
	private static File 
	log = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/log/latest.log"),
	dir = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/log");
	private static FileOutputStream fout;
	private static DateFormat date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	private boolean first = true;
	static{
		try{
			dir.mkdirs();
			if(log.exists()){
				File tempf = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/log/"+date.format(new Date())+".log");
				FileUtil.copy(log,tempf);
				log = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/log/latest.log");
				log.delete();
				log.createNewFile();
			}
			else log.createNewFile();
			fout = new  FileOutputStream(log,false);
		}
		catch(Exception e){e.printStackTrace();}
	}

	@EventHandler
	public void onPrintLog(LogPrintEvent event){
		if(event.getMessage().contains("********")){
			return;
		}
		try{
			fout.write(("\n"+event.getTime()+" ["+event.getLevel()+" ] "+event.getMessage().replaceAll(" ","_")).getBytes());
			fout.flush();
			if(first){
				fout.close();
				fout = new  FileOutputStream(log,true);
				first = false;
			}
		}
		catch(Exception e){e.printStackTrace();}
		for(Client c:Client.clients)
			if(c.isAcceptLog())
				c.sendData(("log "+event.getTime()+" ["+event.getLevel()+" ] "+event.getMessage().replaceAll(" ","_")).getBytes());
	}

	@EventHandler
	public void onAskLog(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		Debuger.print(Arrays.toString(data));
		if(!data[0].equalsIgnoreCase("getlog"))
			return;
		Client client = event.getClient();
		int amount = -1;
		try{
			amount = Integer.valueOf(data[1]);
		}
		catch(Exception exp){}
		File logfile = new File(Bukkit.getPluginManager().getPlugin("ExMCTool").getDataFolder()+"/log/latest.log");
		List<String> chatlogs = new ArrayList<String>();
		try{
			FileInputStream fin = new FileInputStream(logfile);
			Scanner scanner = new Scanner(fin);
			while(scanner.hasNextLine()){
				String message = scanner.nextLine();
				if(amount==-1)
					client.sendData(("log "+message).getBytes());
				else if(chatlogs.size()>=amount)
					chatlogs.remove(0);
				chatlogs.add(message);
			}
			if(amount!=-1)
				for(String message:chatlogs)
					client.sendData(("log "+message).getBytes());
			scanner.close();fin.close();
		}
		catch(Exception e){}
	}

	@EventHandler
	public void onChangeLogState(DataReachEvent event){
		String[] data = event.getStringData().split(" ");
		if(!data[0].equalsIgnoreCase("changelogstate"))
			return;
		Client client = event.getClient();
		if(!data[1].equalsIgnoreCase(MD5.GetMD5Code(Vars.logkey))){
			event.getClient().sendData("changelogstate result fail logkey".getBytes());
			System.out.println("[ExMCTool]"+client.getPossibleName()+"更改log状态失败(log密码错误)");
		}
		try{
			client.setAcceptLog(Boolean.valueOf(data[2]));
			event.getClient().sendData("changelogstate result success".getBytes());
			System.out.println("[ExMCTool]"+client.getPossibleName()+"更改log状态为:"+data[2]);
		}catch(Exception e){}
	}
}
