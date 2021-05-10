package ca.aquiletour.core.pages.git.late_students;

import ca.ntro.core.mvc.NtroView;

public interface LateStudentsView extends NtroView {
	
	void displayLateStudents(LateStudentsModel lateStudentsModel);

}
