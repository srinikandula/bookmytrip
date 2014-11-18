package com.bookmytrip.service.impl;

import javax.sql.DataSource;

import com.bookmytrip.dao.UserDAO;
import com.bookmytrip.domain.User;
import com.bookmytrip.service.UserService;

public class UserServiceImpl implements UserService {
	private DataSource datasource;
	private UserDAO dao;
	
	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	public Integer saveUser(User user){
		if(user == null)
			return null;
		System.out.println("saving "+user.getId());
		return dao.createUser(user);
	}

}
