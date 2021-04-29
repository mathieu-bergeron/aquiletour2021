package ca.aquiletour.core.models.courses.base;

import ca.ntro.core.models.StoredMap;
import ca.ntro.core.system.trace.T;

public class ObservableTaskMap extends StoredMap<Task> {
	
	private CourseModelBase course = null;

	public ObservableTaskMap() {
		super();
		T.call(this);
	}

	public ObservableTaskMap(CourseModelBase course) {
		super();
		T.call(this);
		
		this.course = course;
	}
	
	public CourseModelBase getCourse() {
		return course;
	}

	public void setCourse(CourseModelBase course) {
		this.course = course;
	}
	
	@Override
	public void putEntry(String key, Task task) {
		T.call(this);
		
		task.setGraph(course.asGraph());
		
		super.putEntry(key, task);
	}

}
