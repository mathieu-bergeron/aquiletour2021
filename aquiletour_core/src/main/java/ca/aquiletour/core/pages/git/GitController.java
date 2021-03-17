package ca.aquiletour.core.pages.git;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class GitController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(GitView.class, "fr");

		addParentViewMessageHandler(ShowGitMessage.class, new ShowGitHandler());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
