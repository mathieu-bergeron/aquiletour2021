package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemester;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
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
					                                    SemesterListModelAdmin.class, 
					                                    message.getSemesterId(), 
					                                    Constants.MANAGED_SEMESTER_MODEL_ID);

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
	public void handleLater(ModelStoreSync modelStore, DeleteSemester message) {
		T.call(this);
	}
}
