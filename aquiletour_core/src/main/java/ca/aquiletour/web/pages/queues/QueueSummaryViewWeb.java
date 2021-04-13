package ca.aquiletour.web.pages.queues;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueView;
import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class QueueSummaryViewWeb extends NtroViewWeb implements OpenQueueView {

	private HtmlElement queueLink;
	private HtmlElement numberOfAnswersToDate;
	private HtmlElement teacherName;
	
	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		queueLink = this.getRootElement().find("#queue-link").get(0);
		numberOfAnswersToDate = this.getRootElement().find("#number-of-answers").get(0);
		teacherName = this.getRootElement().find("#teacher-name").get(0);

		MustNot.beNull(queueLink);
		MustNot.beNull(numberOfAnswersToDate);
		MustNot.beNull(teacherName);
	}

	@Override
	public void displaySummary(OpenQueue queue) {
		T.call(this);
		
		queueLink.html(queue.getId());
		queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT  + "/" + queue.getId());
		
		queueLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				if(Ntro.currentUser() instanceof Teacher ||
						Ntro.currentUser() instanceof TeacherGuest) {
					
					ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
					showTeacherQueueMessage.setCourseId(queue.getId());
					Ntro.messages().send(showTeacherQueueMessage);

				}else {
					
					ShowStudentQueueMessage showStudentQueueMessage = Ntro.messages().create(ShowStudentQueueMessage.class);
					showStudentQueueMessage.setCourseId(queue.getId());
					Ntro.messages().send(showStudentQueueMessage);
				}
			}
		});
		
		numberOfAnswersToDate.html(Integer.toString(queue.getNumberOfAnswersToDate()));
		
		String userName = queue.getTeacherName();
		if(queue.getTeacherSurname() != null && queue.getTeacherSurname().length() > 0) {
			userName += " " + queue.getTeacherSurname();
		}

		teacherName.html(userName);
	}
}
