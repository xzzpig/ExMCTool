package com.github.xzzpig.exmctool;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.github.xzzpig.utils.*;
import android.content.*;
import java.util.*;

public class AddServerActivity extends Activity
{
	public static Activity self;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addserver);
		self = this;
		findViewById(R.id.Button_addserver2).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1){
					String ip = ((TextView)findViewById(R.id.EditText_ip)).getText().toString();
					int port = Integer.valueOf("0"+((TextView)findViewById(R.id.EditText_port)).getText().toString());
					if(ip.equalsIgnoreCase("")||port==0){
						Toast.makeText(AddServerActivity.this,"请输入服务器 IP 或 端口 ",Toast.LENGTH_SHORT).show();
						return;
					}
					Vars.servers.add(ip+":"+port);
					
					ObjectFile.save("servers",getFilesDir().getAbsolutePath(),Vars.servers);
					if(Vars.servers.size() == 1)
						((Spinner)findViewById(R.id.Spinner_server)).setSelection(0);
					Toast.makeText(MainActivity.self,"服务器已保存\n长按选项卡删除服务器",Toast.LENGTH_LONG).show();
					onBackPressed();
				}
		});
	}

	@Override
	public void onBackPressed(){
		if(Vars.servers.size() == 0){
			MainActivity.self.finish();
			this.finish();
		}
		super.onBackPressed();
	}
}
