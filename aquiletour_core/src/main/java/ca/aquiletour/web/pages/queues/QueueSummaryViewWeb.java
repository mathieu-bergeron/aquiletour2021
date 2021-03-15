package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueSummaryViewWeb extends NtroViewWeb implements QueueSummaryView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {

	}

	@Override
	public void displaySummary(QueueSummary queue) {
		// TODO Auto-generated method stub
		T.here();
		
		HtmlElement queueId = this.getRootElement().children("#queueId").get(0);
		HtmlElement numberOfAnswersToDate = this.getRootElement().children("#numberOfAnswersToDate").get(0);
		HtmlElement teacherName = this.getRootElement().children("#teacherName").get(0);
		HtmlElement teacherSurname = this.getRootElement().children("#teacherSurname").get(0);
		MustNot.beNull(queueId);
		MustNot.beNull(numberOfAnswersToDate);
		MustNot.beNull(teacherName);
		MustNot.beNull(teacherSurname);
		queueId.appendHtml(queue.getId());
		numberOfAnswersToDate.appendHtml(Integer.toString(queue.getNumberOfAnswersToDate()));
		teacherName.appendHtml(queue.getTeacherName());
		teacherSurname.appendHtml(queue.getTeacherSurname());
	}

}
