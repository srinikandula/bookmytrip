package com.tutorialspoint;

import java.util.List;

public class EmployeeAdapter {
	private EmployeeDAO dao;
	private String name;
	public EmployeeAdapter() {
		// TODO Auto-generated constructor stub
	}
	public EmployeeAdapter(String name){
		this.name = name;
	}

	public EmployeeDAO getDao() {
		return dao;
	}

	public void setDao(EmployeeDAO dao) {
		this.dao = dao;
	}
	public List<Employee> findEmployees(){
		return dao.findEmployees();
	}
	
}
