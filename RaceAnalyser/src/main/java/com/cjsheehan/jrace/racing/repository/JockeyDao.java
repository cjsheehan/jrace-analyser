package com.cjsheehan.jrace.racing.repository;

import javax.persistence.EntityManager;

import com.cjsheehan.jrace.PersistUtilities;
import com.cjsheehan.jrace.racing.Jockey;

public class JockeyDao {

	public void create(Jockey jockey) {
		if (jockey == null) {
			throw new IllegalArgumentException("jockey is null");
		}
		EntityManager em = PersistUtilities.createEntityManager();
		em.getTransaction().begin();
		em.persist(jockey);
		em.getTransaction().commit();
		em.close();
	}
	
	public Jockey find(int id) {
		EntityManager em = PersistUtilities.createEntityManager();
		Jockey jockey = em.find(Jockey.class, id);
		em.close();
		return jockey;
	}
	
	public void remove(int id) {
		EntityManager em = PersistUtilities.createEntityManager();
		Jockey jockey = em.find(Jockey.class, id);
		if(jockey != null) {
			em.getTransaction().begin();
			em.remove(jockey);
			em.getTransaction().commit();
		}
	}
}
