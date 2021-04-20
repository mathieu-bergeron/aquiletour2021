package ca.aquiletour.core.models.courses;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class CoursePath extends Path {
	
	private final int TEACHER_INDEX = 0;
	private final int SEMESTER_INDEX = 1;
	private final int COURSE_INDEX = 2;

	public CoursePath() {
		super();
		T.call(this);
	}

	public CoursePath(String teacherId, String semesterId, String courseId) {
		super();
		
		addName(teacherId);
		addName(semesterId);
		addName(courseId);
	}


	public Path toUrlPath() {
		T.call(this);

		Path path = new Path();
		path.addName(getNames().get(TEACHER_INDEX));
		path.addName(getNames().get(COURSE_INDEX));
		return path;
	}

	public String semesterId() {
		T.call(this);

		return getNames().get(SEMESTER_INDEX);
	}

	public String courseId() {
		T.call(this);

		return getNames().get(COURSE_INDEX);
	}

	public String teacherId() {
		T.call(this);

		return getNames().get(TEACHER_INDEX);
	}
	
	public String toId() {
		T.call(this);
		
		return toFileName();
	}
	
}
