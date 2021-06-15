package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.aquiletour.core.pages.queue_list.views.QueueListItemView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueListItemViewWeb extends NtroViewWeb implements QueueListItemView {

	private HtmlElement queueLink;
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		queueLink = this.getRootElement().find("#queue-link").get(0);

		MustNot.beNull(queueLink);
	}

	@Override
	public void displayQueueListItem(QueueListItem queueListItem) {
		T.call(this);

		String teacherName = queueListItem.getTeacherDisplayName();
		String queueId = queueListItem.getQueueId();

		queueLink.html(teacherName);
		queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT  + "/" + queueId);
	}
}
