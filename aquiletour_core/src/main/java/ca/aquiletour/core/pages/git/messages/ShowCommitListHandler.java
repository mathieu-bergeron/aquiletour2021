package ca.aquiletour.core.pages.git.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitListMessage;
import ca.aquiletour.core.pages.git.CommitListController;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowCommitListHandler extends ControllerMessageHandler<CommitListController,
                                                                  CommitListView,
                                                                  ShowCommitListMessage> {
	
	@Override
	protected void handle(CommitListController currentController, CommitListView currentView, ShowCommitListMessage message) {
		T.call(this);
		
		GetCommitListMessage getCommitListMessage = Ntro.messages().create(GetCommitListMessage.class);
		getCommitListMessage.loadStudentExerciseInfo(message);

		currentController.setModelUsingWebService(Constants.GIT_API_URL, getCommitListMessage); 

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGit(currentView);
	}
}
