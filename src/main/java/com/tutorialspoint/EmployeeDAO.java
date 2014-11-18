package com.tutorialspoint;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

	public EmployeeDAO() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Employee> findEmployees(){
		List<Employee> list = new ArrayList<Employee>();
		
		//find the data from database
		list.add(new Employee(1,"one"));
		list.add(new Employee(20,"twenty"));
		list.add(new Employee(15,"fifteen"));
		list.add(new Employee(7,"seven"));
		
		return list;
	}
}
