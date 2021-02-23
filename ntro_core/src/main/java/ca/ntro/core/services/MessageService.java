package ca.ntro.core.services;

import ca.ntro.core.Ntro;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;

public abstract class MessageService {
	
	public <M extends NtroMessage> void sendMessage(M message) {
		sendMessageToThread(Ntro.threadService().currentThread(), message);
	}

	private <M extends NtroMessage> void sendMessageToThread(NtroThread thread, M message) {

		Class<M> messageClass = (Class<M>) message.getClass();

		MessageHandler<M> handler = new MessageHandler<M>() {
			@Override
			public void handle(M message) {

				// XXX: the thread has not handled the message
				//      it HAS TO send it back

				NtroThread parentThread = Ntro.threadService().parentThreadOf(thread);

				if(parentThread != null) {
					
					sendMessageToThread(parentThread, message);
					
				}else {

					Ntro.backendService().sendMessageToBackend(message);
				}
			}
		};
		
		thread.handleMessageFromThread(messageClass, handler);

		thread.sendMessageToThread(message);
	}
	

}
