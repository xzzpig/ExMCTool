package com.github.xzzpig.utils;
import java.io.*;
public class ObjectFile
{
	public static void save(String name,String path,Object object){
		if(!(path.endsWith("/")||path.endsWith("\\")))
			path = path + "/";
		File file = new File(path+name+".object");
		try{
			if(!file.exists())
				file.createNewFile();
		}catch(IOException e){System.out.println(e);return;}
		try{
			FileOutputStream fout=new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(object);
			out.flush();
			out.close();
			fout.flush();
			fout.close();
			System.out.println(name+"文件已保存");
		}
		catch(Exception e){System.out.println(e);}
	}
	public static Object load(String name,String path){
		if(!(path.endsWith("/")||path.endsWith("\\")))
			path = path + "/";
		File file = new File(path+name+".object");
			if(!file.exists())
				return null;
		try{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fin);
			Object object = in.readObject();
			in.close();
			fin.close();
			return object;
		}
		catch(Exception e){}
		return null;
	}
}
