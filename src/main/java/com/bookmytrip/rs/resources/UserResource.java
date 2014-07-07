package com.bookmytrip.rs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.bookmytrip.dao.UserDAO;
import com.bookmytrip.domain.User;

@Path ("user")
public class UserResource {
	
	@Path("test")
	public String method(@FormParam(value = "name") String name){
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String save(User u){
		UserDAO dao = new UserDAO();
		dao.createUser(u);
		return String.valueOf(u.getId());
	}
	
}
