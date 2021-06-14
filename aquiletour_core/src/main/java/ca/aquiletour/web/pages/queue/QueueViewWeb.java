package ca.aquiletour.web.pages.queue;

import java.util.List;

import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class QueueViewWeb extends NtroViewWeb implements QueueView {

	private HtmlElement appointmentList;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		appointmentList = this.getRootElement().find("#appointment-list").get(0);

		MustNot.beNull(appointmentList);
	}

	@Override
	public void insertAppointment(int index, AppointmentView appointmentView) {
		T.call(this);
		
		HtmlElement appointmentElement = ((AppointmentViewWeb) appointmentView).getRootElement();

		if(index >= 0 && index < appointmentList.children("*").size()) {

			HtmlElement anchorElement = appointmentList.children("*").get(index);
			appointmentElement.insertBefore(anchorElement);

		}else {

			appointmentList.appendElement(appointmentElement);
		}
	}

	@Override
	public void deleteSubViewsNotInList(List<String> currentAppointmentIds) {
		T.call(this);
		
		HtmlElements appointments = getRootElement().find(".appointment-view");
		
		appointments.forEach(e -> {
			
			String appointmentId = e.getAttribute("id");
			
			if(!currentAppointmentIds.contains(appointmentId)) {

				e.deleteForever();
			}
		});
	}
}
