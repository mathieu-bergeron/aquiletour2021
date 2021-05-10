package ca.aquiletour.core.pages.course.student.views;

import ca.aquiletour.core.pages.course.views.CourseView;

public interface CourseViewStudent extends CourseView  {
	
	void displayGitRepoForm();
	void hideGitRepoForm();
	void showCompletionCheckbox(boolean show);
	void checkCompletion(boolean check);

}
