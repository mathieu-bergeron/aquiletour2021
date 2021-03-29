package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.ntro.core.Path;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class GetCommitListMessage extends StudentExerciseMessage implements NtroModelMessage {
	
	private long fromDate;
	private long toDate;

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	@Override
	public DocumentPath getDocumentPath() {
		T.call(this);
		
		DocumentPath documentPath = new DocumentPath();
		documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(CommitListModel.class));
		
		Path exercisePath = new Path(getExerciseId());

		Path path = new Path();
		path.getNames().add(getCourseId());
		path.getNames().add(getSemesterId());
		path.getNames().add(getGroupId());
		path.getNames().add(getStudentId());
		for(int i = 0; i < exercisePath.nameCount(); i++) {
			path.getNames().add(exercisePath.name(i));
		}
		path.getNames().add(String.valueOf(getFromDate()));
		path.getNames().add(String.valueOf(getToDate()));

		documentPath.setDocumentId(path.toFileName());

		return documentPath;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		T.call(this);
		
		return CommitListModel.class;
	}
}
