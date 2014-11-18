package com.tutorialspoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainApp {
   public static void main(String[] args) {
    /*  ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      obj.getMessage();*/
	   System.setProperty("security_location", "/home/skandula");
	   long start = System.currentTimeMillis();
	   System.out.println("Starting IOC container ");
	   ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	   System.out.println("Starting IOC container Done");
	   System.out.println(" Time spent "+(System.currentTimeMillis() - start));
	   start = System.currentTimeMillis();
	  
	   EmployeeAdapter adapter = new EmployeeAdapter();
	   EmployeeDAO dao = new EmployeeDAO();
	   adapter.setDao(dao);
	   
	   
	   //instantiate the container
	   
	   /*EmployeeAdapter adapter = new EmployeeAdapter();
	   EmployeeDAO dao = new EmployeeDAO();
	   adapter.setDao(dao);
	   */
	   //EmployeeAdapter adapter = (EmployeeAdapter)context.getBean("myAdapter");
	   
	   for(Employee employee:adapter.findEmployees()){
		   System.out.println(employee);
	   }
	   System.out.println(" Time spent "+(System.currentTimeMillis() - start));
   }
}