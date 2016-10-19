package com.cjsheehan.jrace;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.repository.JockeyDao;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Persist {
	
	public static void main(String[] args) {
		JockeyDao jockeyDao = new JockeyDao();
		Jockey j1 = new Jockey("Harold");
		jockeyDao.create(j1);
		
		Jockey j2 = jockeyDao.find(1);
		System.out.println("Jockey: " + j2.getName());
		
		jockeyDao.remove(1);
		
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
