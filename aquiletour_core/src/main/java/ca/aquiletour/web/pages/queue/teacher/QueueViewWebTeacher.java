package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.pages.dashboard.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class QueueViewWebTeacher extends QueueViewWeb implements QueueViewTeacher {

	private HtmlElement queueId;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		super.initializeViewWeb(context);
		T.call(this);

		queueId = this.getRootElement().find("#queue-id").get(0);

		MustNot.beNull(queueId);
	}
	
	
	@Override
	public void initializeCloseQueueButton(String courseId) {
		T.call(this);
		
		queueId.text(courseId);
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
