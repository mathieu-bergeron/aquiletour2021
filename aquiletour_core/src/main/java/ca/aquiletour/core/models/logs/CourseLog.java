package ca.aquiletour.core.models.logs;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.Ntro;

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

	public void addAtomicTaskCompletion(TaskPath taskPath, 
			                            String groupId, 
			                            String studentId, 
			                            String atomicTaskId, 
			                            AtomicTaskCompletion completion) {
		T.call(this);
		
		List<String> eventItems = new ArrayList<>();
		
		eventItems.add(atomicTaskId);
		
		if(completion != null) {
			eventItems.addAll(completion.logItems());
			
		}
		
		NtroDate timestamp = Ntro.calendar().now();
		
		CourseLogItem item = new CourseLogItem(timestamp, 
				                               taskPath, 
				                               groupId, 
				                               studentId, 
				                               eventItems);
		
		getCourseLogItems().addItem(item);
	}
}
