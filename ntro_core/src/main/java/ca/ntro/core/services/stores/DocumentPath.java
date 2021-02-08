package ca.ntro.core.services.stores;

public class DocumentPath {
	
	private String collection;
	private String documentId;

	public DocumentPath(String collection, String... modelPath) {
		this.collection = collection;

		if(modelPath.length > 0) {
			this.documentId = modelPath[0];
		}
		for(int i = 1; i < modelPath.length; i++) {
			this.documentId += "__" + modelPath[i];
		}
	}
	
	public DocumentPath(String collection, String documentId) {
		this.collection = collection;
		this.documentId = documentId;
	}

	public String getCollection() {
		return collection;
	}

	public String getId() {
		return documentId;
	}

}
