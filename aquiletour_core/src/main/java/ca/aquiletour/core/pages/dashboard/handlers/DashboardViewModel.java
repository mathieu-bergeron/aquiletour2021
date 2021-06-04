package ca.aquiletour.core.pages.dashboard.handlers;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.views.DashboardItemView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public abstract class DashboardViewModel<M extends DashboardModel<CT>, 
                                        CT extends CurrentTask,
                                        V extends DashboardView> 

       extends ModelViewSubViewHandler<M, V> {

	@Override
	protected void handle(M model, V view, ViewLoader subViewLoader) {
		T.call(this);
		
		observeDashboardItems(model, view, subViewLoader);
	}

	@SuppressWarnings("unchecked")
	private void observeDashboardItems(M model, V view, ViewLoader subViewLoader) {
		T.call(this);

		model.getDashboardItems().removeObservers();
		model.getDashboardItems().onItemAdded(new ItemAddedListener<DashboardItem<CT>>() {
			@Override
			public void onItemAdded(int index, DashboardItem<CT> item) {
				T.call(this);
				
				DashboardItemView<CT> subView = (DashboardItemView<CT>) subViewLoader.createView();
				
				view.appendDashboardItem(subView);
				
				observeDashboardItem(item, subView);
			}
		});
	}
	
	protected void observeDashboardItem(DashboardItem<CT> dashboardItem, DashboardItemView<CT> subView) {
		T.call(this);
		
		subView.identifyCourse(dashboardItem.getCoursePath());


		observeCourseTitle(dashboardItem, subView);

		observeCurrentTasks(dashboardItem, dashboardItem.getCoursePath(), subView);
	}

	private void observeCurrentTasks(DashboardItem<CT> dashboardItem, 
			                         CoursePath coursePath, 
			                         DashboardItemView<CT> subView) {
		T.call(this);

		dashboardItem.getCurrentTasks().removeObservers();
		dashboardItem.getCurrentTasks().onItemAdded(new ItemAddedListener<CT>() {
			@Override
			public void onItemAdded(int index, CT currentTask) {
				T.call(this);
				
				subView.insertTask(index, coursePath, currentTask);
				observeCurrentTask(subView, index, currentTask);
			}
		});
	}

	private void observeCurrentTask(DashboardItemView<CT> subView, int index, CT currentTask) {
		T.call(this);
		
		currentTask.getTaskTitle().removeObservers();
		currentTask.getTaskTitle().observe(new ValueObserver<String>() {
			@Override
			public void onValue(String value) {
				T.call(this);
				subView.updateTaskTitle(index, value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				subView.updateTaskTitle(index, value);
			}
		});
	}




	private void observeCourseTitle(DashboardItem<CT> dashboardItem, DashboardItemView<CT> subView) {
		T.call(this);

		dashboardItem.getCourseTitle().removeObservers();
		dashboardItem.getCourseTitle().observe(new ValueObserver<String>() {
			
			@Override
			public void onDeleted(String lastValue) {
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);
				subView.updateCourseTitle(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);
				subView.updateCourseTitle(value);
			}
		});
	}
}
