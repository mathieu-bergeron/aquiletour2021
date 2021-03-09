package ca.ntro.test.json;

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

}
