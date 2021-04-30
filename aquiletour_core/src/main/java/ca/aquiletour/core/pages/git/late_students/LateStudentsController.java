package ca.aquiletour.core.pages.git.late_students;

import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListHandler;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.git.late_students.messages.ShowLateStudentsHandler;
import ca.aquiletour.core.pages.git.late_students.messages.ShowLateStudentsMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.ModelViewHandler;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class LateStudentsController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(LateStudentsView.class, "fr");

		setModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(ShowLateStudentsMessage.class, new ShowLateStudentsHandler());
		
	}


	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}

	@Override
	protected void onChangeContext(NtroContext<?> oldContext, NtroContext<?> context) {
		// TODO Auto-generated method stub
		
	}

}
