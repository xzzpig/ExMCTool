package com.github.xzzpig.exmctool;
import android.os.*;
import android.widget.*;
import android.content.*;

public class Voids
{
	private static void buildView(int view){
		switch(view){
			case R.layout.main:
				break;
		}
	}

	public static void printToast(Context c,String message){
		try{Looper.prepare();
			Toast.makeText(c,message,Toast.LENGTH_LONG).show();
			Looper.loop();
		}
		catch(Exception e){
			Toast.makeText(c,message,Toast.LENGTH_LONG).show();
		}

	}
}
