package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.late_students.LateStudentsModel;
import ca.aquiletour.core.pages.git.late_students.messages.ShowLateStudentsMessage;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class GetLateStudents extends ShowLateStudentsMessage implements NtroModelMessage {
	
	@Override
	public DocumentPath documentPath() {
		T.call(this);
		
		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(LateStudentsModel.class));

		documentPath.setDocumentId(documentIdAsPath().toFileName());

		return documentPath;
	}
	
	protected Path documentIdAsPath() {
		T.call(this);

		System.out.println(getExercisePath());
		System.out.println(getCourseId());
		Path exercisePath = Path.fromRawPath(getExercisePath());

		Path path = new Path();
		path.getNames().add(getCourseId());
		path.getNames().add(getSemesterId());
		path.getNames().add(getGroupId());
		path.getNames().add(getDeadline());
		for(int i = 0; i < exercisePath.nameCount(); i++) {
			path.getNames().add(exercisePath.name(i));
		}
		
		return path;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		T.call(this);
		
		return LateStudentsModel.class;
	}


}
