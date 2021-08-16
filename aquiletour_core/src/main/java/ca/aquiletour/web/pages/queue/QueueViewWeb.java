package ca.aquiletour.web.pages.queue;

import java.util.List;

import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
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
		
		onContextChange(context);
	}

	@Override
	public void onContextChange(NtroContext<?,?> context) {
		T.call(this);

		/*
		if(Ntro.isJSweet()) {
			
			Log.info("onContextChange: appointment-view.forEach");

			getRootElement().find(".appointment-view").forEach(e -> {
				AppointmentViewWeb appointementView = Ntro.factory().newInstance(AppointmentViewWeb.class);
				appointementView.setRootElement(e);
				appointementView.initializeViewWeb(context);
			});
		}*/
	}

	@Override
	public void insertAppointment(int index, AppointmentView appointmentView) {
		T.call(this);
		
		System.out.println("insertAppointment");
		
		HtmlElement appointmentElement = ((AppointmentViewWeb) appointmentView).getRootElement();

		if(index >= 0 && index < appointmentList.children("*").size()) {

			HtmlElement anchorElement = appointmentList.children("*").get(index);
			appointmentElement.insertBefore(anchorElement);

		}else {

			appointmentList.appendElement(appointmentElement);
		}
	}

	@Override
	public void moveAppointment(int index, String subViewId) {
		T.call(this);

		AppointmentViewWeb appointmentView = (AppointmentViewWeb) findSubView(appointmentViewClass(), subViewId);

		if(appointmentView != null) {
			
			int currentIndex = currentAppointmentIndex(appointmentView);
			
			if(currentIndex != index) {

				removeSubViewFromDocument(subViewId);
				insertAppointment(index, appointmentView);
			}
		}
	}

	private int currentAppointmentIndex(AppointmentViewWeb appointmentView) {
		T.call(this);
		
		int currentIndex = -1;
		
		HtmlElement appointmentElement = appointmentView.getRootElement();
		
		if(appointmentElement != null) {
			
			String appointmentId = appointmentElement.getAttribute("id");
			
			if(appointmentId != null
					&& !appointmentId.isEmpty()) {

				for(int i = 0; i < appointmentList.children("*").size(); i++) {
					
					HtmlElement candidateElement = appointmentList.children("*").get(i);
					
					String candidateId = candidateElement.getAttribute("id");
					
					if(appointmentId.equals(candidateId)) {
						
						currentIndex = i;
						break;
					}
				}
			}

		}

		return currentIndex;
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

	@Override
	public void deleteSubView(String subViewId) {
		T.call(this);
		
		HtmlElement subViewElement = getRootElement().find("#" + subViewId).get(0);
		
		if(subViewElement != null) {

			subViewElement.deleteForever();
		}
	}

	@Override
	public void removeSubViewFromDocument(String subViewId) {
		T.call(this);
		
		HtmlElement subViewElement = getRootElement().find("#" + subViewId).get(0);
		
		if(subViewElement != null) {

			subViewElement.removeFromDocument();
		}
	}

	@Override
	public void hideSubView(String subViewId) {
		T.call(this);

		HtmlElement subViewElement = getRootElement().find("#" + subViewId).get(0);

		if(subViewElement != null) {
			subViewElement.hide();
		}
	}
}
