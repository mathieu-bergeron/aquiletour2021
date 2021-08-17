package ca.aquiletour.core.messages;

import ca.ntro.messages.NtroMessage;

public class SelectCategoryMessage extends NtroMessage {
	
	private String categoryId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
