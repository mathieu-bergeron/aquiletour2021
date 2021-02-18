package ca.aquiletour.core.pages.root;

import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.MessageHandler;
import ca.ntro.core.system.trace.T;

public class QuitMessageHandler extends MessageHandler<RootController, QuitMessage> {

	@Override
	protected void handle(QuitMessage message) {
		T.call(this);

		Ntro.appCloser().close();
	}
}
