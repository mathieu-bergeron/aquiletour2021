package ca.aquiletour.core.pages.open_queue_list;

import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.ntro.core.mvc.NtroView;

public interface OpenQueueView extends NtroView {
	
	void displaySummary(OpenQueue queue);
}
