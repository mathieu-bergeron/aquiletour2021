package ca.ntro.messages;

import ca.ntro.core.models.NtroModel;
import ca.ntro.stores.DocumentPath;

public class NtroModelMessageImpl implements NtroModelMessage {
	
	private DocumentPath documentPath;
	private Class<? extends NtroModel> targetClass;

	@Override
	public DocumentPath documentPath() {
		return documentPath;
	}

	@Override
	public Class<? extends NtroModel> targetClass() {
		return targetClass;
	}

	public void setDocumentPath(DocumentPath documentPath) {
		this.documentPath = documentPath;
	}

	public void setTargetClass(Class<? extends NtroModel> targetClass) {
		this.targetClass = targetClass;
	}

}
