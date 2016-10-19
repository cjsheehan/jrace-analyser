package com.cjsheehan.jrace;

import java.lang.invoke.MethodHandles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistUtilities {
	private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String PU_NAME = "xyz";
	private static EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory(PU_NAME);
		} catch (Exception e) {
			log.error("Problem creating entity manager factory", e);
		}
	}


	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

}
