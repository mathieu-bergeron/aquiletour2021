package ca.aquiletour.web.pages.queue.teacher;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.web.pages.queue.QueueViewWeb;

public class TeacherQueueViewWeb extends QueueViewWeb implements QueueView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {

		HtmlElement closeQueueButton = getRootElement().find("#close-queue-button").get(0);
		
		MustNot.beNull(closeQueueButton);

		closeQueueButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				T.here();
				
				TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
				// FIXME
				teacherClosesQueueMessage.setCourseId("3C6");
				Ntro.messages().send(teacherClosesQueueMessage);
				
				Ntro.messages().send(Ntro.messages().create(ShowTeacherDashboardMessage.class));
			}
		});
		
	}

}
