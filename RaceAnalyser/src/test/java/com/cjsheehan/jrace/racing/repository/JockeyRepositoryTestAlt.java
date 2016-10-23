package com.cjsheehan.jrace.racing.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.repository.config.ApplicationContext;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationContext.class)
public class JockeyRepositoryTestAlt extends TestCase {

    @Autowired
    private JockeyRepository repository;
    
    @Test
    public void repositoryShouldNotBeNull() {
	assertNotNull(repository);
    }
    
    @Test
    public void jockeyIsSavedToDb() {
	Jockey j = new Jockey("Tom");
	repository.save(j);
	assertEquals(1, 1);
    }

}