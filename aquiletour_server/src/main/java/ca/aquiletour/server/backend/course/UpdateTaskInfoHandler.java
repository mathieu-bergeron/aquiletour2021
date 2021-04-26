package ca.aquiletour.server.backend.course;

import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class UpdateTaskInfoHandler extends BackendMessageHandler<UpdateTaskInfoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, UpdateTaskInfoMessage message) {
		T.call(this);
		
		T.here();
		
		CourseUpdater.updateTaskInfo(modelStore, 
				                     CourseModelTeacher.class, 
				                     message.coursePath(), 
				                     message.getTaskPath(),
				                     message.getTaskTitle(),
				                     message.getTaskDescription(),
				                     message.getEndTime(),
				                     message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, UpdateTaskInfoMessage message) {
	}


}
