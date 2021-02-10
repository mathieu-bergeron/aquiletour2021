package ca.aquiletour.core.pages.dashboard.values;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableCourseList extends ObservableList<CourseSummary>{
	private static final long serialVersionUID = -6055373964369299983L;

	public ObservableCourseList() {
		super(new ArrayList<CourseSummary>());
		T.call(this);
	}

	public ObservableCourseList(List<CourseSummary> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		//return CourseSummary.class;
		
		// FIXME: this should be CourseSummary.class
		//        but ObservableList.toJsonObject needs fixing first
		return NtroModelValue.class;
	}

}
