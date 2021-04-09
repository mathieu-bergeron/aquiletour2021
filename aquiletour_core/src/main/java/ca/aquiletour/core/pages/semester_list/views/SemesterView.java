package ca.aquiletour.core.pages.semester_list.views;

import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.mvc.NtroView;

public interface SemesterView extends NtroView {

	void displaySemester(SemesterModel item);

}
