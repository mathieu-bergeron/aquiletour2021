package ca.aquiletour.core.pages.group_list.handlers;


import java.util.List;
import java.util.Map;

import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.models.GroupDescription;
import ca.aquiletour.core.pages.group_list.models.GroupListModel;
import ca.aquiletour.core.pages.group_list.views.GroupDescriptionView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.mvc.ModelViewSubViewMessageHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class GroupListViewModel extends ModelViewSubViewMessageHandler<GroupListModel, GroupListView, SelectGroupListSubset> {
	
	private String currentSemesterId = null;

	@Override
	protected void handle(GroupListModel model, GroupListView view, ViewLoader subViewLoader, SelectGroupListSubset message) {
		T.call(this);

		if(currentSemesterId == null) {
			observeSemesterIdList(model, view);
		}

		if(!message.getSemesterId().equals(currentSemesterId)) {
			currentSemesterId = message.getSemesterId();

			changeCurrentSemester(model, view, subViewLoader);
		}
	}

	private void observeSemesterIdList(GroupListModel model, GroupListView view) {
		T.call(this);

		model.getSemesters().observe(new ListObserver<String>() {
			
			@Override
			public void onItemRemoved(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, String item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);

				view.insertIntoSemesterDropdown(index, item);
			}
			
			@Override
			public void onDeleted(List<String> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<String> oldValue, List<String> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void changeCurrentSemester(GroupListModel model, 
			                           GroupListView view, 
			                           ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentSemesterId);
		view.identifyCurrentSemester(currentSemesterId);
		
		observeGroups(model, view, subViewLoader);
	}

	private void observeGroups(GroupListModel model, 
		                       GroupListView view, 
		                       ViewLoader subViewLoader) {
		T.call(this);

		view.clearItems();

		model.getGroups().removeObservers();
		model.getGroups().observe(new ListObserver<GroupDescription>() {

			@Override
			public void onValueChanged(List<GroupDescription> oldValue, List<GroupDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<GroupDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<GroupDescription> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, GroupDescription item) {

				if(item.getSemesterId().equals(currentSemesterId)) {
					
					GroupDescriptionView subView = (GroupDescriptionView) subViewLoader.createView();
					subView.displayGroupDescription(item);

					view.appendItem(subView);
					
					observeGroupStudents(item, subView);
				}
			}


			@Override
			public void onItemUpdated(int index, GroupDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, GroupDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void observeGroupStudents(GroupDescription group, GroupDescriptionView subView) {
		T.call(this);
		
		subView.clearStudents();
		
		group.getStudents().removeObservers();
		group.getStudents().observe(new ListObserver<StudentDescription>() {

			@Override
			public void onValueChanged(List<StudentDescription> oldValue, List<StudentDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<StudentDescription> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<StudentDescription> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, StudentDescription item) {
				T.call(this);
				
				subView.appendStudent(item);
			}

			@Override
			public void onItemUpdated(int index, StudentDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, StudentDescription item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
