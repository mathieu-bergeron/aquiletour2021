package ca.aquiletour.core.models.logs;


import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class LogItems<LI extends LogItem> extends StoredList<LI> {
	
	private LogModel<?,?> logModel;
	public void registerLogModel(LogModel<?,?> logModel) {
		T.call(this);

		this.logModel = logModel;

		forEachItem((index, item) -> {
			item.registerLogModel(logModel);
		});
	}

	@Override
	public void addItem(LI item) {
		T.call(this);

		item.registerLogModel(logModel);
		super.addItem(item);
	}
}
