package com.github.xzzpig.exmctool.listener;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.exmctool.cusevent.*;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.message.*;
import android.widget.*;
import android.text.*;
import android.os.*;

public class DataListener implements Listener
{
	@EventHandler
	public void onServerInfo(DataReachEvent event){
		String[] datas = event.getStringData().split(" ");
		if(!datas[0].equalsIgnoreCase("serverinfo"))
			return;
		Vars.infos.put("address",datas[1]);
		Vars.infos.put("c/mplayer",datas[2]);
	}
	
	@EventHandler
	public void onChatMessage(DataReachEvent event){
		try{
		String[] datas = event.getStringData().split(" ");
		if(!datas[0].equalsIgnoreCase("chat"))
			return;
			final ChatMessage message = new ChatMessage(event.getStringData());
		Vars.chat.add(message);
		if(ChatActivity.self != null){
			ChatActivity.self.runOnUiThread(new Runnable(){
					@Override
					public void run(){
						((EditText)ChatActivity.self.findViewById(R.id.EditText_chatprint)).getText().append("\n").append(Html.fromHtml(message.getColorableMessage()));
					}
				});
		}
		}catch(Exception e){System.out.println("聊天消息获取错误:"+e);}
	}
}
