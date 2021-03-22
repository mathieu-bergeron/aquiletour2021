package ca.aquiletour.core.pages.git.messages;

import ca.aquiletour.core.pages.git.CommitListController;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class ShowCommitListHandler extends ControllerMessageHandler<CommitListController,
                                                                  CommitListView,
                                                                  ShowCommitListMessage> {
	private String currentStudentId = null;

	@Override
	protected void handle(CommitListController currentController, CommitListView currentView, ShowCommitListMessage message) {
		// TODO Auto-generated method stub
		T.call(this);
		
		String studentId = message.getStudentId();
		
		MustNot.beNull(studentId);
		
		if(!studentId.equals(currentStudentId)) {
			// XXX: change queue model when needed
			String authToken = currentController.context().user().getAuthToken();
			currentController.setModelLoader(CommitListModel.class, authToken, studentId);
			currentStudentId = studentId;
		}

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGit(currentView);
	}
}
