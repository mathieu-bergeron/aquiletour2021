package ca.aquiletour.core.pages.course.teacher.handlers;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.student.StudentCompletionsByTaskId;
import ca.aquiletour.core.models.courses.teacher.CourseTeacher;
import ca.aquiletour.core.models.courses.teacher.GroupDescription;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.handlers.CourseViewModel;
import ca.aquiletour.core.pages.course.messages.ShowTaskMessage;
import ca.aquiletour.core.pages.course.teacher.views.CourseViewTeacher;
import ca.ntro.core.models.listeners.EntryAddedListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class CourseViewModelTeacher extends CourseViewModel<CourseTeacher, CourseViewTeacher> {
	
	@Override
	protected boolean isEditable() {
		T.call(this);

		User user = (User) Ntro.currentUser();
		boolean isOwner = currentCoursePath().teacherId().equals(user.getRegistrationId());
		
		boolean isStructure = currentGroupId().equals(Constants.COURSE_STRUCTURE_ID);
		
		return isOwner && isStructure && user.actsAsTeacher();
	}

	@Override
	protected void handle(CourseTeacher model, CourseViewTeacher view, ViewLoader subViewLoader, ShowTaskMessage message) {
		T.call(this);
		super.handle(model, view, subViewLoader, message);
		
		initializeDropdowns(model, view);

		view.selectGroup(currentGroupId());
		
		view.showUneditableComponents(!isEditable());
		view.showEditableComponents(isEditable());
	}

	protected void observeCompletions(CourseTeacher model, CourseViewTeacher view) {
		T.call(this);

		model.getCompletions().removeObservers();
		model.getCompletions().onEntryAdded(new EntryAddedListener<StudentCompletionsByTaskId>() {
			@Override
			public void onEntryAdded(String studentId, StudentCompletionsByTaskId studentCompletionsByTaskId) {
				T.call(this);

				displayStudentCompletion(studentId, view);
			}
		});
	}

	private void initializeDropdowns(CourseTeacher model, CourseViewTeacher view) {
		T.call(this);
		
		initializeSemesterDropdown(model, view);
		initializeGroupDropdown(model, view);
	}

	private void initializeGroupDropdown(CourseTeacher model, CourseViewTeacher view) {
		T.call(this);

		appendToGroupDropdown(Constants.COURSE_STRUCTURE_ID, view);
		appendToGroupDropdown(Constants.ALL_GROUPS_ID, view);
		
		model.getGroups().removeObservers();
		model.getGroups().onItemAdded(new ItemAddedListener<GroupDescription>() {
			@Override
			public void onItemAdded(int index, GroupDescription item) {
				T.call(this);
				appendToGroupDropdown(item.getGroupId(), view);
			}
		});

		view.selectGroup(Constants.COURSE_STRUCTURE_ID);
	}

	private void initializeSemesterDropdown(CourseTeacher model, CourseViewTeacher view) {
		T.call(this);

		String semesterId = model.getCoursePath().semesterId();
		appendToSemesterDropdown(semesterId, view);
		
		model.getSiblingSemesters().removeObservers();
		model.getSiblingSemesters().onItemAdded(new ItemAddedListener<String>() {
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

	@Override
	protected void displayStudentCompletion(String studentId, CourseViewTeacher view) {
		T.call(this);
		
		view.appendCompletion(studentId);
	}

}
