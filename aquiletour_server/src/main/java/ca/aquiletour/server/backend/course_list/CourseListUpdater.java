package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class CourseListUpdater {

	public static String validateCourseDescription(CourseDescription courseDescription) {
		T.call(CourseListUpdater.class);

		return null;
	}

	public static void addCourseForUser(ModelStoreSync modelStore, CourseDescription courseDescription, User teacher) {
		T.call(CourseListUpdater.class);
		
		if(modelStore.ifModelExists(CourseListModel.class, "admin", teacher.getId())) {
			
			modelStore.updateModel(CourseListModel.class, "admin", teacher.getId(), new ModelUpdater<CourseListModel>() {
				@Override
				public void update(CourseListModel existingModel) {
					T.call(this);
					
					existingModel.addCourse(courseDescription);
				}
			});

		}else {
			
			modelStore.createModel(CourseListModel.class, "admin", teacher.getId(), new ModelInitializer<CourseListModel>() {
				@Override
				public void initialize(CourseListModel newModel) {
					T.call(this);
				}
			});
			
			addCourseForUser(modelStore, courseDescription, teacher);
		}
	}

}
