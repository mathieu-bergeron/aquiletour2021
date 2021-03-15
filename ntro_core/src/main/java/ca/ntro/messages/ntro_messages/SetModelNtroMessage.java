package ca.ntro.messages.ntro_messages;

import ca.ntro.core.models.NtroModel;
import ca.ntro.messages.NtroUserMessage;
import ca.ntro.stores.DocumentPath;

public class SetModelNtroMessage extends NtroUserMessage {
	
	private DocumentPath documentPath;
	private NtroModel model;
	
	public DocumentPath getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(DocumentPath documentPath) {
		this.documentPath = documentPath;
	}
	public NtroModel getModel() {
		return model;
	}
	public void setModel(NtroModel model) {
		this.model = model;
	}
	
	

}
