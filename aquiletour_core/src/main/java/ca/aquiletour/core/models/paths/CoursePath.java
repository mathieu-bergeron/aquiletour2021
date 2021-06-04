package ca.aquiletour.core.models.paths;

import ca.aquiletour.core.Constants;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class CoursePath extends SemesterPath {
	
	private static final CoursePath allCourses = new CoursePath(Constants.ALL_TEACHERS_ID, Constants.ACTIVE_SEMESTERS_ID, Constants.ALL_COURSES_ID);
	
	private final int COURSE_INDEX = 2;

	public CoursePath() {
		super();
		T.call(this);
	}

	public CoursePath(String teacherId, String semesterId, String courseId) {
		super(teacherId, semesterId);

		addName(courseId);
	}

	public Path toUrlPath() {
		T.call(this);

		Path path = new Path();
		path.addName(teacherId());
		path.addName(semesterId());
		path.addName(getNames().get(COURSE_INDEX));
		return path;
	}

	public String courseId() {
		T.call(this);

		return getNames().get(COURSE_INDEX);
	}


	public static CoursePath allCourses() {
		T.call(CoursePath.class);
		
		return allCourses;
	}

	public boolean isAllCourses() {
		T.call(this);

		return this == allCourses;
	}

	public static CoursePath fromKey(String courseKey) {
		T.call(CoursePath.class);
		
		CoursePath coursePath = new CoursePath();

		coursePath.copyNamesOf(Path.fromKey(courseKey));

		return coursePath;
	}

	public static CoursePath fromPath(Path path) {
		T.call(CoursePath.class);

		CoursePath coursePath = new CoursePath();

		coursePath.copyNamesOf(path);

		return coursePath;
	}
}
