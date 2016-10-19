package com.cjsheehan.jrace.racing.repository;

import javax.persistence.EntityManager;

import com.cjsheehan.jrace.PersistUtilities;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Trainer;

public class TrainerDao {

	public void create(Trainer trainer) {
		if (trainer == null) {
			throw new IllegalArgumentException("trainer is null");
		}
		EntityManager em = PersistUtilities.createEntityManager();
		em.getTransaction().begin();
		em.persist(trainer);
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
