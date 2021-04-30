package ca.aquiletour.core.pages.git.late_students;

import java.util.List;

import ca.aquiletour.core.pages.course.models.CourseModel;
import ca.aquiletour.core.pages.course.models.Task;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.Path;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelSubModelViewSubViewHandler;
import ca.ntro.core.mvc.ModelViewHandler;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class LateStudentsViewModel extends ModelViewSubViewHandler<LateStudentsModel, LateStudentsView>  {
	

	@Override
	protected void handle(LateStudentsModel model, LateStudentsView view, ViewLoader subViewLoader) {
		T.call(this);
		T.here();
		view.displayLateStudents(model);
		
	}
}
