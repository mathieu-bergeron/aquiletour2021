package ca.aquiletour.core.pages.dashboards.values;


import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class ObservableCourseList extends StoredList<CourseSummary> implements NtroModelValue {
	                                                                // JSWEET FIXME: should already implement via StoredList

	public ObservableCourseList() {
		super();
		T.call(this);
	}

}
