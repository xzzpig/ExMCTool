package com.github.xzzpig.exmctool.event;
import java.lang.reflect.*;
import java.util.*;
import java.lang.annotation.*;

public class Event
{
	private static final HashMap<Type,List<EventMethod>> events = new HashMap<Type,List<EventMethod>>();

	public static final void registListener(Listener listener){
		for(Method meth: listener.getClass().getMethods()){
			for(Annotation ann:meth.getAnnotations()){
				if(ann.toString().contains("EventHandler()")){
					try{
						Type type = meth.getGenericParameterTypes()[0];
						if(!events.containsKey(type))
							events.put(type,new ArrayList<EventMethod>());
						events.get(type).add(new EventMethod(meth,listener));
						System.out.println(type+"|"+meth.getName());
					}
					catch(Exception e){}
				}
			}
		}
	}

	public static final void callEvent(Event event){
		Type eventtype = event.getClass();
		if(!events.containsKey(eventtype))return;
		for(EventMethod em:events.get(eventtype)){
			try{
				System.out.println(em.method.getName());
				em.method.invoke(em.listener,new Object[]{event});
			}
			catch(Exception e){System.out.println("触发事件错误"+e);}
		}
	}
}

class EventMethod
{
	Method method;
	Listener listener;

	EventMethod(Method method,Listener listener){
		this.method = method;
		this.listener = listener;
	}
}
