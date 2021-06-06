package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.DeleteSemesterMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;
import ca.ntro.services.ModelStoreSync;

public class DeleteSemesterHandler extends BackendMessageHandler<DeleteSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, DeleteSemesterMessage message) throws BackendError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendError("Permission refusée");
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
			throw new BackendError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, DeleteSemesterMessage message) throws BackendError {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			
			Optionnal<BackendError> backendError = new Optionnal<>();
			
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				try {
					SemesterListManager.deleteSemesterFromModel(modelStore, 
																SemesterListModelTeacher.class, 
																message.getSemesterId(), 
																teacherId);
				} catch(BackendError e) {
					backendError.set(e);
					throw new Break();
				}
			});
			
			if(!backendError.isEmpty()) {
				try {
					throw backendError.get();
				} catch (EmptyOptionException e) {}
			}
		}
	}
}
