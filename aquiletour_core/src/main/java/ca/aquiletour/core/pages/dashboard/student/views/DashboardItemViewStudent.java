package ca.aquiletour.core.pages.dashboard.student.views;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.views.DashboardItemView;

public interface DashboardItemViewStudent extends DashboardItemView<CurrentTaskStudent> {

	public void updateAppointmentButtonsFor(CoursePath coursePath, Boolean shouldDisplay);

}
