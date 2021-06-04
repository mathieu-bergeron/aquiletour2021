package ca.aquiletour.core.models.logs;

import ca.ntro.core.system.trace.T;

public class LogItemQueue extends LogItem {

	@Override
	protected void writeCsvLineAfterBasicInfo(String separator, StringBuilder builder) {
		T.call(this);

	}
}
