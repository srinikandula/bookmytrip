package com.examples.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.examples.domain.User;

import javax.sql.DataSource;

public class UserServiceImpl implements UserService {
	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	private DataSource datasource;
	
	public void saveUser(User user) {
		//Persist the user object here. 
		try {
			Connection conn = datasource.getConnection();
			System.out.println("got connection");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User added successfully");
		
	}

}
