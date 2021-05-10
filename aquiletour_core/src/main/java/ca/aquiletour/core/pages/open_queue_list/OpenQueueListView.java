package ca.aquiletour.core.pages.open_queue_list;

import ca.ntro.core.mvc.NtroView;

public interface OpenQueueListView extends NtroView {

	void appendQueue(String queueId, OpenQueueView queueView);
	void deleteQueue(String queueId);
}
