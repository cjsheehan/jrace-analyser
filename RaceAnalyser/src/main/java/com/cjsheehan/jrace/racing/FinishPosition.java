package com.cjsheehan.jrace.racing;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FinishPosition {

	private int position = -1;
	private FinishClassifier fc;

	public FinishPosition(String position) {
		super();
		try {
			this.position = Integer.parseInt(position);
			fc = FinishClassifier.FINISHED;
		} catch (NumberFormatException e) {
			fc = FinishClassifier.valueOf(position);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Position \"{0}\" is not correct format", position));
		}
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return the fc
	 */
	public FinishClassifier getFc() {
		return fc;
	}

}
