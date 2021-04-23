package ca.aquiletour.core.pages.group_list.handlers;


import java.util.List;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.messages.SelectGroupListSubset;
import ca.aquiletour.core.pages.group_list.models.ObservableCourseList;
import ca.aquiletour.core.pages.group_list.models.GroupItem;
import ca.aquiletour.core.pages.group_list.models.GroupListModel;
import ca.aquiletour.core.pages.group_list.views.GroupView;
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
		
		model.getSemesterCourses().observe(new MapObserver<ObservableCourseList>() {
			
			@Override
			public void onDeleted(Map<String, ObservableCourseList> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(Map<String, ObservableCourseList> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(Map<String, ObservableCourseList> oldValue, Map<String, ObservableCourseList> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEntryRemoved(String key, ObservableCourseList value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEntryAdded(String key, ObservableCourseList value) {
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
		
		ObservableCourseList courseList = model.getSemesterCourses().getValue().get(currentSemesterId);
		
		if(courseList != null) {
			for(String courseId : courseList.getValue()) {
				view.appendToCourseDropdown(courseId, "/" + Constants.GROUP_LIST_URL_SEGMENT + "?" + Constants.COURSE_URL_PARAM + "=" + courseId);
			}
		}
		
		view.selectCourse(currentCourseId);
		view.identifyCurrentSemester(currentSemesterId);
		view.identifyCurrentCourse(currentCourseId);
		
		observeGroups(model, view, subViewLoader);
	}

	private void observeGroups(GroupListModel model, 
		                       GroupListView view, 
	                       ViewLoader subViewLoader) {
		T.call(this);
		
		view.clearItems();

		model.getGroups().removeObservers();
		model.getGroups().observe(new ListObserver<GroupItem>() {

			@Override
			public void onValueChanged(List<GroupItem> oldValue, List<GroupItem> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<GroupItem> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<GroupItem> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, GroupItem item) {
				T.call(this);

				if(item.getSemesterId().equals(currentSemesterId)
						&& item.getCourseId().equals(currentCourseId)) {

					GroupView subView = (GroupView) subViewLoader.createView();
					subView.displayGroupDescription(item);

					view.appendItem(subView);
					
					observeGroupStudents(item, subView);
				}
			}

			@Override
			public void onItemUpdated(int index, GroupItem item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, GroupItem item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void observeGroupStudents(GroupItem group, GroupView subView) {
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
