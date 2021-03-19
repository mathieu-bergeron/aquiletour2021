package ca.aquiletour.core.pages.queues;

import ca.ntro.core.mvc.NtroView;

public interface QueuesView extends NtroView {

	void appendQueue(QueueSummaryView queueView);
}
