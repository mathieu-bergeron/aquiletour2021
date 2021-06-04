package ca.aquiletour.core.models.logs;

import ca.ntro.core.system.trace.T;

public class LogModelQueue extends LogModel<LogItemQueue, LogItemsQueue> {
	
	private LogItemsQueue logItems = new LogItemsQueue();

	@Override
	public LogItemsQueue getLogItems() {
		return logItems;
	}

	@Override
	public void registerLogItems(LogItemsQueue logItems) {
		this.logItems = logItems;
	}

	@Override
	public void writeCsvFileContent(String separator, StringBuilder builder) {
		T.call(this);
		
	}

	@Override
	protected void writeCsvHeader(String separator, StringBuilder builder) {
		T.call(this);
	}

	@Override
	protected void writeCsvLine(LogItemQueue logItem, String separator, StringBuilder builder) {
		T.call(this);
		
		
	}
}
