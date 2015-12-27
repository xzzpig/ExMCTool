package com.github.xzzpig.exmctool;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import java.util.*;
import com.github.xzzpig.utils.*;
import android.widget.*;
import android.view.View.*;

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
		System.out.println(ob);
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
		System.out.println(autosave);
		if(autosave){
			((CheckBox)this.findViewById(R.id.CheckBox_spw)).setChecked(true);
			((TextView)this.findViewById(R.id.EditText_ip)).setText(data.getString("ip",""));
			((TextView)this.findViewById(R.id.EditText_psw)).setText(data.getString("password",""));
			((TextView)this.findViewById(R.id.EditText_port)).setText(data.getInt("port",0)+"");
		}
    }
	
	public void onASButtonClick(View view){
		Intent intent = new Intent();
		intent.setClass(MainActivity.this,AddServerActivity.class);
		startActivity(intent);
	}
	
	public void onCKClick(View view){
		SharedPreferences data = this.getSharedPreferences("DATA",MODE_PRIVATE);
		data.edit().putBoolean("autosave",((CheckBox)this.findViewById(R.id.CheckBox_spw)).isChecked()).apply();
	}
}
