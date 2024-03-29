package ca.aquiletour.server.backend.schedule;

import java.util.List;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class ScheduleUpdater {

	public static void updateSchedulesForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  User user) throws BackendError {
		T.call(ScheduleUpdater.class);
		
		updateSchedulesForUserId(modelStore, semesterId, user.getId());
	}

	public static void updateSchedulesForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    String userId) throws BackendError {
		T.call(ScheduleUpdater.class);

		SemesterSchedule semesterSchedule = SemesterListManager.getSemesterSchedule(modelStore, 
																				    SemesterListModelTeacher.class,
				                                                                    semesterId, 
				                                                                    userId);
		
		TeacherSchedule teacherSchedule = SemesterListManager.getTeacherSchedule(modelStore, 
				                                                                 semesterId, 
				                                                                 userId);

		
		List<CoursePath> teacherCourses = CourseListManager.getCourseList(modelStore, 
																	      CourseListModelTeacher.class,
				                                                          semesterId, 
				                                                          userId);
		
		if(semesterSchedule != null
				&& teacherSchedule != null) {

			for(CoursePath coursePath : teacherCourses) {
				CourseManager.updateCourseSchedule(modelStore,
												   coursePath,
												   semesterSchedule,
												   teacherSchedule);
			}
		}
	}

}
