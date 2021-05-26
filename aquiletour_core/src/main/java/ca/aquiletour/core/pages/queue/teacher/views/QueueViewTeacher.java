package ca.aquiletour.core.pages.queue.teacher.views;

import ca.aquiletour.core.pages.queue.views.QueueView;

public interface QueueViewTeacher extends QueueView {

	void displayIfQueueOpen(boolean isOpen);
	void displayIfQueueOpen(String courseId, boolean isOpen);
	void displayIfQueueOpen(String courseId, String groupId, boolean isOpen);

	void clearQueueMenu();
	void addToQueueMenu(String courseId);
	void addToQueueMenu(String courseId, String groupId);
	void displayCourseTitle(String courseId, String title);
	void removeFromQueueMenu(String courseId);
	void removeFromQueueMenu(String courseId, String groupId);

}
