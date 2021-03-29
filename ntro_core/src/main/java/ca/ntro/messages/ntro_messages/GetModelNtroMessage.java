package ca.ntro.messages.ntro_messages;

import ca.ntro.core.models.NtroModel;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.messages.NtroUserMessage;
import ca.ntro.stores.DocumentPath;

public class GetModelNtroMessage extends NtroUserMessage implements NtroModelMessage {
	
	private DocumentPath documentPath;
	private Class<? extends NtroModel> targetClass;

	public DocumentPath getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(DocumentPath documentPath) {
		this.documentPath = documentPath;
	}

	@Override
	public Class<? extends NtroModel> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<? extends NtroModel> targetClass) {
		this.targetClass = targetClass;
	}
}
