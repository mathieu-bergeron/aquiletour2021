package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.student.CompletionByAtomicTaskId;
import ca.aquiletour.core.models.courses.student.CourseStudent;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelStudent extends CourseViewModel<CourseStudent, CourseViewStudent> {

	@Override
	protected void handle(CourseStudent model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
	}

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewStudent view) {
		T.call(this);

		view.checkCompletion(true);
	}

	protected void observeCompletions(CourseStudent model, CourseViewStudent view) {
		T.call(this);
		
		model.getCompletions().onEntryAdded(new EntryAddedListener<CompletionByAtomicTaskId>() {
			@Override
			public void onEntryAdded(String atomicTaskId, CompletionByAtomicTaskId value) {
				T.call(this);
				
				displayStudentCompletion(null, view);
			}
		});
	}
}
