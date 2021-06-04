package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiExerciseMessage;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class GetCommitsForPath extends GitApiExerciseMessage implements NtroModelMessage {
	
	private String authToken = "";
	
	public GetCommitsForPath() {
		super();
		T.call(this);
	}

	public GetCommitsForPath(AquiletourGitMessage message) {
		super(message);
		T.call(this);
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public DocumentPath documentPath() {
		T.call(this);
		
		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(CommitListModel.class));

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
		path.getNames().add(getStudentId());
		for(int i = 0; i < exercisePath.nameCount(); i++) {
			path.getNames().add(exercisePath.name(i));
		}
		
		return path;
	}

	public static GetCommitsForPath fromDocumentPath(DocumentPath documentPath) {
		T.call(GetCommitsForPath.class);
		
		Path path = Path.fromFileName(documentPath.getDocumentId());
		
		GetCommitsForPath getCommitsForPath = new GetCommitsForPath();
		
		getCommitsForPath.setCourseId(path.name(0));
		getCommitsForPath.setSemesterId(path.name(1));
		getCommitsForPath.setGroupId(path.name(2));
		getCommitsForPath.setStudentId(path.name(3));
		getCommitsForPath.setExercisePath(path.subPath(4).toRawPath());

		return getCommitsForPath;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		T.call(this);
		
		return CommitListModel.class;
	}

}
