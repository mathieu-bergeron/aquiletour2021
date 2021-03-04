package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueSummaryViewWeb extends NtroViewWeb implements QueueSummaryView {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySummary(QueueSummary queue) {
		// TODO Auto-generated method stub
		T.here();
		
		HtmlElement queueId = this.getRootElement().children("#queueId").get(0);
		MustNot.beNull(queueId);
		queueId.appendHtml(queue.getId());
	}

}
