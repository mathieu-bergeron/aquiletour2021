package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.schedule.ScheduleUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterWeekHandler extends BackendMessageHandler<AddSemesterWeekMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterWeekMessage message) throws BackendMessageHandlerError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendMessageHandlerError("Permission refusée!");
		}
		
		if(message.getUser().actsAsAdmin()) {

			SemesterListManager.addSemesterWeekToModel(modelStore, 
													   SemesterListModelAdmin.class,
					                                   message.getSemesterId(), 
					                                   message.getSemesterWeek(), 
					                                   Constants.MANAGED_SEMESTER_MODEL_ID);

		}else if(message.getUser().actsAsTeacher()) {

			SemesterListManager.addSemesterWeekForUser(modelStore, 
													   SemesterListModelTeacher.class,
					                                   message.getSemesterId(), 
					                                   message.getSemesterWeek(), 
					                                   message.getUser());
		} else {

			throw new BackendMessageHandlerError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterWeekMessage message) {
		T.call(this);

		ScheduleUpdater.updateSchedulesForUser(modelStore, message.getSemesterId(), message.getUser());
	}
}
