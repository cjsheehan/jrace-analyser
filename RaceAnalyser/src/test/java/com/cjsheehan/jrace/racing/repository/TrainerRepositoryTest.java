package com.cjsheehan.jrace.racing.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.Trainer;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;
import com.cjsheehan.jrace.racing.repository.config.Profiles;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class TrainerRepositoryTest extends TestCase {

	@Autowired
	private TrainerRepository repository;

	@Test
	public void repositoryShouldNotBeNull() {
		assertNotNull(repository);
	}

	@Test
	public void jockeyIsSavedToDb() {
		Trainer trainerSave = new Trainer("test_trainer", 12345);
		repository.save(trainerSave);
		Trainer trainerRead = repository.findOne(trainerSave.getId());
		assertEquals(trainerSave.getName(), trainerRead.getName());
		assertEquals(trainerSave.getId(), trainerRead.getId());
	}

}