package ca.aquiletour.core.pages.queue.views;

import java.util.List;

import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void insertAppointment(int index, AppointmentView appointmentView);
	void deleteSubViewsNotInList(List<String> currentAppointmentIds);
	void deleteSubView(String subViewId);
	void hideSubView(String subViewId);
}
