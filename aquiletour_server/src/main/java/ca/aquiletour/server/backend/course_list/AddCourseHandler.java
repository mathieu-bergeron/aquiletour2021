package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.services.Ntro;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddCourseMessage message) throws BackendMessageHandlerError {
		T.call(this);
		
		User teacher = message.getUser();
		
		CourseItem item = message.getCourseDescription();
		
		CoursePath path = new CoursePath(teacher.getId(), item.getSemesterId(), item.getCourseId());
		
		CourseListUpdater.validateCourseDescription(item);
			
		CourseListUpdater.addCourseForUser(modelStore, item, teacher);
		
		CourseUpdater.createCourseForUser(modelStore, 
				                          path,
				                          item.getCourseTitle(),
				                          teacher);

		CourseUpdater.updateCourseTitle(modelStore, path, item.getCourseTitle());

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
