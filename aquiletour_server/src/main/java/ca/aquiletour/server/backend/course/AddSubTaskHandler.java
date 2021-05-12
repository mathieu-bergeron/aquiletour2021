package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.teacher.CourseListTeacher;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSubTaskHandler extends BackendMessageHandler<AddSubTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSubTaskMessage message) {
		T.call(this);
		
		CourseUpdater.addSubTask(modelStore, message.coursePath(), message.getParentPath(), message.getSubTask());
		CourseListUpdater.addTask(modelStore, 
								  CourseListTeacher.class,
								  message.coursePath(),
								  TaskDescription.fromTask(message.getSubTask()));
								   
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSubTaskMessage message) {
		T.call(this);
	}
}
