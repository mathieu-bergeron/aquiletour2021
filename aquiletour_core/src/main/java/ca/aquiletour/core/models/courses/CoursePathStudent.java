package ca.aquiletour.core.models.courses;

import ca.ntro.core.system.trace.T;

public class CoursePathStudent extends CoursePath {

	private final int STUDENT_INDEX = 3;
	
	public static CoursePathStudent fromCoursePath(CoursePath coursePath, String studentId) {
		T.call(CoursePathStudent.class);
		
		CoursePathStudent path = new CoursePathStudent(coursePath.teacherId(), coursePath.semesterId(), coursePath.courseId(), studentId);
		
		return path;
	}
	
	public CoursePathStudent(String teacherId, String semesterId, String courseId, String studentId) {
		super(teacherId, semesterId, courseId);
		
		addName(studentId);
	}

	public String studentId() {
		T.call(this);

		return getNames().get(STUDENT_INDEX);
	}
}
