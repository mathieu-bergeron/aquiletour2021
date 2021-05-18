package ca.aquiletour.core.pages.git.commit_list.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.GetCommitsForPathAndTimePeriod;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.pages.git.commit_list.CommitListController;
import ca.aquiletour.core.pages.git.commit_list.CommitListView;
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
		
		GetCommitsForPath getCommitListMessage = null;
		if (message instanceof ShowCommitListForTimePeriodMessage) {
			
			GetCommitsForPathAndTimePeriod getMessage = Ntro.messages().create(GetCommitsForPathAndTimePeriod.class);		
			getMessage.loadStudentExerciseInfo(message);
			getCommitListMessage = getMessage;
			
		} else {
			
			getCommitListMessage = Ntro.messages().create(GetCommitsForPath.class);
			getCommitListMessage.loadStudentExerciseInfo(message);
		}

		currentController.setModelUsingWebService(Constants.GIT_API_URL, getCommitListMessage); 
		currentController.setSubModelLoader(CourseModelTeacher.class, Ntro.currentUser().getAuthToken(), message.getCourseId());

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGitCommitList(currentView);
	}
}
