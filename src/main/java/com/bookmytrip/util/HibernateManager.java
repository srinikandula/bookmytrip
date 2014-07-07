package com.bookmytrip.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateManager // Hibernate utility class
{
  private static final SessionFactory sessionFactory;

  static {    //create sessionFactory only once    
    try {
      // creating the SessionFactory from hibernate.cfg.xml            
      sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    } catch (Throwable ex) {
           System.err.println("SessionFactory initial creation error."+ ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
  public static SessionFactory getSessionFactory() {
     return sessionFactory;
  }

}