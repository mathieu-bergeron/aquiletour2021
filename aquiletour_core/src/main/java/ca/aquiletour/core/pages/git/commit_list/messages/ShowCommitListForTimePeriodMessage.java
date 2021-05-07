package ca.aquiletour.core.pages.git.commit_list.messages;

import ca.aquiletour.core.messages.git.StudentExerciseMessage;

public class ShowCommitListForTimePeriodMessage extends ShowCommitListMessage {

	Long fromDate = (long) 0, toDate = (long) 0 ;

	public Long getFromDate() {
		return fromDate;
	}

	public void setFromDate(Long fromDate) {
		this.fromDate = fromDate;
	}

	public Long getToDate() {
		return toDate;
	}

	public void setToDate(Long toDate) {
		this.toDate = toDate;
	}
	
}
