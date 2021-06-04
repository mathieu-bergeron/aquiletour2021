package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.default_task.DefaultAtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloneFailed;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloned;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoSubmitted;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.status.StatusBlocked;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class CourseViewWebStudent extends CourseViewWeb implements CourseViewStudent {

	private HtmlElement atomicTaskContainerDefault;
	private HtmlElement atomicTaskLockDefault;
	private HtmlElement atomicTaskCheckboxContainerDefault;

	private HtmlElement subTaskLock;

	private HtmlElement gitRepoTaskSubmitUrl;
	private HtmlElement gitRepoTaskCloningRepo;
	private HtmlElement gitRepoTaskCloned;
	private HtmlElement gitRepoTaskCloneFailed;

	private HtmlElement gitProgressionContainer;
	private HtmlElement gitProgressionLink;

	private HtmlElement parentTaskLink;

	private HtmlElement endtimeContainer;
	private HtmlElement endtimeLabel;

	private HtmlElement toCompleteFirstContainer;
	private HtmlElement toCompleteFirstLink;

	private HtmlElement doneContainer;
	private HtmlElement doneEntryTasksContainer;
	private HtmlElement doneExitTasksContainer;

	private HtmlElement todoContainer;
	private HtmlElement todoEntryTasksContainer;
	private HtmlElement todoExitTasksContainer;


	private HtmlElement doneTasksContainer;
	private HtmlElement todoTasksContainer;

	private HtmlElements addTaskIdToForm;
	private HtmlElements addTaskIdToValue;
	private HtmlElements addTaskIdToId;
	
	private String gitProgressionHref = "";

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		atomicTaskContainerDefault = this.getRootElement().find("#atomic-task-container-default").get(0);
		atomicTaskLockDefault = this.getRootElement().find("#atomic-task-lock-default").get(0);
		atomicTaskCheckboxContainerDefault = this.getRootElement().find("#atomic-task-checkbox-container-default").get(0);

		subTaskLock = this.getRootElement().find("#subtask-lock").get(0);

		gitRepoTaskSubmitUrl = this.getRootElement().find(".git-repo-task-submit-url").get(0);
		gitRepoTaskCloningRepo = this.getRootElement().find(".git-repo-task-cloning-repo").get(0);
		gitRepoTaskCloned = this.getRootElement().find(".git-repo-task-repo-cloned").get(0);
		gitRepoTaskCloneFailed = this.getRootElement().find(".git-repo-task-clone-failed").get(0);

		parentTaskLink = this.getRootElement().find("#parent-task-link").get(0);

		gitProgressionContainer = this.getRootElement().find("#git-progression-container").get(0);
		gitProgressionLink = this.getRootElement().find("#git-progression-link").get(0);

		endtimeContainer = this.getRootElement().find("#endtime-container").get(0);
		endtimeLabel = this.getRootElement().find("#endtime-label").get(0);

		toCompleteFirstContainer = this.getRootElement().find("#to-complete-first-container").get(0);
		toCompleteFirstLink = this.getRootElement().find("#to-complete-first-link").get(0);

		doneContainer = this.getRootElement().find("#done-container").get(0);
		doneEntryTasksContainer = this.getRootElement().find("#done-entry-tasks-container").get(0);
		doneExitTasksContainer = this.getRootElement().find("#done-exit-tasks-container").get(0);

		todoContainer = this.getRootElement().find("#todo-container").get(0);
		todoEntryTasksContainer = this.getRootElement().find("#todo-entry-tasks-container").get(0);
		todoExitTasksContainer = this.getRootElement().find("#todo-exit-tasks-container").get(0);

		doneTasksContainer = this.getRootElement().find("#done-tasks-container").get(0);
		todoTasksContainer = this.getRootElement().find("#todo-tasks-container").get(0);

		addTaskIdToForm = this.getRootElement().find(".add-task-id-to-form");
		addTaskIdToValue = this.getRootElement().find(".add-task-id-to-value");
		addTaskIdToId = this.getRootElement().find(".add-task-id-to-id");

		MustNot.beNull(atomicTaskLockDefault);
		MustNot.beNull(atomicTaskContainerDefault);
		MustNot.beNull(atomicTaskCheckboxContainerDefault);

		MustNot.beNull(subTaskLock);

		MustNot.beNull(gitRepoTaskSubmitUrl);
		MustNot.beNull(gitRepoTaskCloningRepo);
		MustNot.beNull(gitRepoTaskCloned);
		MustNot.beNull(gitRepoTaskCloneFailed);

		MustNot.beNull(gitProgressionContainer);
		MustNot.beNull(gitProgressionLink);

		MustNot.beNull(toCompleteFirstContainer);
		MustNot.beNull(toCompleteFirstLink);

		MustNot.beNull(endtimeLabel);

		MustNot.beNull(doneContainer);
		MustNot.beNull(doneEntryTasksContainer);
		MustNot.beNull(doneExitTasksContainer);

		MustNot.beNull(todoContainer);
		MustNot.beNull(todoEntryTasksContainer);
		MustNot.beNull(todoExitTasksContainer);

		MustNot.beNull(doneTasksContainer);
		MustNot.beNull(todoTasksContainer);

		atomicTaskContainerDefault.hide();

		doneContainer.hide();
		doneEntryTasksContainer.hide();
		doneExitTasksContainer.hide();

		todoContainer.hide();
		todoEntryTasksContainer.hide();
		todoExitTasksContainer.hide();

		subTaskLock.hide();
		gitRepoTaskSubmitUrl.hide();
		gitRepoTaskCloningRepo.hide();
		gitRepoTaskCloned.hide();
		gitRepoTaskCloneFailed.hide();
		
		toCompleteFirstContainer.hide();
	}

	@Override
	public void identifyCurrentTask(CoursePath coursePath, Task task) {
		T.call(this);
		
		if(!task.isRootTask()){
			parentTaskLink.setAttribute("href", "/" + Constants.COURSE_URL_SEGMENT + coursePath.toUrlPath() + task.parent().getPath().toString());
		}else {
			parentTaskLink.hide();
		}

		addTaskIdToForm.appendToAttribute("form", task.getPath().toFileName());
		addTaskIdToValue.appendToAttribute("value", task.getPath().toFileName());
		addTaskIdToId.appendToAttribute("id", task.getPath().toFileName());
		
		gitProgressionHref = "/" + Constants.GIT_COMMIT_LIST_URL_SEGMENT 
				                                + coursePath.toUrlPath()
				                                + task.id()
												+ "?" + Constants.USER_URL_PARAM + "=" + Ntro.currentUser().getId()
												+ "&" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId();
	}

	@Override
	public void displayGitRepoForm(boolean show) {
		T.call(this);
		
		if(show) {
			
			gitRepoTaskSubmitUrl.show();

		}else {

			gitRepoTaskSubmitUrl.hide();
		}
	}

	@Override
	public void displayTaskEndTime(boolean shouldDisplay) {
		T.call(this);

		if(shouldDisplay) {
			endtimeContainer.show();
		
		}else {
			endtimeContainer.hide();
		}
	}

	@Override
	public void updateTaskEndTime(AquiletourDate endTime, boolean editable) {
		T.call(this);
		
		super.updateTaskEndTime(endTime, editable);
		
		uneditableEndTime().removeClass("bg-danger");
		uneditableEndTime().removeClass("bg-success");
		uneditableEndTime().addClass("bg-warning");
		
		endtimeLabel.text("À terminer avant");
	}

	@Override
	public void updateTaskDoneTime(AquiletourDate doneTime) {
		T.call(this);

		super.updateTaskEndTime(doneTime, false);
		
		uneditableEndTime().removeClass("bg-danger");
		uneditableEndTime().removeClass("bg-warning");
		uneditableEndTime().addClass("bg-success");
		
		endtimeLabel.text("Terminé le");
		
		displayTaskEndTime(true);
	}

	@Override
	public void updateTaskLateTime(AquiletourDate lateTime) {
		T.call(this);
		
		super.updateTaskEndTime(lateTime, false);
		
		uneditableEndTime().removeClass("bg-success");
		uneditableEndTime().removeClass("bg-warning");
		uneditableEndTime().addClass("bg-danger");
		
		endtimeLabel.text("En retard depuis");

		displayTaskEndTime(true);
	}

	@Override
	public void clearEntryTasks() {
		T.call(this);
		
		doneEntryTasksContainer.deleteChildrenForever();
		todoEntryTasksContainer.deleteChildrenForever();
	}

	@Override
	public void clearExitTasks() {
		T.call(this);
		
		doneExitTasksContainer.deleteChildrenForever();
		todoExitTasksContainer.deleteChildrenForever();
	}

	@Override
	public void appendEntryTask(String groupId, AtomicTask task, AtomicTaskCompletion completion) {
		T.call(this);
		
		if(task instanceof GitRepoTask) {
			
			appendGitRepoEntryTask(groupId, (GitRepoTask) task);

		}else if(task instanceof GitExerciseTask) {
			
		}
	}

	private void addCompletionToGitRepoEntryTask(String groupId, GitRepoTask gitRepoTask, AtomicTaskCompletion completion) {
		T.call(this);
		
		if(completion instanceof GitRepoSubmitted){
			
			GitRepoSubmitted gitRepoSubmitted = (GitRepoSubmitted) completion;

			HtmlElement formElement = gitRepoTaskCloningRepo.clone();
			identifyGitRepo(groupId, gitRepoTask, formElement);

			HtmlElements addRepoUrlToValue = formElement.find(".add-repo-url-to-value");
			addRepoUrlToValue.appendToAttribute("value", gitRepoSubmitted.getRepoUrl());

			HtmlElement repoUrl = formElement.find(".repo-url").get(0);
			repoUrl.text(gitRepoSubmitted.getRepoUrl());

			doneTasksContainer.appendElement(formElement);
			formElement.show();

		}else if(completion instanceof GitRepoCloned){

			GitRepoCloned gitRepoCloned = (GitRepoCloned) completion;

			HtmlElement formElement = gitRepoTaskCloned.clone();

			HtmlElement repoUrl = formElement.find(".repo-url").get(0);
			repoUrl.text(gitRepoCloned.getRepoUrl());

			doneTasksContainer.appendElement(formElement);
			formElement.show();

		}else if(completion instanceof GitRepoCloneFailed){

		}
	}

	private String atomicTaskId(AtomicTask task) {
		T.call(this);
		
		return "atomic-task-" + task.getId();
	}

	private void appendGitRepoEntryTask(String groupId, GitRepoTask repoTask) {
		T.call(this);
		
		HtmlElement formElement = gitRepoTaskSubmitUrl.clone();
		
		identifyGitRepo(groupId, repoTask, formElement);

		doneTasksContainer.appendElement(formElement);
		formElement.show();
	}

	private void identifyAtomicTask(AtomicTask task, HtmlElement container) {
		T.call(this);
		
		HtmlElements addAtomicTaskPathToValue = container.find(".add-atomic-task-id-to-value");
		addAtomicTaskPathToValue.appendToAttribute("value", task.getId());
	}

	private void identifyGitRepo(String groupId, GitRepoTask repoTask, HtmlElement container) {
		T.call(this);
		
		identifyAtomicTask(repoTask, container);

		container.addClass(atomicTaskId(repoTask));

		HtmlElements addStudentIdToValue = container.find(".add-student-id-to-value");
		HtmlElements addGroupIdToValue = container.find(".add-group-id-to-value");
		HtmlElements addRepoPathToValue = container.find(".add-repo-path-to-value");
		
		addStudentIdToValue.appendToAttribute("value", ((User) Ntro.currentUser()).getId());
		addGroupIdToValue.appendToAttribute("value", groupId);
		addRepoPathToValue.appendToAttribute("value", repoTask.getRepoPath().toString());
	}

	@Override
	public void appendExitTask(String groupId, AtomicTask task, AtomicTaskCompletion completion) {
		T.call(this);
		
		gitProgressionHref += "&" + Constants.GROUP_URL_PARAM + "=" + groupId;
		gitProgressionLink.setAttribute("href", gitProgressionHref);
		
		if(task instanceof DefaultAtomicTask) {
			
			appendDefaultExitTask(groupId, task, completion);
			
		}
		
	}

	private void appendDefaultExitTask(String groupId, AtomicTask task, AtomicTaskCompletion completion) {
		T.call(this);
		
		HtmlElement atomicTaskContainer = atomicTaskContainerDefault.clone();
		identifyAtomicTask(task, atomicTaskContainer);
		atomicTaskContainer.show();
		
		HtmlElement atomicTaskCheckbox = atomicTaskContainer.find("#atomic-task-checkbox-default").get(0);
		
		if(completion == null) {

			todoContainer.show();
			todoExitTasksContainer.show();
			todoExitTasksContainer.appendElement(atomicTaskContainer);

			atomicTaskCheckbox.removeAttribute("checked");
			atomicTaskCheckbox.removeAttribute("disabled");

		}else {
			
			doneContainer.show();
			doneExitTasksContainer.show();
			doneExitTasksContainer.appendElement(atomicTaskContainer);

			atomicTaskCheckbox.setAttribute("checked", "true");
			atomicTaskCheckbox.setAttribute("disabled", "true");
		}
	}


	@Override
	public void updateAtomicTaskCompletion(String groupId, AtomicTask atomicTask, AtomicTaskCompletion completion) {
		T.call(this);
		
		removeEntryTask(atomicTask);

		if(atomicTask instanceof GitRepoTask) {
			addCompletionToGitRepoEntryTask(groupId, (GitRepoTask) atomicTask, completion);
		}
	}

	@Override
	public void removeEntryTask(AtomicTask atomicTask) {
		T.call(this);
		
		getRootElement().find("." + atomicTaskId(atomicTask)).forEach(e -> {
			e.deleteForever();
		});
	}

	@Override
	public void displayDoneTasks(boolean shouldDisplay) {
		T.call(this);

		doneContainer.display(shouldDisplay);
	}

	@Override
	public void displayTodoTasks(boolean shouldDisplay) {
		T.call(this);
		
		todoContainer.display(shouldDisplay);
	}

	@Override
	public void displayToCompleteFirst(boolean shouldDisplay) {
		T.call(this);
		
		if(shouldDisplay) {
			
			toCompleteFirstContainer.show();
			
		}else {

			toCompleteFirstContainer.hide();
		}
	}

	@Override
	public void updateToCompleteFirst(StatusBlocked status) {
		T.call(this);
		
		toCompleteFirstLink.text(status.text());
		if(status.href().isEmpty()) {
			toCompleteFirstLink.removeAttribute("href");
		}else {
			toCompleteFirstLink.setAttribute("href", status.href());
		}
	}

	@Override
	public void enableSubTasks(boolean shouldEnable) {
		T.call(this);
		
		super.enableSubTasks(shouldEnable);
		subTaskLock.setVisibility(shouldEnable);
	}


}
