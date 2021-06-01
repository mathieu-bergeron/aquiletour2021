package ca.aquiletour.core.pages.git.late_students;

import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;

public class LateStudentsViewModel extends ModelViewSubViewHandler<LateStudentsModel, LateStudentsView>  {
	


	@Override
	protected void handle(LateStudentsModel model, LateStudentsView view, ViewLoader subViewLoader) {
		// TODO Auto-generated method stub
		 view.displayLateStudents(model);
	}
}
