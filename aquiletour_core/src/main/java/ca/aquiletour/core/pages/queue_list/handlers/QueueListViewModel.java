package ca.aquiletour.core.pages.queue_list.handlers;


import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.queue_list.models.QueueListItem;
import ca.aquiletour.core.pages.queue_list.models.QueueListModel;
import ca.aquiletour.core.pages.queue_list.views.QueueListItemView;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelObserver;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class QueueListViewModel extends ModelViewSubViewHandler<QueueListModel, QueueListView> {
	
	private NtroModel currentModel = null;
	private ModelObserver currentObserver = null;

	@Override
	protected void handle(QueueListModel model, QueueListView view, ViewLoader subViewLoader) {
		
		ModelStoreSync modelStore = new ModelStoreSync(Ntro.modelStore());
		
		if(currentModel != null) {
			modelStore.removeObserver(currentModel, currentObserver);
		}

		currentModel = model;
		
		currentObserver = modelStore.observeModel(currentModel, new ModelObserver() {
			@Override
			public void onModelUpdate(NtroModel updatedModel) {
				T.call(this);
				
				QueueListModel queueListModel = (QueueListModel) updatedModel;
				
				List<String> currentSubViewIds = new ArrayList<>();
				
				queueListModel.forEachItemInOrder(queueListItem -> {
					
					String subViewId = queueListItem.subViewId();
					
					currentSubViewIds.add(subViewId);

					displayQueueListItem(view, subViewLoader, queueListItem, subViewId);
				});

				view.displayOrHideSubView(QueueListItemView.class , subViewId -> {
					return currentSubViewIds.contains(subViewId);
				});
			}

		});
	}

	private void displayQueueListItem(QueueListView view, 
			                          ViewLoader subViewLoader, 
			                          QueueListItem queueListItem, 
			                          String subViewId) {
		T.call(this);

		QueueListItemView subView = (QueueListItemView) view.findSubView(QueueListItemView.class, subViewId);
		
		if(subView != null) {

			subView.initializeView(AquiletourMain.createNtroContext());
			subView.displayQueueListItem(queueListItem);

		} else {
			
			subView = (QueueListItemView) subViewLoader.createView();
			subView.displayQueueListItem(queueListItem);
			view.appendQueueItem(subViewId, subView);
		}
	}
}
