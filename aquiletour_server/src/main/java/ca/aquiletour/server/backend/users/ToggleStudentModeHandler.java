package ca.aquiletour.server.backend.users;

import ca.aquiletour.core.messages.user.ToggleStudentModeMessage;
import ca.aquiletour.core.models.users.Teacher;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ToggleStudentModeHandler extends BackendMessageHandler<ToggleStudentModeMessage>{

	@Override
	public void handleNow(ModelStoreSync modelStore, ToggleStudentModeMessage message) throws BackendMessageHandlerError {
		T.call(this);
		
		if(Ntro.currentUser() instanceof Teacher) {
			Teacher teacher = (Teacher) Ntro.currentSession().getUser();
			teacher.toggleStudentMode();

			UserUpdater.toggleStudentMode(modelStore, teacher);
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, ToggleStudentModeMessage message) {
		T.call(this);
		
	}
	
}
