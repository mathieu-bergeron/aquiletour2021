package ca.aquiletour.core.pages.git.late_students.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.GetLateStudents;
import ca.aquiletour.core.pages.git.commit_list.CommitListController;
import ca.aquiletour.core.pages.git.commit_list.CommitListView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsController;
import ca.aquiletour.core.pages.git.late_students.LateStudentsModel;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowLateStudentsHandler
		extends ControllerMessageHandler<LateStudentsController, LateStudentsView, ShowLateStudentsMessage> {

	@Override
	protected void handle(LateStudentsController currentController, LateStudentsView currentView,
			ShowLateStudentsMessage message) {
		T.call(this);

		GetLateStudents getLateStudentsMessage = Ntro.messages().create(GetLateStudents.class);
		getLateStudentsMessage.loadExerciseInfo(message);

		currentController.setModelUsingWebService(Constants.GIT_API_URL, getLateStudentsMessage);

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGitLateStudents(currentView);
		
		
	}
}
