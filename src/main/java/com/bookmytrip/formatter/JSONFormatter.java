package com.bookmytrip.formatter;

import com.google.gson.Gson;

public class JSONFormatter {
	
	public static Object toObject(String jsonString,Class classType){
		Object obj = null;
		try{
		Gson gson = new Gson();
		obj = gson.fromJson(jsonString, classType);
		return obj;
		}catch(Exception e){
			return null;
		}
	}

}
