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
		if(ob != null&&(ob instanceof List))
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
		//((Spinner)findViewById(R.id.Spinner_server)).setAdapter();
    }
	
	public void onASButtonClick(View view){
		Intent intent = new Intent();
		intent.setClass(MainActivity.this,AddServerActivity.class);
		startActivity(intent);
		System.out.println("changesuccess");
	}
}
