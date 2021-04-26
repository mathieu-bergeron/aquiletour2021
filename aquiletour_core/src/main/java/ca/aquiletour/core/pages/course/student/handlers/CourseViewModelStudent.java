package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseModelStudent, CourseViewStudent> {

	@Override
	protected void handle(CourseModelStudent model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
		/*
		if(getCurrentTask() != null && isTaskEmpty(getCurrentTask())) {
			
			((CourseViewStudent) view).displayGitRepoForm();

		}else {

			((CourseViewStudent) view).hideGitRepoForm();

		}*/
	}
	
	private boolean isTaskEmpty(Task task) {
		T.call(this);
		
		return task.getPreviousTasks().size() == 0
				&& task.getSubTasks().size() == 0
				&& task.getNextTasks().size() == 0;
	}
}
