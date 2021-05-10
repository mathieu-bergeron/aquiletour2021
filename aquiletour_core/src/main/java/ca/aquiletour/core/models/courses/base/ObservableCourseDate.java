package ca.aquiletour.core.models.courses.base;

import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class ObservableCourseDate extends StoredProperty<CourseDate> {
	
	public ObservableCourseDate() {
		super(new CourseDateScheduleItem());
		T.call(this);
	}

	public ObservableCourseDate(CourseDate date) {
		super(date);
		T.call(this);
	}
}
