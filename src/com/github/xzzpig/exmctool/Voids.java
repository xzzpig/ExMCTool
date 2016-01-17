package com.github.xzzpig.exmctool;
import android.os.*;
import android.widget.*;
import android.content.*;

public class Voids
{
	public static void printToast(Context c,String message){
		try{Looper.prepare();
			Toast.makeText(c,message,Toast.LENGTH_LONG).show();
			Looper.loop();
		}
		catch(Exception e){
			Toast.makeText(c,message,Toast.LENGTH_LONG).show();
		}
	}
	
	public static String sub(String s,String pre,String suf)
	{
		int f = s.indexOf(pre);
		int e = s.indexOf(suf,f);
		return s.substring(f+pre.length(),e);
	}
}
