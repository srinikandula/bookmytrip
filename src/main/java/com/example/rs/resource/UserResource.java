package com.example.rs.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
@Path ("user")
public class UserResource {
	@Path("test")
	public String method(@FormParam(value = "name") String name){
		return null;
	}
	
	
	@POST
	public String save(){
		return "saved";
	}
	
}
