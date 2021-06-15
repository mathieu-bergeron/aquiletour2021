package ca.aquiletour.core.pages.queue_list.views;

import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.ntro.core.mvc.NtroView;

public interface QueueListItemView extends NtroView {
	
	void displaySummary(QueueListItem queue);
}
