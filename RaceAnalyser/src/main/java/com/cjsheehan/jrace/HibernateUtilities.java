package com.cjsheehan.jrace;

import java.lang.invoke.MethodHandles;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtilities {
	private static SessionFactory sessionFactory;
	private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	static {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (HibernateException e) {
			log.error("Problem creating session factory", e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
