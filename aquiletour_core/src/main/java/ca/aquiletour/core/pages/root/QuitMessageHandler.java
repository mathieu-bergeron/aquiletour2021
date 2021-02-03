package ca.aquiletour.core.pages.root;

import ca.ntro.core.Ntro;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageReceptor;

public class QuitMessageHandler extends MessageReceptor {

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
