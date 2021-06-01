package ca.aquiletour.core.pages.git.commit_list.messages;

import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class OnNewCommitsHandler extends ModelViewSubViewMessageHandler<CommitListModel, CommitView, OnNewCommits> {
	
//	@Override
//	protected void handle(CommitListController currentController, CommitListView currentView, ShowCommitListMessage message) {
//		T.call(this);
//		
//		GetCommitsForPath getCommitListMessage = Ntro.messages().create(GetCommitsForPath.class);
//		getCommitListMessage.loadStudentExerciseInfo(message);
//
//		currentController.setModelUsingWebService(Constants.GIT_API_URL, getCommitListMessage); 
//		currentController.setSubModelLoader(CourseModel.class, Ntro.userService().user().getAuthToken(), message.getCourseId());
//
//		RootView rootView = (RootView) currentController.getParentController().getView();
//		rootView.showGitCommitList(currentView);
//	}

	@Override
	protected void handle(CommitListModel model, CommitView view, ViewLoader subViewLoader, OnNewCommits message) {
		T.call(this);
		T.here();
		
	}
}
