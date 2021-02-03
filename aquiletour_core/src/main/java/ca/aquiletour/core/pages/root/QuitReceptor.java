package ca.aquiletour.core.pages.root;

import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.MessageReceptor;
import ca.ntro.core.system.trace.T;

public class QuitReceptor extends MessageReceptor {

	@Override
	protected void initializeTask() {
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);

		Ntro.appCloser().close();
	}

	@Override
	protected void onFailure(Exception e) {
	}

}
