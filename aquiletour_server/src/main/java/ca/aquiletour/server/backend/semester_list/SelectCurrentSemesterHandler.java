package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.SelectCurrentSemester;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class SelectCurrentSemesterHandler extends BackendMessageHandler<SelectCurrentSemester> {

	@Override
	public void handleNow(ModelStoreSync modelStore, SelectCurrentSemester message) throws BackendMessageHandlerError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendMessageHandlerError("Permission refusée");
		}
		
		if(message.getUser().actsAsAdmin()) {

			SemesterListManager.selectCurrentSemesterForModelId(modelStore,
					 										    SemesterListModelAdmin.class,
															    message.getSemesterId(),
															    message.getCurrentSemester(),
															    Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);
		}else if(message.getUser().actsAsTeacher()) {

			SemesterListManager.selectCurrentSemesterForUser(modelStore,
					 										 SemesterListModelTeacher.class,
															 message.getSemesterId(),
															 message.getCurrentSemester(),
															 message.getUser());
		}else {
			
			throw new BackendMessageHandlerError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, SelectCurrentSemester message) {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				SemesterListManager.selectCurrentSemesterForModelId(modelStore,
																	SemesterListModelTeacher.class,
																	message.getSemesterId(),
																	message.getCurrentSemester(),
																	teacherId);
			});
		}
	}
}
