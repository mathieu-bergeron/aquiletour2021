package ca.aquiletour.server.backend.course_list;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.server.backend.course.CourseUpdater;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
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
		
		CoursePath path = new CoursePath(teacher.getId(), item.getSemesterId(), item.getCourseId());
		
		CourseListUpdater.validateCourseDescription(item);
			
		CourseListUpdater.addCourseForUser(modelStore, CourseListModelTeacher.class, item, teacher);
		
		CourseUpdater.createCourseForUser(modelStore, 
				                          path,
				                          item.getCourseTitle(),
				                          teacher);

		CourseUpdater.updateCourseTitle(modelStore, path, item.getCourseTitle());

	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddCourseMessage message) {
		T.call(this);
		
		GroupListUpdater.addCourseForUser(modelStore, message.getSemesterId(), message.getCourseListItem().getCourseId(), message.getUser());
		
		DashboardUpdater.addDashboardItemForUser(modelStore, DashboardModelTeacher.class, message.getCourseListItem(), message.getUser());

		/*

		User teacher = message.getUser();
		String courseId = message.getCourse().getCourseId();

		QueueUpdater.createQueue(modelStore, courseId, teacher);
		QueuesUpdater.createQueue(modelStore, courseId, teacher);
		
		*/
	}
}
