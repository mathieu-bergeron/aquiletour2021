package ca.aquiletour.core.pages.queues;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.users.UserView;
import ca.ntro.core.mvc.NtroView;

public interface QueuesView extends NtroView {

	void appendQueue(QueueSummaryView queueView);
}
