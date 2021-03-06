package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiExerciseMessage;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class GetCommitsForPath extends GitApiExerciseMessage implements NtroModelMessage {
	
	public GetCommitsForPath() {
		super();
		T.call(this);
	}

	public GetCommitsForPath(AquiletourGitMessage message) {
		super(message);
		T.call(this);
	}

	@Override
	public DocumentPath getDocumentPath() {
		T.call(this);
		
		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(CommitListModel.class));

		documentPath.setDocumentId(documentIdAsPath().toFileName());

		return documentPath;
	}
	
	protected Path documentIdAsPath() {
		T.call(this);
		Path exercisePath = new Path(getExercisePath());

		Path path = new Path();
		path.getNames().add(getCourseId());
		path.getNames().add(getSemesterId());
		path.getNames().add(getGroupId());
		path.getNames().add(getStudentId());
		for(int i = 0; i < exercisePath.nameCount(); i++) {
			path.getNames().add(exercisePath.name(i));
		}
		
		return path;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		T.call(this);
		
		return CommitListModel.class;
	}
}
