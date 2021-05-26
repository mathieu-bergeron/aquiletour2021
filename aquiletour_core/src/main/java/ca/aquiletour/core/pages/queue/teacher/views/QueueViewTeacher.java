package ca.aquiletour.core.pages.queue.teacher.views;

import ca.aquiletour.core.pages.queue.views.QueueView;

public interface QueueViewTeacher extends QueueView {

	void displayIfQueueOpen(boolean isQueueOpen);

	void updateIsQueueOpenCheckbox(String queueId, Boolean isOpen);

}
