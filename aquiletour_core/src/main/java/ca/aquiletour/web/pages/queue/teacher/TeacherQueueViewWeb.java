package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class TeacherQueueViewWeb extends QueueViewWeb implements QueueView {

	@Override
	public void initializeCloseQueueButton(String courseId) {
		HtmlElement closeQueueButton = getRootElement().find("#close-queue-button").get(0);
		
		MustNot.beNull(closeQueueButton);

		closeQueueButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				sendCloseQueueMessage(courseId);
				sendShowDashboardMessage();
			}
		});
	}

	private void sendCloseQueueMessage(String courseId) {
		T.call(this);

		TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
		teacherClosesQueueMessage.setCourseId(courseId);
		Ntro.messages().send(teacherClosesQueueMessage);
	}

	private void sendShowDashboardMessage() {
		T.call(this);

		Ntro.messages().send(Ntro.messages().create(ShowTeacherDashboardMessage.class));
	}
}
