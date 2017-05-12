package com.cjsheehan.jrace.scrape;

import org.springframework.stereotype.Component;

@Component
public interface StandardParamProvider {
	String dateFormat();
	String ageConstraintSelector();
	String courseNameSelector();
	String gradeSelector();
	String goingSelector();
	String titleSelector();
	String dateSelector();
	String timeSelector();
	String prizeSelector();
	String numRunnersSelector();
	String distanceSelector();
}
