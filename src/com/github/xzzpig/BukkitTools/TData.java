package com.github.xzzpig.BukkitTools;
import java.util.*;
import java.util.Map.Entry;

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
	@Override
	public String toString() {
		List<String> ss = new ArrayList<String>();
		Iterator<Entry<String, String>> is = strs.entrySet().iterator();
		while (is.hasNext()){
			Entry<String, String> ise = is.next();
			ss.add(ise.getKey()+"\t"+ise.getValue());
		}
		Iterator<Entry<String, Integer>> ii = ints.entrySet().iterator();
		while (is.hasNext()){
			Entry<String, Integer> iie = ii.next();
			ss.add(iie.getKey()+"\t"+iie.getValue());
		}
		Iterator<Entry<String, Boolean>> ib = boos.entrySet().iterator();
		while (ib.hasNext()){
			Entry<String, Boolean> ibe = ib.next();
			ss.add(ibe.getKey()+"\t"+ibe.getValue());
		}
		Iterator<Entry<String, Object>> io = obs.entrySet().iterator();
		while (io.hasNext()){
			Entry<String, Object> ioe = io.next();
			ss.add(ioe.getKey()+"\t"+ioe.getValue());
		}
		StringBuffer sb = new StringBuffer();
		for(String s:ss){
			sb.append(s+"\n");
		}
		return sb.toString();
	}
}
