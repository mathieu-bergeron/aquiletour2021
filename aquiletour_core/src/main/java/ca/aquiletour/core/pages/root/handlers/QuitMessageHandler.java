package ca.aquiletour.core.pages.root.handlers;

import ca.aquiletour.core.pages.root.messages.QuitMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageHandler;
import ca.ntro.services.Ntro;

public class QuitMessageHandler extends MessageHandler<QuitMessage> {

	@Override
	public void handle(QuitMessage message) {
		T.call(this);

		Ntro.appCloser().close();
	}
}
