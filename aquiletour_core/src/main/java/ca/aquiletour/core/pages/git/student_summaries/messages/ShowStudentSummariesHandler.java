package ca.aquiletour.core.pages.git.student_summaries.messages;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.git.GetStudentSummaries;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesController;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ControllerMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class ShowStudentSummariesHandler extends ControllerMessageHandler<StudentSummariesController,
																		StudentSummariesView,
                                                                  ShowStudentSummariesMessage> {
	
	@Override
	protected void handle(StudentSummariesController currentController, StudentSummariesView currentView, ShowStudentSummariesMessage message) {
		T.call(this);
		
		GetStudentSummaries getStudentSummariesMessage = Ntro.messages().create(GetStudentSummaries.class);
		getStudentSummariesMessage.loadExerciseInfo(message);
		currentController.setModelUsingWebService(Constants.GIT_API_URL, getStudentSummariesMessage); 

		RootView rootView = (RootView) currentController.getParentController().getView();
		rootView.showGitStudentSummaries(currentView);
	}

}
