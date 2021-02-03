package ca.ntro.core.mvc;

import ca.ntro.messages.NtroMessage;

public abstract class ViewMessageHandler<CB extends ControllerBase, 
                                         V extends NtroView, 
                                         M extends NtroMessage> {
	
	protected abstract void handle(CB controller, V view, M message);
}
