package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.aquiletour.core.pages.queue_list.views.QueueListItemView;
import ca.aquiletour.web.widgets.UpdateAnimator;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueListItemViewWeb extends NtroViewWeb implements QueueListItemView {

	private HtmlElement queueLink;
	private HtmlElement lastActivityElement;
	private HtmlElement numberOfAppointmentsElement;
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		queueLink = this.getRootElement().find(".queue-link").get(0);
		lastActivityElement = this.getRootElement().find(".last-activity").get(0);
		numberOfAppointmentsElement = this.getRootElement().find(".number-of-appointments").get(0);

		MustNot.beNull(queueLink);
		MustNot.beNull(lastActivityElement);
		MustNot.beNull(numberOfAppointmentsElement);
	}

	@Override
	public void displayQueueListItem(QueueListItem queueListItem) {
		T.call(this);

		String queueId = queueListItem.getQueueId();
		String teacherName = queueListItem.getTeacherDisplayName().getValue();
		NtroDate lastActivity = queueListItem.getLastActivity().getValue();
		long numberOfAppointments = queueListItem.getNumberOfAppointments().getValue();
		
		UpdateAnimator.updateText(queueLink, teacherName);
		queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT  + "/" + queueId);
		
		UpdateAnimator.updateText(lastActivityElement, lastActivity.format("EEE d MMM HH:mm"));
		UpdateAnimator.updateText(numberOfAppointmentsElement, String.valueOf(numberOfAppointments));
	}
}
