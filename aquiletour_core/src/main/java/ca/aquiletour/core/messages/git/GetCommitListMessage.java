package ca.aquiletour.core.messages.git;

public class GetCommitListMessage extends StudentExerciseMessage {
	
	private long fromDate;
	private long toDate;

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}
}
