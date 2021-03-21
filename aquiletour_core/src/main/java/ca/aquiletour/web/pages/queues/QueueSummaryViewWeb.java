package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueSummaryViewWeb extends NtroViewWeb implements QueueSummaryView {

	private HtmlElement queueLink;
	private HtmlElement numberOfAnswersToDate;
	private HtmlElement teacherName;
	
	private String queueLinkHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		queueLink = this.getRootElement().children("#queue-link").get(0);
		numberOfAnswersToDate = this.getRootElement().children("#number-of-answers").get(0);
		teacherName = this.getRootElement().children("#teacher-name").get(0);

		MustNot.beNull(queueLink);
		MustNot.beNull(numberOfAnswersToDate);
		MustNot.beNull(teacherName);
		
		queueLinkHref = queueLink.getAttribute("href");
	}

	@Override
	public void displaySummary(QueueSummary queue) {
		T.call(this);
		
		queueLink.html(queue.getId());
		queueLink.setAttribute("href", queueLinkHref + queue.getId());
		
		numberOfAnswersToDate.html(Integer.toString(queue.getNumberOfAnswersToDate()));
		
		String userName = queue.getTeacherName();
		if(queue.getTeacherSurname() != null && queue.getTeacherSurname().length() > 0) {
			userName += " " + queue.getTeacherSurname();
		}

		teacherName.html(userName);
	}

}
