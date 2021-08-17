package ca.aquiletour.core.models.paths;

import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;

public class SemesterPath extends Path {
	
	private final int TEACHER_INDEX = 0;
	private final int SEMESTER_INDEX = 1;

	public SemesterPath() {
		super();
		T.call(this);
	}

	public SemesterPath(String teacherId, String semesterId) {
		super();
		
		addName(teacherId);
		addName(semesterId);
	}

	public String semesterId() {
		T.call(this);

		return getNames().get(SEMESTER_INDEX);
	}

	public String teacherId() {
		T.call(this);

		return getNames().get(TEACHER_INDEX);
	}
	
	public static SemesterPath fromKey(String courseKey) {
		T.call(SemesterPath.class);
		
		SemesterPath semesterPath = new SemesterPath();

		semesterPath.copyNamesOf(Path.fromKey(courseKey));

		return semesterPath;
	}
}
