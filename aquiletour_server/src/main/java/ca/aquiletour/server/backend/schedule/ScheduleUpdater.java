package ca.aquiletour.server.backend.schedule;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ScheduleUpdater {

	public static void updateSchedulesForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  User user) {
		T.call(ScheduleUpdater.class);
		
		updateSchedulesForUserId(modelStore, semesterId, user.getId());
	}

	public static void updateSchedulesForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    String userId) {
		T.call(ScheduleUpdater.class);

		SemesterSchedule semesterSchedule = SemesterListManager.getSemesterSchedule(modelStore, 
																				    SemesterListModelTeacher.class,
				                                                                    semesterId, 
				                                                                    userId);
		
		TeacherSchedule teacherSchedule = SemesterListManager.getTeacherSchedule(modelStore, 
				                                                                 semesterId, 
				                                                                 userId);

		
		List<CoursePath> teacherCourses = CourseListUpdater.getCourseList(modelStore, 
																	      CourseListModelTeacher.class,
				                                                          semesterId, 
				                                                          userId);
		
		if(semesterSchedule != null
				&& teacherSchedule != null) {

			for(CoursePath coursePath : teacherCourses) {
				CourseUpdater.updateCourseSchedule(modelStore,
												   coursePath,
												   semesterSchedule,
												   teacherSchedule);
			}
		}
	}

}
