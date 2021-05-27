package ca.aquiletour.core.models.courses.base;

import ca.aquiletour.core.models.dates.CourseDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.system.trace.T;

public class StoredCourseDate extends StoredProperty<CourseDate> {
	
	public StoredCourseDate() {
		super(new CourseDateScheduleItem());
		T.call(this);
	}

	public StoredCourseDate(CourseDate date) {
		super(date);
		T.call(this);
	}
}
