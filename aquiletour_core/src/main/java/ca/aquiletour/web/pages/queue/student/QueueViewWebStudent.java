package ca.aquiletour.web.pages.queue.student;

import java.util.List;

import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class QueueViewWebStudent extends QueueViewWeb implements QueueViewStudent {
	
	private HtmlElement queueMessageContainer;
	private HtmlElement queueMessageElement;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		
		super.initializeViewWeb(context);

		queueMessageContainer = getRootElement().find("#queue-message-container").get(0);
		queueMessageElement = getRootElement().find(".queue-message-element").get(0);

		queueMessageContainer.hide();

		MustNot.beNull(queueMessageContainer);
		MustNot.beNull(queueMessageElement);
	}

	@Override
	public void displayQueueMessage(String queueMessage) {
		T.call(this);
		
		queueMessageContainer.show();
		queueMessageElement.text(queueMessage);
	}

}
