package ca.aquiletour.core.models.logs;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseLog implements NtroModel {
	
	private CourseLogItems courseLogItems = new CourseLogItems();

	public CourseLogItems getCourseLogItems() {
		return courseLogItems;
	}

	public void setCourseLogItems(CourseLogItems courseLogItems) {
		this.courseLogItems = courseLogItems;
	}
	
	public void writeCsvFileContent(String separator, StringBuilder builder) {
		T.call(this);
		
		int longuestTaskPath = courseLogItems.longuestTaskPath();
		
		courseLogItems.forEachItem((index, courseLogItem) -> {

			courseLogItem.writeCsvLine(longuestTaskPath, separator, builder);

			builder.append(System.lineSeparator());
		});
	}

	public void addAtomicTaskCompletion(Path taskPath, 
			                            String groupId, 
			                            String studentId, 
			                            String atomicTaskId, 
			                            AtomicTaskCompletion completion) {
		T.call(this);
		
		List<String> eventItems = 
		
		CourseLogItem item = new CourseLogItem(taskPath, groupId, studentId);
		
		
	}
}
