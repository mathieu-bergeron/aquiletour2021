package ca.aquiletour.core.pages.course.teacher.handlers;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CourseViewModelTeacher extends CourseViewModel<CourseModelTeacher, CourseViewTeacher> {
	
	private boolean initialized = false;

	@Override
	protected void handle(CourseModelTeacher model, CourseViewTeacher view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);

		String groupId = message.getGroupId();
		
		if(!initialized) {
			initialized = true;
			initializeDropdowns(model, view);
		}
		
		view.selectGroup(groupId);
	}

	private void initializeDropdowns(CourseModelTeacher model, CourseViewTeacher view) {
		T.call(this);
		
		initializeSemesterDropdown(model, view);
		initializeGroupDropdown(model, view);
	}

	private void initializeGroupDropdown(CourseModelTeacher model, CourseViewTeacher view) {
		T.call(this);

		appendToGroupDropdown(Constants.COURSE_STRUCTURE_ID, view);
		appendToGroupDropdown(Constants.ALL_GROUPS_ID, view);
		
		model.getGroups().removeObservers();
		model.getGroups().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				appendToGroupDropdown(item, view);
			}
		});
		
		view.selectGroup(Constants.COURSE_STRUCTURE_ID);
	}

	private void initializeSemesterDropdown(CourseModelTeacher model, CourseViewTeacher view) {
		T.call(this);

		String semesterId = model.getCoursePath().semesterId();
		appendToSemesterDropdown(semesterId, view);
		
		model.getOtherSemesters().removeObservers();
		model.getOtherSemesters().onItemAdded(new ItemAddedListener<String>() {
			@Override
			public void onItemAdded(int index, String item) {
				T.call(this);
				appendToSemesterDropdown(item, view);
			}
		});

		view.selectSemester(semesterId);
	}
	
	private void appendToSemesterDropdown(String semesterId, CourseViewTeacher view) {
		T.call(this);
		
		String href = "?" + Constants.SEMESTER_URL_PARAM + "=" + semesterId;
		String text = semesterId;
		
		view.appendToSemesterDropdown(semesterId, href, text);
	}

	private void appendToGroupDropdown(String groupId, CourseViewTeacher view) {
		T.call(this);
		
		String href = "?" + Constants.GROUP_URL_PARAM + "=" + groupId;
		String text = "Groupe " + groupId;
		
		if(groupId.equals(Constants.COURSE_STRUCTURE_ID)) {

			text = Constants.COURSE_STRUCTURE_TEXT;

		}else if(groupId.equals(Constants.ALL_GROUPS_ID)) {
			
			text = Constants.ALL_GROUPS_TEXT;
		}
		
		view.appendToGroupDropdown(groupId, href, text);
	}
}
