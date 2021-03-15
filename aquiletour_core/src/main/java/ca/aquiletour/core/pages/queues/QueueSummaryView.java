package ca.aquiletour.core.pages.queues;

import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.ntro.core.mvc.NtroView;

public interface QueueSummaryView extends NtroView {
	
	void displaySummary(QueueSummary queue);
}
