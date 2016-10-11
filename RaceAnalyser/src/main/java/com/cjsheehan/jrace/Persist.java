package com.cjsheehan.jrace;

import org.hibernate.Session;

import com.cjsheehan.jrace.racing.Jockey;

public class Persist {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();
		
		Jockey j1 = new Jockey();
		j1.setName("Chris Sheehan");
		session.save(j1);

		session.getTransaction().commit();
		session.close();
		
		
		HibernateUtilities.getSessionFactory().close();
	}

//	private static void populateSampleData() {
//		Session session = HibernateUtilities.getSessionFactory().openSession();
//		session.beginTransaction();
//		
//
//		
//		session.getTransaction().commit();
//		session.close();
//	}

}
