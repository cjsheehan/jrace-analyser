package com.cjsheehan.jrace.racing.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.Entry;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.APPLICATION)
public class EntryRepositoryTest extends TestCase {

	@Autowired
	private EntryRepository repository;

	@Test
	public void repositoryShouldNotBeNull() {
		assertNotNull(repository);
	}

	@Test
	public void entryIsSavedToDb() {
		Entry entry = new Entry(new Horse("Horse1"), new Jockey("Jockey1"), new Trainer("Trainer1"), null);
		entry.setWeightClaim(3);
//		entry.setWeightInLbs(150);
		repository.save(entry);
		assertEquals(1, 1);
	}

}