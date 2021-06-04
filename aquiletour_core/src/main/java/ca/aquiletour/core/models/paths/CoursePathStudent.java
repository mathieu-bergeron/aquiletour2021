package ca.aquiletour.core.models.paths;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class CoursePathStudent extends CoursePath {

	private final int STUDENT_INDEX = 3;

	public CoursePathStudent(CoursePath coursePath, String studentId) {
	}
	
	private CoursePathStudent(String teacherId, String semesterId, String courseId, String studentId) {
		super(teacherId, semesterId, courseId);
		
		addName(studentId);
	}

	public String studentId() {
		T.call(this);

		return getNames().get(STUDENT_INDEX);
	}

	public Path toUrlPath() {
		T.call(this);

		Path path = super.toUrlPath();
		path.addName(getNames().get(STUDENT_INDEX));
		return path;
	}

	public CoursePath toCoursePath() {
		T.call(this);

		return new CoursePath(teacherId(), semesterId(), courseId());
	}

	public static CoursePathStudent fromCoursePath(CoursePath coursePath, String studentId) {
		T.call(CoursePathStudent.class);
		
		CoursePathStudent path = new CoursePathStudent(coursePath.teacherId(), coursePath.semesterId(), coursePath.courseId(), studentId);
		
		return path;
	}
}
