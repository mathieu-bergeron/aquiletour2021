package ca.aquiletour.web.pages.queue.student;

import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.web.pages.queue.QueueViewWeb;

public class QueueViewWebStudent extends QueueViewWeb implements QueueViewStudent {

	@Override
	public void initializeCloseQueueButton(String courseId) {
		// XXX: not supported
	}
}
