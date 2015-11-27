package com.github.xzzpig.BukkitTools;
import java.util.*;

public class TCommandHelp
{
	@SuppressWarnings("unused")
	private String command,describe,useage,var;
	private List<TCommandHelp> subs = new ArrayList<TCommandHelp>();
	private TCommandHelp uphelp;

	public TCommandHelp(String command,String describe,String useage){
		this.command = command;
		this.describe = describe;
		this.useage = useage;
	}
	private TCommandHelp(String command,String describe,String useage,String var,TCommandHelp uphelp){
		if(command == null)
			command = "error";
		this.command = command;
		if(describe == null)
			describe = "无";
		this.describe = describe;
		if(useage == null)
			useage = "无";
		this.useage = useage;
		if(var == null)
			var = "";
		this.var = var;
		this.uphelp = uphelp;
	}

	public static TCommandHelp valueOf(TCommandHelp basichelp,String command){
		for(TCommandHelp ch:basichelp.getAllSubs())
			if(ch.toString().equalsIgnoreCase(command))
				return ch;
		return basichelp;
	}

	public TCommandHelp getFinalUpHelp(){
		TCommandHelp ch = this;
		while(ch.uphelp != null)
			ch = ch.uphelp;
		return ch;
	}

	public TCommandHelp addSubTCommandHelp(String command,String describe,String useage,String var){
		TCommandHelp sub = new TCommandHelp(this.command+" "+command,describe,useage,var,this);
		subs.add(sub);
		return sub;
	}
	public TCommandHelp getSubTCommandHelp(String command){
		for(TCommandHelp c:subs){
			if(command.equalsIgnoreCase(c.toString()))
				return c;
			if(c.toStrings()[c.toStrings().length-1].equalsIgnoreCase(command))
				return c;
		}
		return this;
	}
	public TCommandHelp[] getSubTCommandHelps(){
		return subs.toArray(new TCommandHelp[0]);
	}
	public List<TCommandHelp> getAllSubs()
	{
		List<TCommandHelp> sublist = new ArrayList<TCommandHelp>();
		for (TCommandHelp pre:this.subs)
		{
			sublist.add(pre);
			List<TCommandHelp> sub = pre.getAllSubs();
			if (sub != null)
			{
				for (TCommandHelp sub2:sub)
				{
					if (!sublist.contains(sub2))
						sublist.add(sub2);
				}
			}
		}
		return sublist;
	}

	@Override
	public String toString(){
		return command;
	}
	public String[] toStrings(){
		return command.split(" ");
	}
}
