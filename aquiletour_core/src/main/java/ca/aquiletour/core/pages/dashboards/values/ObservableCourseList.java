package ca.aquiletour.core.pages.dashboards.values;


import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableCourseList extends ObservableList<CourseSummary>{
	private static final long serialVersionUID = -6055373964369299983L;

	public ObservableCourseList() {
		super();
		T.call(this);
	}

}
