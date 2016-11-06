package com.cjsheehan.jrace.racing.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.Entry;
import com.cjsheehan.jrace.racing.Horse;
import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Odds;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class EntryRepositoryTest extends TestCase {

	@Autowired
	private EntryRepository repository;

	@Test
	public void repositoryShouldNotBeNull() {
		assertNotNull(repository);
	}

	@Test
	public void entryIsSavedToDb() {
		int weightClaim = 3;
		int weightInLbs = 150;
		Entry entrySave = new Entry(2, new Horse("Horse1", 1), new Jockey("Jockey1", 1), new Trainer("Trainer1", 1), new Odds(new Date(), 1, 2), null);
		entrySave.setWeightClaim(weightClaim);
		entrySave.setWeightInLbs(weightInLbs);
		repository.save(entrySave);
		Entry entryRead = repository.findOne(entrySave.getId());
		assertNotNull(entryRead);
		assertEquals(entrySave.getWeightClaim(), entryRead.getWeightClaim());
		assertEquals(entrySave.getWeightInLbs(), entryRead.getWeightInLbs());
	}

}