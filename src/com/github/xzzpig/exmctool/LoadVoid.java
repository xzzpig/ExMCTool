package com.github.xzzpig.exmctool;
import android.app.*;
import android.content.*;
import android.widget.*;

public class LoadVoid
{
	public static ProgressDialog dialog;
	
	public static ProgressDialog show(Context c){
		dialog = ProgressDialog.show(c,"","");
		dialog.setContentView(R.layout.loading);
		return dialog;
	}
	
	public static ProgressDialog setMessage(CharSequence message){
		((TextView)dialog.findViewById(R.id.TextView_loading)).setText(message);
		return dialog;
	}
	
	public static void cancel(){
		dialog.cancel();
	}
}
