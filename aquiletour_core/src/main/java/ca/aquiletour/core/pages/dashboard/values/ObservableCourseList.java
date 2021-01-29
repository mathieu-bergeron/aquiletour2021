package ca.aquiletour.core.pages.dashboard.values;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.trace.T;

public class ObservableCourseList extends ObservableList<Course>{
	private static final long serialVersionUID = -6055373964369299983L;

	public ObservableCourseList(List<Course> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return Course.class;
	}

}
