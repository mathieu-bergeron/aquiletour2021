package ca.aquiletour.core.pages.git.commit_list.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.GetCommitsForPathAndTimePeriod;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.git.commit_list.CommitListController;
import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
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
		
		GetCommitsForPath getCommitListMessage = new GetCommitsForPath(message);
		getCommitListMessage.setAuthToken(Ntro.currentUser().getAuthToken());

		currentController.setModelLoader(CommitListModel.class, Ntro.currentUser().getAuthToken(), getCommitListMessage.getDocumentPath().toString());
		currentController.setSubModelLoader(CourseModelStudent.class, Ntro.currentUser().getAuthToken(), message.getCourseId());

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGitCommitList(currentView);
	}
}
