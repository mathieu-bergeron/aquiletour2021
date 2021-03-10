package ca.aquiletour.core.pages.root;

import ca.ntro.core.Ntro;

import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;

public class QuitMessageHandler extends MessageHandler<QuitMessage> {

	@Override
	public void handle(QuitMessage message) {
		T.call(this);

		Ntro.appCloser().close();
	}
}
