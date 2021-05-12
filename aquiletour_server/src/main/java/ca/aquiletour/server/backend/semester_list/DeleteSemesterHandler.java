package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemester;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteSemesterHandler extends BackendMessageHandler<DeleteSemester> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteSemester message) throws BackendMessageHandlerError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendMessageHandlerError("Permission refusée");
		}
		
		if(message.getUser().actsAsAdmin()) {
			
			SemesterListManager.deleteSemesterFromModel(modelStore, 
					                                    SemesterListAdmin.class, 
					                                    message.getSemesterId(), 
					                                    Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);

		}else if(message.getUser().actsAsTeacher()){

			SemesterListManager.deleteSemesterForUser(modelStore, 
									  			      SemesterListTeacher.class, 
												      message.getSemesterId(), 
												      message.getUser());
		}else {
			throw new BackendMessageHandlerError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteSemester message) {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				SemesterListManager.deleteSemesterFromModel(modelStore, 
															SemesterListTeacher.class, 
															message.getSemesterId(), 
															teacherId);
			});
		}
	}
}
