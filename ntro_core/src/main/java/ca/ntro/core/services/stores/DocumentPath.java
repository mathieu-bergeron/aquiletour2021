package ca.ntro.core.services.stores;

import ca.ntro.core.json.JsonSerializable;

public class DocumentPath implements JsonSerializable {
	
	private String collection;
	private String documentId;


	public DocumentPath(String collection, String firstPathName, String... pathRemainder) {
		this.collection = collection;

		this.documentId = firstPathName;

		for(int i = 0; i < pathRemainder.length; i++) {
			this.documentId += "__" + pathRemainder[i];
		}
	}

	public String getCollection() {
		return collection;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(collection);
		builder.append("/");
		builder.append(documentId);
		
		return builder.toString();
	}

}
