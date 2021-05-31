package ca.aquiletour.server.backend.course;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class UpdateTaskInfoHandler extends BackendMessageHandler<UpdateTaskInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateTaskInfoMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.updateTaskInfo(modelStore, 
				                     message.coursePath(), 
				                     message.getTaskPath(),
				                     message.getTaskTitle(),
				                     message.getTaskDescription(),
				                     message.getEndTime(),
				                     message.getUser());

		SemesterSchedule semesterSchedule = SemesterListManager.getSemesterSchedule(modelStore, 
																					SemesterListModelTeacher.class,
				                                                                    message.getSemesterId(), 
				                                                                    message.getUser());
		
		TeacherSchedule teacherSchedule = SemesterListManager.getTeacherSchedule(modelStore, 
				                                                                 message.getSemesterId(), 
				                                                                 message.getUser());

		CourseManager.updateCourseSchedule(modelStore,
										   message.coursePath(),
										   semesterSchedule,
										   teacherSchedule);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateTaskInfoMessage message) throws BackendError {
		T.call(this);

		CourseManager.updateTaskInfoForStudents(modelStore, 
				                     			message.coursePath(), 
				                     			message.getTaskPath(),
				                     			message.getTaskTitle(),
				                     			message.getTaskDescription(),
				                     			message.getEndTime(),
				                     			message.getUser());

		CourseModelTeacher teacherCourse = CourseManager.getCourse(modelStore, 
													   CourseModelTeacher.class,
													   message.coursePath());

		List<CurrentTaskTeacher> currentTasksTeacher = teacherCourse.updateCurrentTasks(Constants.NUMBER_OF_CURRENT_TASKS_TEACHER);

		DashboardManager.updateCurrentTasksForUserId(modelStore, 
				                                     DashboardModelTeacher.class, 
				                                     CurrentTaskTeacher.class, 
				                                     message.coursePath(), 
				                                     currentTasksTeacher, 
				                                     message.getTeacherId());
	}

}
