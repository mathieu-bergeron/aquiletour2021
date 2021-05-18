package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemesterMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class DeleteSemesterHandler extends BackendMessageHandler<DeleteSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteSemesterMessage message) throws BackendMessageHandlerError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendMessageHandlerError("Permission refusée");
		}
		
		if(message.getUser().actsAsAdmin()) {
			
			SemesterListManager.deleteSemesterFromModel(modelStore, 
					                                    SemesterListModelAdmin.class, 
					                                    message.getSemesterId(), 
					                                    Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);

		}else if(message.getUser().actsAsTeacher()){

			SemesterListManager.deleteSemesterForUser(modelStore, 
									  			      SemesterListModelTeacher.class, 
												      message.getSemesterId(), 
												      message.getUser());
		}else {
			throw new BackendMessageHandlerError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteSemesterMessage message) {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				SemesterListManager.deleteSemesterFromModel(modelStore, 
															SemesterListModelTeacher.class, 
															message.getSemesterId(), 
															teacherId);
			});
		}
	}
}
