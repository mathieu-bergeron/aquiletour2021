package ca.aquiletour.core.handlers;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.SelectCategoryMessage;
import ca.aquiletour.core.views.ListView;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class ItemListViewModel<M extends   NtroModel, 
                                        V extends   ListView<?>, 
                                        MSG extends SelectCategoryMessage> 
      
       extends ModelViewSubViewMessageHandler<M, V, MSG> {

	private String currentCategoryId = null;

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader, MSG message) {
		T.call(this);

		if(currentCategoryId == null) {
			initializeCategories(model, view);
		}
		
		String categoryId = message.getCategoryId();
		if(categoryId == null) {
			categoryId = Constants.CATEGORY_ID_CURRENT;
		}
		
		if(!categoryId.equals(currentCategoryId)) {
			currentCategoryId = categoryId;
			view.selectCategory(currentCategoryId);
			onCategoryChanges(model, view, subViewLoader, categoryId);
		}
	}
	
	protected abstract void onCategoryChanges(M model, V view, ViewLoader subViewLoader, String currentCategoryId);

	protected abstract void initializeCategories(M model, V view);

	protected void appendToSemesterDropdown(String semesterId, String text, V view) {
		T.call(this);
		
		String href = "?" + Constants.CATEGORY_URL_PARAM + "=" + semesterId;
		
		view.appendToCategoryDropdown(semesterId, href, text);
	}

	protected void updateSemesterDropdown(String semesterId, String text, V view) {
		T.call(this);
		
		String href = "?" + Constants.CATEGORY_URL_PARAM + "=" + semesterId;
		
		view.updateCategory(semesterId, href, text);
	}
}
