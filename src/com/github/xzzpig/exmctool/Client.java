package com.github.xzzpig.exmctool;
import android.os.*;
import com.github.xzzpig.exmctool.event.*;
import com.github.xzzpig.exmctool.extevent.*;
import com.github.xzzpig.utils.*;
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
		if(id.equalsIgnoreCase("")||password.equalsIgnoreCase("")){
			result = "账号密码不能为空";
			dialogCancel();
			return;
		}
		Vars.logindialog.setMessage("开始尝试连接服务器插件");
		InetAddress add = null;
		try{
			add = InetAddress.getByName(ip);
		}
		catch(Exception e){
			result ="无法获取IP";
			dialogCancel();
			return;
		}
		Vars.logindialog.setMessage("开始尝试连接服务器插件\nIP获取成功");
		try{
			socket = new Socket(add,port);
		}
		catch(Exception e){
			result ="连接服务器插件失败";
			dialogCancel();
			return;
		}
		Vars.logindialog.setMessage("成功连接服务器插件\n开始验证账号密码");
		
		try{
			//String id = ((TextView)MainActivity.self.findViewById(R.id.EditText_id)).getText().toString();
			//String password = ((TextView)MainActivity.self.findViewById(R.id.EditText_psw)).getText().toString();
			askPerpare();
			String loginmessage = 
				"type App"+
				"###name "+id+
				"###login player "+MD5.GetMD5Code(id)+
				"###login password "+MD5.GetMD5Code(password)+
				"###canlogin";
			socket.getOutputStream().write(loginmessage.getBytes());
			socket.getOutputStream().flush();
			/*
			socket.getOutputStream().write("###".getBytes());
			socket.getOutputStream().write(("player "+id).getBytes());
			socket.getOutputStream().write("###".getBytes());
			socket.getOutputStream().write(("login player "+MD5.GetMD5Code(id)).getBytes());
			socket.getOutputStream().write("###".getBytes());
			socket.getOutputStream().write(("login paasword "+MD5.GetMD5Code(password)).getBytes());
			socket.getOutputStream().write("###".getBytes());
			*/
//			Thread.sleep(1000);
//			askPerpare();
//			socket.getOutputStream().write(("canlogin").getBytes());
			String ret="";
			while((!ret.contains("login deny")&&(!ret.contains("login pass")))){
				ret = new String(read());
				System.out.println("ret:"+ret);
			}
			if(ret.contains("login pass")){
				result = "成功";
				readdata();
				dialogCancel();
				return;
			}
			result = "密码错误";
			dialogCancel();
		}
		catch(Exception e){
			System.out.println("数据错误"+e);
		}
	}
	
	public void askPerpare(){
		this.ask = true;
	}
	public byte[] askAnswer(){
		while(ask){}
		return data;
	}
	
	private void dialogCancel(){
		Vars.logindialog.cancel();
	}
	
	protected void readdata(){
		new Thread(new Runnable(){
				@Override
				public void run(){
					while(read){
						System.out.println("new loop");
						if(socket.isClosed()){
							System.out.println("服务端已关闭");
							return;
						}
						byte[] buf = new byte[1024*1024];
						int length = 0;
						try{
							length = socket.getInputStream().read(buf);
						}
						catch(IOException e){System.out.println("接受数据错误"+e);}
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
						catch(Exception e){System.out.println("接受数据错误"+e);return;}
					}
				}
			}).start();
	}
	
	private byte[] read(){
		byte[] buf = new byte[1024*1024];
		int length = 0;
		try{
			length = socket.getInputStream().read(buf);
		}
		catch(IOException e){System.out.println("接受数据错误"+e);}
		try{
			data = Arrays.copyOf(buf,length);
			return data;
		}catch(Exception e){}
		return null;
	}
}
