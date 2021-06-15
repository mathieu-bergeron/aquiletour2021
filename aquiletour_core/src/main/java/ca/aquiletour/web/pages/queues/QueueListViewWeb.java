package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.queue_list.views.QueueListItemView;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueListViewWeb extends NtroViewWeb implements QueueListView {

	private HtmlElement container;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {

		container = this.getRootElement().find("#queues-container").get(0);
		
		MustNot.beNull(container);
	}

	@Override
	public void appendQueue(String queueId, QueueListItemView queueView) {
		T.call(this);
		
		QueueListItemViewWeb queueViewWeb = (QueueListItemViewWeb) queueView;
		
		HtmlElement queueRoot = queueViewWeb.getRootElement();
		
		queueRoot.setAttribute("id", queueId);
		
		container.appendElement(queueRoot);
	}

}
