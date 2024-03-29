package ca.aquiletour.core.pages.queue.student.views;

import ca.aquiletour.core.pages.queue.views.QueueView;

public interface QueueViewStudent extends QueueView {

	void displayQueueMessage(String queueMessage);
	void displayTeacherName(String value);
	void hideQueueMessage();

	void displayMakeAppointmentButton(boolean shouldDisplay);
}
