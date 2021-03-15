package ca.aquiletour.core.pages.dashboards.values;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ObservableCourseList extends StoredList<CourseSummary> {

	public ObservableCourseList() {
		super();
		T.call(this);
	}

}
