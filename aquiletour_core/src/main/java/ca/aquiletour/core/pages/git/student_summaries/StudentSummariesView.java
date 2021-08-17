package ca.aquiletour.core.pages.git.student_summaries;

import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.mvc.NtroView;

public interface StudentSummariesView extends NtroView  {
	
	void appendStudentSummary(StudentSummary studentSummary, StudentSummaryView studentSummaryView);
	void displayStudentSummaries(StudentSummariesModel studentSummariesModel);
}
