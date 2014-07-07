package com.bookmytrip.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bookmytrip.domain.User;
import com.bookmytrip.util.HibernateManager;

public class UserDAO {
	public Integer createUser(User u){
		SessionFactory factory = HibernateManager.getSessionFactory();
		Session  session=  factory.openSession();
		session.getTransaction().begin();
		Integer id =  (Integer) session.save(u);
		session.getTransaction().commit();
		return id;
	}
	public User updateUser(User c){
		SessionFactory factory = HibernateManager.getSessionFactory();
		Session  session=  factory.openSession();
		session.getTransaction().begin();
		User u =  (User) session.merge(c);
		return u;
	}
	public static List<User> getUserList() {

		// create SessionFactory object for opening Session
		SessionFactory sessionFactory = HibernateManager.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		// Criteria requires a transaction opened
		session.beginTransaction();
		// create Criteria for Customer class
		Criteria criteria = session.createCriteria(User.class); //select * from customer
		// add a Restriction which will be used for equality --> state = "MI"
		//criteria.add(Restrictions.eq("state", stateName));
		// add an order for using customer_ID column
		criteria.addOrder(Order.asc("id"));
		// return the resultset as a List
		List<User> users = criteria.list();

		// convert List to Array
		return users;
	}
	public static List<User> getSortedUserList(String sortColumn,String sortOrder) {

		// create SessionFactory object for opening Session
		SessionFactory sessionFactory = HibernateManager.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		// Criteria requires a transaction opened
		session.beginTransaction();
		// create Criteria for Customer class
		Criteria criteria = session.createCriteria(User.class); //select * from customer
		// add a Restriction which will be used for equality --> state = "MI"
		//criteria.add(Restrictions.eq("state", stateName));
		// add an order for using customer_ID column
		if(sortOrder.equals("asc"))
			criteria.addOrder(Order.asc(sortColumn));
		else
			criteria.addOrder(Order.desc(sortColumn));
		//criteria.addOrder(Order.asc("name"));
		// return the resultset as a List
		List<User> customers = criteria.list();

		// convert List to Array
		return customers;
	}
	public List<User> searchCustomers(String filterText){
		SessionFactory sessionFactory = HibernateManager.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.ilike("name",filterText+"%"));
		return (List<User>)criteria.list();
	}
	public List<User> searchCustomersByState(String state){
		SessionFactory sessionFactory = HibernateManager.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.ilike("state",state));
		return (List<User>)criteria.list();
	}
	public List<User> searchCustomersByName(String name){
		SessionFactory sessionFactory = HibernateManager.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("name",name));
		return (List<User>)criteria.list();
	}
}
