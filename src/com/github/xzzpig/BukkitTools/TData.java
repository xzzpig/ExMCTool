package com.github.xzzpig.BukkitTools;
import java.util.*;

public class TData
{
	private HashMap<String,String> strs = new HashMap<String,String>();
	private HashMap<String,Integer> ints = new HashMap<String,Integer>();
	private HashMap<String,Boolean> boos = new HashMap<String,Boolean>();
	private HashMap<String,Object> obs = new HashMap<String,Object>();
	
	public TData(){}
	
	public String getString(String key){
		if(!this.strs.containsKey(key))
			return null;
		return this.strs.get(key);
	}
	public TData setString(String key,String value){
		strs.put(key,value);
		return this;
	}
	public HashMap<String,String> getStrings(){
		return this.strs;
	}
	
	public int getInt(String key){
		if(!this.ints.containsKey(key))
			return 0;
		return this.ints.get(key);
	}
	public TData setInt(String key,int value){
		ints.put(key,value);
		return this;
	}
	public HashMap<String,Integer> getInts(){
		return this.ints;
	}
	
	public boolean getBoolan(String key){
		if(!this.boos.containsKey(key))
			return false;
		return this.boos.get(key);
	}
	public TData setBoolean(String key,boolean value){
		boos.put(key,value);
		return this;
	}
	public HashMap<String,Boolean> getBooleans(){
		return this.boos;
	}
	
	public Object getObject(String key){
		if(!this.obs.containsKey(key))
			return null;
		return this.obs.get(key);
	}
	public TData setObject(String key,Object value){
		obs.put(key,value);
		return this;
	}
	public HashMap<String,Object> getaObjects(){
		return this.obs;
	}
}
