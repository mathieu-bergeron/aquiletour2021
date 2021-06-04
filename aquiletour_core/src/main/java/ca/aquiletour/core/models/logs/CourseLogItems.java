package ca.aquiletour.core.models.logs;

import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;

public class CourseLogItems extends StoredList<CourseLogItem> {

	public int longuestTaskPath() {
		T.call(this);
		
		return reduceTo(Integer.class, null, (index, courseLogItem, accumulator) -> {

			Integer currentLength = courseLogItem.getTaskPath().nameCount();

			if(accumulator == null) {

				accumulator = currentLength;

			}else if(currentLength > accumulator){

				accumulator = currentLength;
			}
			
			return accumulator;
		});
	}

}
