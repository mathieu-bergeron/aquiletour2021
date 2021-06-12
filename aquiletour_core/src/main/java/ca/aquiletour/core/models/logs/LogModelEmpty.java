package ca.aquiletour.core.models.logs;

public class LogModelEmpty extends LogModel {

	@Override
	public LogItems getLogItems() {
		return null;
	}

	@Override
	public void setLogItems(LogItems logItems) {
	}

	@Override
	protected void writeCsvHeader(String separator, StringBuilder builder) {
	}
}
