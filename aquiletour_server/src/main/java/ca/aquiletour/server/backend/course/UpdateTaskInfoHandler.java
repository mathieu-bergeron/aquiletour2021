package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.aquiletour.server.backend.semester_list.SemesterListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class UpdateTaskInfoHandler extends BackendMessageHandler<UpdateTaskInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateTaskInfoMessage message) {
		T.call(this);
		
		CourseUpdater.updateTaskInfo(modelStore, 
				                     CourseModelTeacher.class, 
				                     message.coursePath(), 
				                     message.getTaskPath(),
				                     message.getTaskTitle(),
				                     message.getTaskDescription(),
				                     message.getEndTime(),
				                     message.getUser());

		SemesterSchedule semesterSchedule = SemesterListUpdater.getSemesterSchedule(modelStore, 
				                                                                    message.getSemesterId(), 
				                                                                    message.getUser());
		
		TeacherSchedule teacherSchedule = SemesterListUpdater.getTeacherSchedule(modelStore, 
				                                                                 message.getSemesterId(), 
				                                                                 message.getUser());

		CourseUpdater.updateCourseSchedule(modelStore,
										   CourseModelTeacher.class,
										   message.coursePath(),
										   semesterSchedule,
										   teacherSchedule,
										   message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateTaskInfoMessage message) {
		T.call(this);
	}


}
