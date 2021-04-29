package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.model.CourseModel;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.task_types.GitExerciseTask;
import ca.aquiletour.core.models.courses.task_types.GitRepoTask;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseModel, CourseViewStudent> {

	@Override
	protected void handle(CourseModel model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
		
		if(currentTask() != null
				&& currentTask().hasType(GitExerciseTask.class)) {
			
			view.showCompletionCheckbox(false);

		}
	}
}
