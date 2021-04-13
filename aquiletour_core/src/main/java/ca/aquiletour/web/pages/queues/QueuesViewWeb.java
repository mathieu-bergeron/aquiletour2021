package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueuesViewWeb extends NtroViewWeb implements OpenQueueListView {

	private HtmlElement container;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {

		container = this.getRootElement().children("#queues-container").get(0);
		
		MustNot.beNull(container);
	}

	@Override
	public void appendQueue(String queueId, OpenQueueView queueView) {
		T.call(this);
		
		QueueSummaryViewWeb queueViewWeb = (QueueSummaryViewWeb) queueView;
		
		HtmlElement queueRoot = queueViewWeb.getRootElement();
		
		queueRoot.setAttribute("id", queueId);
		
		container.appendElement(queueRoot);
	}

	@Override
	public void deleteQueue(String queueId) {
		T.call(this);
		
		this.getRootElement().find("#" + queueId).get(0).deleteForever();
	}
}
