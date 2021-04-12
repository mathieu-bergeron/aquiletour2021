package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class CourseListUpdater {

	public static void addCourseForUser(ModelStoreSync modelStore, CourseDescription courseDescription, User teacher) {
		T.call(CourseListUpdater.class);
		
		
		
	}

}
