package com.github.xzzpig.exmctool.message;
import com.github.xzzpig.exmctool.*;

public class ChatMessage extends Message
{
	String sayer,message,time;
	
	public ChatMessage(String origenmessage){
		sayer = Voids.sub(origenmessage,"<Player>","<Player/>");
		message = Voids.sub(origenmessage,"<Message>","<Message/>");
		time = Voids.sub(origenmessage,"<Time>","<Time/>");
	}
	
	public String getSayer(){
		return sayer;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getTime(){
		return time;
	}
	
	@Override
	public String toString(){
		return time+"\t"+sayer+":\t"+message;
	}
	
}
