package ca.aquiletour.core.pages.queue.teacher.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.queue.views.QueueView;

public interface QueueViewTeacher extends QueueView {

	void displayIfQueueOpen(boolean isOpen);
	void displayIfQueueOpen(CoursePath coursePath, boolean isOpen);
	void displayIfQueueOpen(CoursePath coursePath, String groupId, boolean isOpen);

	void clearQueueMenu();
	void addToQueueMenu(CoursePath coursePath);
	void addToQueueMenu(CoursePath coursePath, String groupId);
	void displayCourseTitle(CoursePath coursePath, String title);
	void removeFromQueueMenu(CoursePath coursePath);
	void removeFromQueueMenu(CoursePath coursePath, String groupId);

}
