package ca.aquiletour.core.pages.course_list.models;


import ca.aquiletour.core.models.courses.CoursePath;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public abstract class CourseListModel implements NtroModel {
	
	private SemesterIds activeSemesters = new SemesterIds();
	private SemesterIds allSemesters = new SemesterIds();
	private ObservableCourseDescriptionList courses = new ObservableCourseDescriptionList();

	public SemesterIds getAllSemesters() {
		return allSemesters;
	}

	public void setAllSemesters(SemesterIds allSemesters) {
		this.allSemesters = allSemesters;
	}
	
	public SemesterIds getActiveSemesters() {
		return activeSemesters;
	}

	public void setActiveSemesters(SemesterIds activeSemesters) {
		this.activeSemesters = activeSemesters;
	}

	public ObservableCourseDescriptionList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseDescriptionList courses) {
		this.courses = courses;
	}


	public void addCourse(CourseListItem courseDescription) {
		T.call(this);
		
		courses.addItem(courseDescription);
	}

	public void addSemesterId(String semesterId) {
		T.call(this);

		if(!allSemesters.contains(semesterId)) {
			allSemesters.addItem(semesterId);
		}
	}

	public void addGroup(String semesterId, String courseId, String groupId) {
		T.call(this);
		
		CourseListItem course = courseById(semesterId, courseId);
		
		if(course != null) {
			course.addGroup(groupId);
		}
	}

	public CourseListItem courseById(String semesterId, String courseId) {
		T.call(this);
		
		CourseListItem result = null;
		
		for(CourseListItem candidate : getCourses().getValue()) {
			if(candidate.getSemesterId().equals(semesterId)
					&& candidate.getCourseId().equals(courseId)) {
				
				result = candidate;
				break;
			}
		}

		return result;
	}

	public void addTask(CoursePath coursePath, TaskDescription task) {
		T.call(this);
		
		CourseListItem course = courseById(coursePath.semesterId(), coursePath.courseId());
		
		if(course != null) {

			course.addTask(task);
		}
	}

	public void openQueue(String semesterId, String courseId) {
		T.call(this);

		CourseListItem course = courseById(semesterId, courseId);
		
		if(course != null) {

			course.updateQueueOpen(true);
		}
	}

	public void closeQueue(String semesterId, String courseId) {
		T.call(this);

		CourseListItem course = courseById(semesterId, courseId);
		
		if(course != null) {

			course.updateQueueOpen(false);
		}
	}

	public boolean isActiveSemester(String semesterId) {
		T.call(this);

		return getActiveSemesters().contains(semesterId);
	}

}
