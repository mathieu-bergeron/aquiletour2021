package ca.aquiletour.server.backend.schedule;

import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.semester_list.SemesterListUpdater;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class ScheduleUpdater {

	public static void updateSchedulesForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  User user) {
		T.call(ScheduleUpdater.class);

		SemesterSchedule semesterSchedule = SemesterListUpdater.getSemesterSchedule(modelStore, 
				                                                                    semesterId, 
				                                                                    user);
		
		TeacherSchedule teacherSchedule = SemesterListUpdater.getTeacherSchedule(modelStore, 
				                                                                 semesterId, 
				                                                                 user);

		
		List<CoursePath> teacherCourses = CourseListUpdater.getCourseList(modelStore, 
																	      CourseListModelTeacher.class,
				                                                          semesterId, 
				                                                          user);
		
		for(CoursePath coursePath : teacherCourses) {
			CourseUpdater.updateCourseSchedule(modelStore,
											   coursePath,
											   semesterSchedule,
											   teacherSchedule,
											   user);
		}
	}

}
