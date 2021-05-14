package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.model.Course;
import ca.aquiletour.core.models.courses.student.TaskCompletion;
import ca.aquiletour.core.models.courses.task_types.GitExerciseTask;
import ca.aquiletour.core.models.courses.task_types.GitRepoTask;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class CourseViewModelStudent extends CourseViewModel<Course, CourseViewStudent> {

	@Override
	protected void handle(Course model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
		
		if(currentTask() != null
				&& currentTask().hasType(GitExerciseTask.class)) {
			
			view.enableCompletionCheckbox(false);
			view.displayGitRepoForm(false);
		}

		if(currentTask() != null
				&& currentTask().hasType(GitRepoTask.class)) {
			
			view.displayCompletionCheckbox(false);
			view.displayGitRepoForm(true);
		}
	}

	@Override
	protected void displayStudentCompletion(String studentId, TaskCompletion completion, CourseViewStudent view) {
		T.call(this);

		if(studentId.equals(Ntro.currentUser().getId())) {
			
			view.checkCompletion(true);
		}
	}
}
