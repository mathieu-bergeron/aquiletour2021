package ca.aquiletour.core.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.mvc.NtroView;

public interface StudentSummaryView extends NtroView {
	
	void displayStudentSummary(StudentSummary studentSummary);

}
