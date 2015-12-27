package com.github.xzzpig.exmctool;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.exmctool.extevent.*;
import java.io.*;
import java.net.*;
import java.util.*;
import android.os.*;

public class Client extends Thread
{
	public static Client self;
	
	public Socket socket;
	public String ip,id,password;
	public int port;
	public String result;
	public boolean read = true;
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
		InetAddress add = null;
		try{
			add = InetAddress.getByName(ip);
		}
		catch(Exception e){
			result ="无法获取IP";
			return;
		}
		try{
			socket = new Socket(add,port);
		}
		catch(Exception e){
			result ="RCON服务器连接失败";
			return;
		}
		readdata();
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
						}
						catch(Exception e){self =null;return;}
					}
				}
			}).start();
	}
}
