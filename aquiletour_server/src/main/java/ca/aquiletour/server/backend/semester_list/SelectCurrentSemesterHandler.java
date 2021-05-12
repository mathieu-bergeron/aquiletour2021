package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.SelectCurrentSemester;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class SelectCurrentSemesterHandler extends BackendMessageHandler<SelectCurrentSemester> {

	@Override
	public void handleNow(ModelStoreSync modelStore, SelectCurrentSemester message) {
		T.call(this);
		
		if(message.getUser() instanceof Admin && message.getUser().actsAsAdmin()) {

			SemesterListManager.selectCurrentSemesterForUser(modelStore,
					 										 SemesterListModelAdmin.class,
															 message.getSemesterId(),
															 message.getCurrentSemester(),
															 message.getUser());
		}else {

			SemesterListManager.selectCurrentSemesterForUser(modelStore,
					 										 SemesterListModelTeacher.class,
															 message.getSemesterId(),
															 message.getCurrentSemester(),
															 message.getUser());
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, SelectCurrentSemester message) {
		T.call(this);
		
	}
}
