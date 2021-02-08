package ca.ntro.core.services.stores;

public class DocumentPath {
	
	private String collection;
	private String documentId;

	public DocumentPath(String collection, String firstPathName, String... pathRemainder) {
		this.collection = collection;

		this.documentId = firstPathName;

		for(int i = 0; i < pathRemainder.length; i++) {
			this.documentId += "__" + pathRemainder[i];
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
