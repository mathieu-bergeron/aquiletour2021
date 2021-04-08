package ca.aquiletour.core.pages.git.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.models.courses.base.CourseModel;
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
		
		GetCommitsForPath getCommitListMessage = Ntro.messages().create(GetCommitsForPath.class);
		getCommitListMessage.loadStudentExerciseInfo(message);
		
		currentController.setModelUsingWebService(Constants.GIT_API_URL, getCommitListMessage); 
		currentController.setSubModelLoader(CourseModel.class, Ntro.userService().user().getAuthToken(), message.getCourseId());

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGit(currentView);
	}
}
