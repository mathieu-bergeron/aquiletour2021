package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.teacher.CourseListTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.dashboard.DashboardManager;
import ca.aquiletour.server.backend.group_list.GroupListManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddCourseHandler extends BackendMessageHandler<AddCourseMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddCourseMessage message) throws BackendMessageHandlerError {
		T.call(this);
		
		User teacher = message.getUser();
		
		CourseListItem item = message.getCourseListItem();
		
		CoursePath path = new CoursePath(teacher.getRegistrationId(), item.getSemesterId(), item.getCourseId());
		
		CourseListManager.validateCourseListItem(item);
			
		CourseListManager.addCourseForUser(modelStore, CourseListTeacher.class, item, teacher);
		
		CourseUpdater.createCourseForUser(modelStore, 
				                          path,
				                          item.getCourseTitle(),
				                          teacher);

		CourseUpdater.updateCourseTitle(modelStore, path, item.getCourseTitle());

	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		GroupListManager.addCourseForUser(modelStore, message.getSemesterId(), message.getCourseListItem().getCourseId(), message.getUser());
		
		DashboardManager.addDashboardItemForUser(modelStore, DashboardTeacher.class, message.getCourseListItem(), message.getUser());

		/*

		User teacher = message.getUser();
		String courseId = message.getCourse().getCourseId();

		QueueUpdater.createQueue(modelStore, courseId, teacher);
		QueuesUpdater.createQueue(modelStore, courseId, teacher);
		
		*/
	}
}
