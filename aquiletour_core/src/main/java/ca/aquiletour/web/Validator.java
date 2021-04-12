package ca.aquiletour.web;

import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.ntro.core.system.trace.T;

public class Validator {

	public static String validateCourseDescription(CourseDescription courseDescription) {
		T.call(Validator.class);

		return "Code de cours invalide";
	}

}
