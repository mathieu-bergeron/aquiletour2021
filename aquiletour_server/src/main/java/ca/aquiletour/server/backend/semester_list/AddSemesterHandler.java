package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.teacher.CourseListModelTeacher;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterHandler extends BackendMessageHandler<AddSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterMessage message) throws BackendMessageHandlerError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendMessageHandlerError("Permission refusée");
		}
		
		if(message.getUser().actsAsAdmin()) {
			
			SemesterListManager.addSemesterToModel(modelStore, 
					                               SemesterListModelAdmin.class, 
					                               SemesterModelAdmin.class,
					                               message.getSemesterId(), 
					                               true,
					                               Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);
			
		}else if(message.getUser().actsAsTeacher()){
			
			boolean adminControlledSemester = false;

			SemesterListManager.addSemesterForUser(modelStore, 
												   SemesterListModelTeacher.class, 
												   SemesterModelTeacher.class,
												   message.getSemesterId(), 
												   adminControlledSemester,
												   message.getUser());
		}else {

			throw new BackendMessageHandlerError("Permission refusée");
		}
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			
			boolean adminControlledSemester = true;
			
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				SemesterListManager.addSemesterToModel(modelStore, 
													   SemesterListModelTeacher.class, 
													   SemesterModelTeacher.class,
													   message.getSemesterId(), 
													   adminControlledSemester,
													   teacherId);
			});

		}else if(message.getUser().actsAsTeacher()){

			CourseListUpdater.addSemesterForUser(modelStore, CourseListModelTeacher.class, message.getSemesterId(), message.getUser());
			GroupListUpdater.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
		}
	}

}
