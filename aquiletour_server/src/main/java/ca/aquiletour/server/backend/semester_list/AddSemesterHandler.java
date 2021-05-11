package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.users.Admin;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterHandler extends BackendMessageHandler<AddSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
		if(message.getUser() instanceof Admin && message.getUser().actsAsAdmin()) {
			
			SemesterListManager.addSemesterToModel(modelStore, 
					                               SemesterListModelAdmin.class, 
					                               message.getSemesterId(), 
					                               Constants.MANAGED_SEMESTER_MODEL_ID);
			
		}else {

			SemesterListManager.addSemesterForUser(modelStore, 
												   SemesterListModelTeacher.class, 
												   message.getSemesterId(), 
												   message.getUser());
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
		CourseListUpdater.addSemesterForUser(modelStore, CourseListModelTeacher.class, message.getSemesterId(), message.getUser());
		GroupListUpdater.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
	}

}
