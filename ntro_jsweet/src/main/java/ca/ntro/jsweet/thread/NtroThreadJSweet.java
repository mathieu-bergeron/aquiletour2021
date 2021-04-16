package ca.ntro.jsweet.thread;

import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.NtroMessage;
import ca.ntro.threads.NtroThread;
import def.dom.Worker;

public class NtroThreadJSweet implements NtroThread {

	private long threadId;
	private Worker worker;

	public NtroThreadJSweet(long threadId) {
		this.threadId = threadId;

		// TODO Fournir une URL qui contient le script du worker ?
		// 		Peut-être `Ntro.workerScriptUrl()` ?
		// TODO JSweet ou pas ?
		this.worker = new Worker("");
	}

	@Override
	public void sendMessageToThread(NtroMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public <M extends NtroMessage> void handleMessageFromThread(Class<M> messageClass, MessageHandler<M> handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public String threadId() {
		return String.valueOf(threadId);
	}

}
