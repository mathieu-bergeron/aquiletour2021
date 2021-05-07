package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListForTimePeriodMessage;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class GetCommitsForPathAndTimePeriod extends GetCommitsForPath {
	
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

	public void loadStudentExerciseInfo(ShowCommitListForTimePeriodMessage message) {
		T.call(this);
		
		loadExerciseInfo(message);
		
		setFromDate(message.getFromDate());
		setToDate(message.getToDate());
		setGroupId(message.getGroupId());
		setSemesterId(message.getSemesterId());
		setStudentId(message.getStudentId());
	}

	@Override
	protected Path documentIdAsPath() {
		T.call(this);

		Path path = super.documentIdAsPath();

		path.getNames().add(String.valueOf(getFromDate()));
		path.getNames().add(String.valueOf(getToDate()));

		return path;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		T.call(this);
		
		return CommitListModel.class;
	}
}
