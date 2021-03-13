package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueuesViewWeb extends NtroViewWeb implements QueuesView {

	@Override
	public void initialize(NtroContext<?> context) {
	}

	@Override
	public void appendQueue(QueueSummaryView queueView) {
		T.call(this);

		HtmlElement container = this.getRootElement().children("#queues-container").get(0);
		
		MustNot.beNull(container);
		
		QueueSummaryViewWeb queueViewWeb = (QueueSummaryViewWeb) queueView;
		
		container.appendElement(queueViewWeb.getRootElement());
		
	}



}
