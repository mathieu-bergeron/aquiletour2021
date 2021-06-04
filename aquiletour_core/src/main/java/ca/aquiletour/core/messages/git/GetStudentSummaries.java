package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesModel;
import ca.aquiletour.core.pages.git.student_summaries.messages.ShowStudentSummariesMessage;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class GetStudentSummaries extends ShowStudentSummariesMessage implements NtroModelMessage {
	

	@Override
	public DocumentPath documentPath() {
		T.call(this);
		
		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(StudentSummariesModel.class));

		documentPath.setDocumentId(documentIdAsPath().toFileName());

		return documentPath;
	}
	
	protected Path documentIdAsPath() {
		T.call(this);

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
		
		return StudentSummariesModel.class;
	}
}
