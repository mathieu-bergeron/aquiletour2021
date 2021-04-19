package ca.aquiletour.core.pages.group_list.handlers;


import java.util.List;
import java.util.Map;

import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.models.CourseList;
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
	private String currentCourseId = null;

	@Override
	protected void handle(GroupListModel model, GroupListView view, ViewLoader subViewLoader, SelectGroupListSubset message) {
		T.call(this);
		
		if(currentSemesterId == null) {
			observeSemesterCourses(model, view);
		}
		
		if(!message.getSemesterId().equals(currentSemesterId)
				|| !message.getCourseId().equals(currentCourseId)) {
			
			currentSemesterId = message.getSemesterId();
			currentCourseId = message.getCourseId();
			
			changeCurrentSelection(model, view, subViewLoader);
		}

	}

	private void observeSemesterCourses(GroupListModel model, GroupListView view) {
		T.call(this);
		
		model.getSemesterCourses().observe(new MapObserver<CourseList>() {
			
			@Override
			public void onDeleted(Map<String, CourseList> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(Map<String, CourseList> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(Map<String, CourseList> oldValue, Map<String, CourseList> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEntryRemoved(String key, CourseList value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEntryAdded(String key, CourseList value) {
				T.call(this);
				
				view.appendToSemesterDropdown(key);
			}
		});
	}

	private void changeCurrentSelection(GroupListModel model, 
			                           GroupListView view, 
			                           ViewLoader subViewLoader) {

		T.call(this);
		
		
		view.selectSemester(currentSemesterId);
		
		view.clearCourseDropdown();
		for(String courseId : model.getSemesterCourses().getValue().get(currentSemesterId).getValue()) {
			view.appendToCourseDropdown(courseId);
		}
		
		
		/*
		view.selectCourse(currentCourseId);
		view.identifyCurrentSemester(currentSemesterId);
		view.identifyCurrentCourse(currentCourseId);
		
		observeGroups(model, view, subViewLoader);
		*/
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
				T.call(this);

				if(item.getSemesterId().equals(currentSemesterId)
						&& item.getCourseId().equals(currentCourseId)) {

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
