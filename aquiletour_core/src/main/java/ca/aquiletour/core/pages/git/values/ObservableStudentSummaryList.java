package ca.aquiletour.core.pages.git.values;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ObservableStudentSummaryList extends StoredList<StudentSummary> {

	public ObservableStudentSummaryList() {
		super();
		T.call(this);
	}

}
