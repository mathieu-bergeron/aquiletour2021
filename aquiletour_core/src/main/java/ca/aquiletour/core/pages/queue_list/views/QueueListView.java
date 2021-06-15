package ca.aquiletour.core.pages.queue_list.views;

import ca.ntro.core.mvc.NtroView;

public interface QueueListView extends NtroView {

	void appendQueueItem(String queueId, QueueListItemView queueView);

}
