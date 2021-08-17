package ca.aquiletour.core.pages.dashboard.teacher.models;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.system.trace.T;

public class CurrentTaskTeacher extends CurrentTask {
	
	private StoredInteger numberOfStudents = new StoredInteger();

	public CurrentTaskTeacher() {
		super();
		T.call(this);
	}

	public CurrentTaskTeacher(Task task) {
		super(task);
		T.call(this);
		updateNumberOfStudents(1);
	}

	public StoredInteger getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(StoredInteger numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public void updateNumberOfStudents(int numberOfStudents) {
		T.call(this);
		
		getNumberOfStudents().set(numberOfStudents);
	}

	public static CurrentTaskTeacher currentTaskByPath(List<CurrentTaskTeacher> currentTasks, TaskPath path) {
		T.call(CurrentTaskTeacher.class);
		
		CurrentTaskTeacher result = null;
		
		for(CurrentTaskTeacher candidate : currentTasks) {
			if(candidate.getTaskPath().equals(path)) {
				result = candidate;
				break;
			}
		}
		
		return result;
	}

	public void incrementNumberOfStudent(int inc) {
		T.call(this);

		updateNumberOfStudents(getNumberOfStudents().getValue() + inc);
	}

}
