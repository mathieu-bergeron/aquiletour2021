package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.backend.schedule.ScheduleUpdater;
import ca.aquiletour.server.backend.users.UserManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.wrappers.options.EmptyOptionException;
import ca.ntro.core.wrappers.options.Optionnal;

public class AddSemesterWeekHandler extends BackendMessageHandler<AddSemesterWeekMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterWeekMessage message) throws BackendError {
		T.call(this);

		if(!SessionManager.isUserAuthenticated(modelStore, message.getUser())) {
			throw new BackendError("Permission refusée!");
		}
		
		if(message.getUser().actsAsAdmin()) {

			SemesterListManager.addSemesterWeekToModel(modelStore, 
													   SemesterListModelAdmin.class,
					                                   message.getSemesterId(), 
					                                   message.getSemesterWeek(), 
					                                   Constants.ADMIN_CONTROLLED_SEMESTER_LIST_ID);

		}else if(message.getUser().actsAsTeacher()) {

			SemesterListManager.addSemesterWeekForUser(modelStore, 
													   SemesterListModelTeacher.class,
					                                   message.getSemesterId(), 
					                                   message.getSemesterWeek(), 
					                                   message.getUser());
		} else {

			throw new BackendError("Permission refusée");
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterWeekMessage message) throws BackendError {
		T.call(this);

		if(message.getUser().actsAsAdmin()) {
			
			Optionnal<BackendError> backendError = new Optionnal<>();

			UserManager.forEachTeacherId(modelStore, teacherId -> {
				try {
					SemesterListManager.addSemesterWeekToModel(modelStore, 
															   SemesterListModelTeacher.class,
															   message.getSemesterId(), 
															   message.getSemesterWeek(), 
															   teacherId);

				}catch(BackendError e) {
					backendError.set(e);
					throw new Break();
				}

				//ScheduleUpdater.updateSchedulesForUserId(modelStore, message.getSemesterId(), teacherId);
			});
			
			if(!backendError.isEmpty()) {
				try {
					throw backendError.get();
				} catch (EmptyOptionException e) {}
			}
		}

	}
}
