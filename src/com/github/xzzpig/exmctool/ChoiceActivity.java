package com.github.xzzpig.exmctool;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.github.xzzpig.exmctool.*;

public class ChoiceActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice);
		new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						Client.self.socket.getOutputStream().write("serverinfo".getBytes());
					}
					catch(Exception e){System.out.println(e);}
					while(!Vars.infos.containsKey("address")){}
					while(!Vars.infos.containsKey("c/mplayer")){}
					((TextView)findViewById(R.id.TextView_serverinfo)).setText("服务器ip:"+Vars.infos.get("address")+"\n在线人数:"+Vars.infos.get("c/mplayer"));
				}
			}).start();
	}
	
	public void onChatClick(View view){
		Intent intent = new Intent();
		intent.setClass(ChoiceActivity.this,ChatActivity.class);
		startActivity(intent);
	}
}
