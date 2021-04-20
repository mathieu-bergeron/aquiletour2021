package ca.aquiletour.core.pages.course_list.models;


import ca.aquiletour.core.models.courses.CoursePath;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class CourseListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();
	private ObservableCourseDescriptionList courses = new ObservableCourseDescriptionList();

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}

	public ObservableCourseDescriptionList getCourses() {
		return courses;
	}

	public void setCourses(ObservableCourseDescriptionList courses) {
		this.courses = courses;
	}


	public void addCourse(CourseItem courseDescription) {
		T.call(this);
		
		courses.addItem(courseDescription);
	}

	public void addSemesterId(String semesterId) {
		T.call(this);
		
		semesters.addItem(semesterId);
	}

	public void addGroup(String semesterId, String courseId, String groupId) {
		T.call(this);
		
		CourseItem course = courseById(semesterId, courseId);
		
		if(course != null) {
			course.addGroup(groupId);
		}
	}

	private CourseItem courseById(String semesterId, String courseId) {
		T.call(this);
		
		CourseItem result = null;
		
		for(CourseItem candidate : getCourses().getValue()) {
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
		
		CourseItem course = courseById(coursePath.semesterId(), coursePath.courseId());
		
		if(course != null) {

			course.addTask(task);
		}
	}
}
