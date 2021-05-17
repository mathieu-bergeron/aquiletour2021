package ca.aquiletour.core.pages.course.student.handlers;

import ca.aquiletour.core.models.courses.teacher.CourseTeacher;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class CourseViewModelStudent extends CourseViewModel<CourseTeacher, CourseViewStudent> {

	@Override
	protected void handle(CourseTeacher model, CourseViewStudent view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
	}

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewStudent view) {
		T.call(this);

		if(studentId.equals(Ntro.currentUser().getId())) {
			
			view.checkCompletion(true);
		}
	}
}
