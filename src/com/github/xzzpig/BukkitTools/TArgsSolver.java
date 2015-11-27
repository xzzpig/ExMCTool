package com.github.xzzpig.BukkitTools;
import org.bukkit.*;

public class TArgsSolver
{
	private TData data = new TData();
	public TArgsSolver(String arg){
		for(ChatColor c:ChatColor.values())
			arg.replaceAll(c.toString(),"");
		String[] args = arg.split(" ");
		for(String set:args){
			if(set.startsWith("-")){
				String key = TString.sub(set,"-",":");
				String value = set.replaceAll("-"+key+":","");
				data.setString(key,value);
			}
		}
	}
	public TArgsSolver(String[] args){
		for(String set:args){
			for(ChatColor c:ChatColor.values())
				set.replaceAll(c.toString(),"");
			if(set.startsWith("-")){
				String key = TString.sub(set,"-",":");
				String value = set.replaceAll("-"+key+":","");
				data.setString(key,value);
			}
		}
	}
	public String get(String key){
		return data.getString(key);
	}
}
