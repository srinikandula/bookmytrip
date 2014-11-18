package com.samples;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

enum Cache{
    INSTANCE;
    private Map<String,String> cache= new ConcurrentHashMap<String,String>();
    {
        cache.put("one","ONE");
        cache.put("two","TWO");
    }
    public  Map<String,String>  getRecords(){
        return cache;
    }
}

public class SingletonCache{
    public static void main(String args[]){
    	System.out.println(Cache.INSTANCE.getRecords());
        
    }
}