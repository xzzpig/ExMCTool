package com.github.xzzpig.exmctool;
import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import com.github.xzzpig.exmctool.message.*;
import android.widget.*;
import android.text.*;

public class ChatActivity extends Activity
{
	public static ChatActivity self;
	int chatnumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		self = this;
		LoadVoid.show(this);
		LoadVoid.setMessage("正在获取聊天记录。。。");
		Vars.chat.clear();
		Client.self.sendData("getchat".getBytes());
		new Thread(new Runnable(){
				@Override
				public void run(){
					while(Vars.chat.size() == 0){}
					LoadVoid.cancel();
				}
			}).start();
		String message = "";
		for(ChatMessage chat:Vars.chat){
			message = message + "\n" + chat.getColorableMessage();
		}
		((EditText)findViewById(R.id.EditText_chatprint)).setText(Html.fromHtml(message));
		
	}
	
	
	public void onSendChatClick(View view){
		TextView chattext = (EditText) findViewById(R.id.EditText_chat);
		String chatmessage = chattext.getText().toString().replace('&','§');
		chattext.setText("");
		Client.self.sendData(("chat "+chatmessage).getBytes());
	}
	
//	public void freshMessage(){
//		runOnUiThread(new Runnable(){
//				@Override
//				public void run(){
//					while(true){
//						try{
//						Thread.sleep(500);
//						if(chatnumber == Vars.chat.size())
//							continue;
//						String message = "";
//						for(ChatMessage chat:Vars.chat)
//							message = message + "\n" + chat.getColorableMessage();
//						((EditText)findViewById(R.id.EditText_chatprint)).setText(Html.fromHtml(message));
//						}catch(Exception e){System.out.println("更新聊天消息错误:"+e);}
//					}
//				}
//			});
//	}
}
