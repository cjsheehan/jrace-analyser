package com.cjsheehan.jrace.racing.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
public class JockeyRepositoryTest extends TestCase {

	@Autowired
	private JockeyRepository repository;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public JockeyRepositoryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JockeyRepositoryTest.class);
	}

	public void testRepositoryShouldNotBeNull() {
		assertNotNull(repository);
	}

	public void testJockeyIsSavedToDb() {
		Jockey j = new Jockey("Tom");
		repository.save(j);
		assertEquals(1, 2);
	}
}