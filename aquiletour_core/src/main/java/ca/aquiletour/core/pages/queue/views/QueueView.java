package ca.aquiletour.core.pages.queue.views;

import java.util.List;

import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;

public interface QueueView extends NtroView {

	void insertAppointment(int index, AppointmentView appointmentView);
	void moveAppointment(int index, String subViewId);
	void deleteSubViewsNotInList(List<String> currentAppointmentIds);

	void deleteSubView(String subViewId);
	void removeSubViewFromDocument(String subViewId);
	void hideSubView(String subViewId);

	Class<? extends AppointmentView> appointmentViewClass();

	void onContextChange(NtroContext<?,?> context);
}
