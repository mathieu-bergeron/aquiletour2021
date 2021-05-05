package ca.aquiletour.core.pages.git.commit_list.messages;

import ca.aquiletour.core.messages.git.StudentExerciseMessage;

public class ShowCommitListForTimePeriodMessage extends ShowCommitListMessage {

	Long startTime = (long) 0, endTime = (long) 0 ;

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	
}
