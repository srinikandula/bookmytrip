package com.bookmytrip.service.impl;

import javax.sql.DataSource;

import com.bookmytrip.dao.UserDAO;
import com.bookmytrip.domain.User;
import com.bookmytrip.service.UserService;

public class UserServiceImpl implements UserService {
	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	private DataSource datasource;
	
	public void saveUser(User user){
		UserDAO dao = new UserDAO();
		dao.createUser(user);
	}

}
