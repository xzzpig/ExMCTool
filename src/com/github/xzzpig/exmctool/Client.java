package com.github.xzzpig.exmctool;
import android.app.*;
import android.os.*;
import com.github.xzzpig.exmctool.*;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.exmctool.extevent.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread
{
	public static Client self;
	
	public Socket socket;
	public String ip,id,password;
	public int port;
	public String result;
	public boolean read = true,ask;
	public byte[] data;
	
	Client(String ip,int port,String id,String password){
		self = this;
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.password = password;
	}

	@Override
	public void run(){
		/*
		Looper.prepare();
		
		Looper.loop();
		*/
		if(id.equalsIgnoreCase("")||password.equalsIgnoreCase("")){
			result = "账号密码不能为空";
			return;
		}
		//dialog.setMessage("开始尝试连接服务器插件");
		InetAddress add = null;
		try{
			add = InetAddress.getByName(ip);
		}
		catch(Exception e){
			result ="无法获取IP";
			return;
		}
		//dialog.setMessage("开始尝试连接服务器插件\nIP获取成功");
		try{
			socket = new Socket(add,port);
		}
		catch(Exception e){
			result ="连接服务器插件失败";
			return;
		}
		readdata();
		//dialog.setMessage("成功连接服务器插件\n开始验证账号密码");
		
		try{
			//String id = ((TextView)MainActivity.self.findViewById(R.id.EditText_id)).getText().toString();
			//String password = ((TextView)MainActivity.self.findViewById(R.id.EditText_psw)).getText().toString();
			socket.getOutputStream().write("type App".getBytes());
			socket.getOutputStream().write(("player "+id).getBytes());
			Thread.sleep(100);
			socket.getOutputStream().write(("login player "+id).getBytes());
			socket.getOutputStream().write(("login paasword "+password).getBytes());
			askPerpare();
			socket.getOutputStream().write(("canlogin"+password).getBytes());
			String ret = new String(askAnswer());
			if(ret.equalsIgnoreCase("login pass")){
				result = "成功";
				return;
			}
			result = "密码错误";
		}
		catch(Exception e){}
	}
	
	public void askPerpare(){
		this.ask = true;
	}
	public byte[] askAnswer(){
		while(ask){}
		return data;
	}
	
	protected void readdata(){
		new Thread(new Runnable(){
				@Override
				public void run(){
					while(read){
						if(socket.isClosed()){
							self = null;
							return;
						}
						byte[] buf = new byte[1024*1024];
						int length = 0;
						try{
							length = socket.getInputStream().read(buf);
						}
						catch(IOException e){}
						try{
							data = Arrays.copyOf(buf,length);
							Looper.prepare();
							System.out.println("数据到达:");
							System.out.println(Arrays.toString(data));
							System.out.println(new String(data));
							Event.callEvent(new DataReachEvent(self,data));
							Looper.loop();
							if(ask)ask=false;
						}
						catch(Exception e){self =null;return;}
					}
				}
			}).start();
	}
}
