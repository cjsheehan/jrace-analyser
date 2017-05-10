package com.cjsheehan.jrace;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.cjsheehan.jrace.racing.Jockey;
import com.cjsheehan.jrace.racing.repository.JockeyRepository;
import com.cjsheehan.jrace.racing.repository.config.Profiles;

@Component
public class Persist {
	final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private JockeyRepository repository;

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles(Profiles.APPLICATION);
		context.register(com.cjsheehan.jrace.racing.repository.config.ApplicationContext.class);
		context.refresh();

		Persist p = context.getBean(Persist.class);
		p.start(args);
		context.close();
	}

	public void start(String[] args) {
		try {

			Jockey j = new Jockey("Chris", 1);
			repository.save(j);

		} catch (BeansException e) {
			log.error("HELP", e);
		} catch (Exception e) {
			log.error("HELP", e);
		}
	}
}
