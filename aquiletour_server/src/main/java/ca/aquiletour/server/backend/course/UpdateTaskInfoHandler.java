package ca.aquiletour.server.backend.course;


import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.semester_list.SemesterListManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class UpdateTaskInfoHandler extends BackendMessageHandler<UpdateTaskInfoMessage> {

	private SemesterSchedule semesterSchedule;
	private TeacherSchedule teacherSchedule;

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

		semesterSchedule = SemesterListManager.getSemesterSchedule(modelStore, 
																   SemesterListModelTeacher.class,
				                                                   message.getSemesterId(), 
				                                                   message.getUser());
		
		teacherSchedule = SemesterListManager.getTeacherSchedule(modelStore, 
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

		DashboardManager.updateCurrentTasks(modelStore, 
											message.coursePath());

		CourseManager.updateCourseScheduleForStudents(modelStore,
										              message.coursePath(),
										              semesterSchedule,
										              teacherSchedule);
		

	}

}
