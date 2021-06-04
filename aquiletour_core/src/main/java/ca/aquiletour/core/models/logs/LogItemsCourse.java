package ca.aquiletour.core.models.logs;

import ca.ntro.core.system.trace.T;

public class LogItemsCourse extends LogItems<LogItemCourse> {

	public int longuestTaskPath() {
		T.call(this);
		
		return reduceTo(Integer.class, 0, (index, courseLogItem, accumulator) -> {
			
			Integer currentLength = courseLogItem.getTaskPath().nameCount();

			if(currentLength > accumulator){

				accumulator = currentLength;
			}
			
			return accumulator;
		});
	}

}
