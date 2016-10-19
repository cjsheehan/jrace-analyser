package com.cjsheehan.jrace.racing.repository;

import javax.persistence.EntityManager;

import com.cjsheehan.jrace.PersistUtilities;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;

public class HorseDao {

	public void create(Horse horse) {
		if (horse == null) {
			throw new IllegalArgumentException("horse is null");
		}
		EntityManager em = PersistUtilities.createEntityManager();
		em.getTransaction().begin();
		em.persist(horse);
		em.getTransaction().commit();
		em.close();
	}
	
	public Horse find(int id) {
		EntityManager em = PersistUtilities.createEntityManager();
		Horse horse = em.find(Horse.class, id);
		em.close();
		return horse;
	}
	
	public void remove(int id) {
		EntityManager em = PersistUtilities.createEntityManager();
		Horse horse = em.find(Horse.class, id);
		if(horse != null) {
			em.getTransaction().begin();
			em.remove(horse);
			em.getTransaction().commit();
		}
	}
}
