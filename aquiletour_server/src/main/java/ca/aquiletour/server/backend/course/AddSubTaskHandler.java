package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.server.backend.course_list.CourseListManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSubTaskHandler extends BackendMessageHandler<AddSubTaskMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSubTaskMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.addSubTask(modelStore, message.coursePath(), message.getParentPath(), message.getSubTask());
		CourseListManager.addTask(modelStore, 
								  CourseListModelTeacher.class,
								  message.coursePath(),
								  TaskDescription.fromTask(message.getSubTask()));
								   
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSubTaskMessage message) throws BackendError {
		T.call(this);
		
		CourseManager.addSubTaskForStudents(modelStore, message.coursePath(), message.getParentPath(), message.getSubTask());
	}
}
