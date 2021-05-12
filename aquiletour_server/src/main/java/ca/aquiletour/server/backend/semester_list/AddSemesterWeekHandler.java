package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.schedule.ScheduleUpdater;
import ca.aquiletour.server.backend.users.UserManager;
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
													   SemesterListAdmin.class,
					                                   message.getSemesterId(), 
					                                   message.getSemesterWeek(), 
					                                   Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);

		}else if(message.getUser().actsAsTeacher()) {

			SemesterListManager.addSemesterWeekForUser(modelStore, 
													   SemesterListTeacher.class,
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

		if(message.getUser().actsAsAdmin()) {
			UserManager.forEachTeacherId(modelStore, teacherId -> {
				SemesterListManager.addSemesterWeekToModel(modelStore, 
														   SemesterListTeacher.class,
														   message.getSemesterId(), 
														   message.getSemesterWeek(), 
														   teacherId);

				//ScheduleUpdater.updateSchedulesForUserId(modelStore, message.getSemesterId(), teacherId);
			});

		}

	}
}
