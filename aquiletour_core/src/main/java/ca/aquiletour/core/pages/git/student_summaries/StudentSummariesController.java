package ca.aquiletour.core.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.student_summaries.messages.ShowStudentSummariesHandler;
import ca.aquiletour.core.pages.git.student_summaries.messages.ShowStudentSummariesMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class StudentSummariesController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(StudentSummariesView.class, "fr");

		setModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(ShowStudentSummariesMessage.class, new ShowStudentSummariesHandler());
		addSubViewLoader(StudentSummaryView.class, context.lang());
		addModelViewSubViewHandler(StudentSummaryView.class, new StudentSummariesViewModel());
	}


	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}

	@Override
	protected void onChangeContext(NtroContext<?,?> oldContext, NtroContext<?,?> context) {
		// TODO Auto-generated method stub
		
	}

}
