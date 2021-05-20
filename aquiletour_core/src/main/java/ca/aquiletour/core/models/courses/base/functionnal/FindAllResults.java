package ca.aquiletour.core.models.courses.base.functionnal;

import java.util.ArrayList;

import ca.aquiletour.core.models.courses.base.Task;
import ca.ntro.core.system.trace.T;

public class FindAllResults extends ArrayList<FindResult> {
	private static final long serialVersionUID = -5465197643346101186L;

	public void addOrUpdateFindResult(int distance, Task task) {
		T.call(this);
		
		FindResult result = resultByTask(task);
		
		if(result == null) {
			
			
		}else {
			
		}
	}
	
	

}
