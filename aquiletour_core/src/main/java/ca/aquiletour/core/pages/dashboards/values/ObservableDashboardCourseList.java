package ca.aquiletour.core.pages.dashboards.values;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ObservableDashboardCourseList extends StoredList<CourseDashboard> {

	public ObservableDashboardCourseList() {
		super();
		T.call(this);
	}

}
