package com.github.xzzpig.exmctool.cilents;

import com.github.xzzpig.exmctool.event.*;

import org.bukkit.*;

public class Cilent_Chatable extends Cilent
{
	private Cilent_Chatable self = this;
	public Cilent superc;
	
	public Cilent_Chatable(){}
	public Cilent_Chatable(Cilent cilent){
		super(cilent.s);
		superc = cilent;
		this.types.add(CilentType.Chatable);
		cilent.subcilent.put(CilentType.Chatable,this);
	}

	public static Cilent_Chatable valueOf(Cilent cilent){
		for(Cilent c:cilents){
			if(!(c instanceof Cilent_Chatable))
				continue;
			if(cilent.s == c.s)
				return (Cilent_Chatable)c;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public String getName(){
		String sayer = "未知来源<"+s.getInetAddress().getHostName()+">";
		if(superc.isType(CilentType.Player)){
			sayer = ((Cilent_Player)superc.getSubCilet(CilentType.Player)).getName();
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
	public void renew(Cilent c){
		new Cilent_Chatable(c);
	}
}
