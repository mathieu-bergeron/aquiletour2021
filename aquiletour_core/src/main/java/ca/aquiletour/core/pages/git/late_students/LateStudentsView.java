package ca.aquiletour.core.pages.git.late_students;

import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.mvc.NtroView;

public interface LateStudentsView extends NtroView {
	
	void displayLateStudents(LateStudentsModel lateStudentsModel);

}
