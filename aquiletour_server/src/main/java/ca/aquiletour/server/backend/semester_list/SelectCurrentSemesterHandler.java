package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.pages.semester_list.messages.SelectCurrentSemester;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class SelectCurrentSemesterHandler extends BackendMessageHandler<SelectCurrentSemester> {

	@Override
	public void handleNow(ModelStoreSync modelStore, SelectCurrentSemester message) {
		T.call(this);
		
		SemesterListUpdater.selectCurrentSemesterForUser(modelStore,
										        	     message.getSemesterId(),
												         message.getCurrentSemester(),
												         message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, SelectCurrentSemester message) {
		T.call(this);
		
	}
}
