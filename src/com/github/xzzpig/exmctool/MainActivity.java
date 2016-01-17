package com.github.xzzpig.exmctool;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import java.util.*;
import com.github.xzzpig.utils.*;
import android.widget.*;
import android.view.View.*;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.exmctool.listener.*;

public class MainActivity extends Activity
{
    public static Activity self;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		self = this;
        setContentView(R.layout.main);
		Object ob = ObjectFile.load("servers",getFilesDir().getAbsolutePath());
		if(ob != null&&(ob instanceof List)&&((List)ob).size() !=0)
			Vars.servers = (List)ob;
		else{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,AddServerActivity.class);
			startActivity(intent);
		}
		Spinner spinner = ((Spinner)findViewById(R.id.Spinner_server));
		SpinnerAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Vars.servers);
		spinner.setAdapter(adapter);
		spinner.setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1){
					String server = (String)((Spinner)p1).getSelectedItem();
					Vars.servers.remove(server);
					ObjectFile.save("servers",getFilesDir().getAbsolutePath(),Vars.servers);
					Toast.makeText(MainActivity.this,"已删除服务器\n"+server,Toast.LENGTH_LONG).show();
					if(Vars.servers.size() == 0)
						onASButtonClick(p1);
					else
						((Spinner)p1).setSelection(0);
					return true;
				}
		});
		SharedPreferences data = this.getSharedPreferences("DATA",MODE_PRIVATE);
		boolean autosave = data.getBoolean("autosave",false);
		if(autosave){
			((CheckBox)this.findViewById(R.id.CheckBox_spw)).setChecked(true);
			((TextView)this.findViewById(R.id.EditText_id)).setText(data.getString("id",""));
			((TextView)this.findViewById(R.id.EditText_psw)).setText(data.getString("password",""));
			spinner.setSelection((data.getInt("address",0)));
		}
		
		((Button)findViewById(R.id.Button_login)).setOnLongClickListener(new OnLongClickListener(){
				@Override
				public boolean onLongClick(View p1){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,ChoiceActivity.class);
					startActivity(intent);
					return true;
				}
			});
		Event.registListener(new DataListener());
    }
	
	public void onASButtonClick(View view){
		Intent intent = new Intent();
		intent.setClass(MainActivity.this,AddServerActivity.class);
		startActivity(intent);
	}
	public void onBLClick(View view){
		String add,ip,id,pass;
		int port;
		add = (String) ((Spinner)findViewById(R.id.Spinner_server)).getSelectedItem();
		ip = add.split(":")[0];
		port = Integer.valueOf(add.split(":")[1]);
		id = ((TextView)findViewById(R.id.EditText_id)).getText().toString();
		pass = ((TextView)findViewById(R.id.EditText_psw)).getText().toString();
		
		if(((CheckBox)findViewById(R.id.CheckBox_spw)).isChecked()){
			SharedPreferences data = getSharedPreferences("DATA",MODE_PRIVATE);
			data.edit().putString("id",id).apply();
			data.edit().putString("password",pass).apply();
			data.edit().putInt("address",((Spinner)findViewById(R.id.Spinner_server)).getSelectedItemPosition());
		}
		if(Client.self != null){
			Client.self.read = false;
		}
		
		final Client client = new Client(ip,port,id,pass);
		Vars.logindialog =ProgressDialog.show(this, "登录", "登录中。。。");
		client.start();
		final Thread outdata = new Thread(new Runnable(){
				@Override
				public void run(){
					try{
						Thread.sleep(10000);
						if(Vars.logindialog.isShowing())
							Vars.logindialog.cancel();
					}
					catch(InterruptedException e){}
				}
			});
		outdata.start();
		Vars.logindialog.setOnCancelListener(new ProgressDialog.OnCancelListener(){
				@Override
				public void onCancel(DialogInterface p1){
					String result = client.result;
					if(!outdata.isAlive())
						result = "登录超时";
					if(result.equalsIgnoreCase("成功")){
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,ChoiceActivity.class);
						startActivity(intent);
						return;
					}
					Toast.makeText(MainActivity.this,result,0).show();
				}
			});
		/*
		while(client.isAlive()&&outdata.isAlive()){}
		String result = client.result;
		dialog.cancel();
		if(!outdata.isAlive())
			result = "登录超时";
		Toast.makeText(this,result,0).show();*/
	}
	public void onCKClick(View view){
		SharedPreferences data = this.getSharedPreferences("DATA",MODE_PRIVATE);
		data.edit().putBoolean("autosave",((CheckBox)this.findViewById(R.id.CheckBox_spw)).isChecked()).apply();
	}
}
