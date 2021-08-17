package ca.aquiletour.core.models.logs;


import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class LogItems<LI extends LogItem> extends StoredList<LI> {

	public int longuestTaskPath() {
		T.call(this);
		
		return reduceTo(Integer.class, 0, (index, logItem, accumulator) -> {
			
			TaskPath taskPath = logItem.getTaskPath();
			
			if(taskPath != null) {

				Integer currentLength = logItem.getTaskPath().nameCount();

				if(currentLength > accumulator){

					accumulator = currentLength;
				}
			}
			
			return accumulator;
		});
	}

}
