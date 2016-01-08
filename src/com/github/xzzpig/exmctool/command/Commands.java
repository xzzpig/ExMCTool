package com.github.xzzpig.exmctool.command;

import java.io.UnsupportedEncodingException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.xzzpig.BukkitTools.TString;
import com.github.xzzpig.exmctool.clients.Client_Player;

public class Commands {	
	public static boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
		if(label.equalsIgnoreCase("ExMCTool")||label.equalsIgnoreCase("ex")||label.equalsIgnoreCase("et")){
			return exmctool(sender, command, label, args);
		}
		else if (label.equalsIgnoreCase("exmessage")) {
			return exmessage(sender, command, label, args);
		}
		return false;
	}
	
	public static boolean exmctool(CommandSender sender, Command command,String label, String[] args) {
		String prefix = "[ExMCTool]";
		if(args.length < 1)
			return false;
		if(args[0].equalsIgnoreCase("help")){
			if(sender instanceof Player)
				prefix = TString.Color(3) + prefix + TString.Color("f");
			sender.sendMessage(prefix + "/exmessage [消息] <玩家> -使玩家客户端弹出消息框");
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean exmessage(CommandSender sender, Command command,String label, String[] args) {
		String prefix = "[ExMCTool]",message;
		Client_Player client;
		Player player = null;
		if(sender instanceof Player){
			prefix = TString.Color(3) + prefix + TString.Color("f");
			player = (Player)sender;
		}
		try {
			message = args[0];			
		} catch (Exception e) {
			sender.sendMessage(prefix+"[消息]不能为空");
			return false;
		}
		try {
			player = Bukkit.getPlayer(args[1]);
		} catch (Exception e) {}
		if(player == null){
			sender.sendMessage(prefix+"错误!未知的玩家");
			return false;
		}
		client = Client_Player.valueOf(player);
		if(client == null){
			sender.sendMessage(prefix+"错误!该玩家未有客户端连接");
			return false;
		}
		try {
			client.sendData(("messagebox "+message.replace(' ', '_')).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
