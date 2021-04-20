package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.root.DisplayErrorMessage;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		User teacher = message.getUser();
		
		CourseItem courseDescription = message.getCourseDescription();
		
		String errorMessage = CourseListUpdater.validateCourseDescription(courseDescription);
		
		if(errorMessage != null) {
			
			DisplayErrorMessage displayErrorMessage = Ntro.messages().create(DisplayErrorMessage.class);
			displayErrorMessage.setMessage(errorMessage);
			Ntro.messages().send(displayErrorMessage);
			
		}else {
			
			CourseListUpdater.addCourseForUser(modelStore, message.getCourseDescription(), teacher);
			
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		GroupListUpdater.addCourseForUser(modelStore, message.getSemesterId(), message.getCourseDescription().getCourseId(), message.getUser());

		/*

		User teacher = message.getUser();
		String courseId = message.getCourse().getCourseId();

		QueueUpdater.createQueue(modelStore, courseId, teacher);
		QueuesUpdater.createQueue(modelStore, courseId, teacher);
		
		*/
	}
}
