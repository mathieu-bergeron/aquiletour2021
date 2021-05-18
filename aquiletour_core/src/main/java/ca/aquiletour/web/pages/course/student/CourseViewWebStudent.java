package ca.aquiletour.web.pages.course.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloneFailed;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloned;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoSubmitted;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.student.views.CourseViewStudent;
import ca.aquiletour.web.pages.course.CourseViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import static ca.ntro.assertions.Factory.that;

public class CourseViewWebStudent extends CourseViewWeb implements CourseViewStudent {

	private HtmlElement gitRepoTaskSubmitUrl;
	private HtmlElement gitRepoTaskCloningRepo;
	private HtmlElement gitRepoTaskRepoCloned;
	private HtmlElement gitRepoTaskCloneFailed;

	private HtmlElement gitProgressionLink;
	private HtmlElement taskCompletedContainer;
	private HtmlElement taskCompletedCheckbox;

	private HtmlElement entryTasksContainer;
	private HtmlElement exitTasksContainer;

	private HtmlElements addTaskIdToForm;
	private HtmlElements addTaskIdToValue;
	private HtmlElements addTaskIdToId;

	private String gitProgressionText;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		gitRepoTaskSubmitUrl = this.getRootElement().find(".git-repo-task-submit-url").get(0);
		gitRepoTaskCloningRepo = this.getRootElement().find(".git-repo-task-cloning-repo").get(0);
		gitRepoTaskRepoCloned = this.getRootElement().find(".git-repo-task-repo-cloned").get(0);
		gitRepoTaskCloneFailed = this.getRootElement().find(".git-repo-task-clone-failed").get(0);

		gitProgressionLink = this.getRootElement().find("#git-progression-link").get(0);
		taskCompletedContainer = this.getRootElement().find("#task-completed-container").get(0);
		taskCompletedCheckbox = this.getRootElement().find("#task-completed-checkbox").get(0);

		entryTasksContainer = this.getRootElement().find("#entry-tasks-container").get(0);
		exitTasksContainer = this.getRootElement().find("#exit-tasks-container").get(0);

		addTaskIdToForm = this.getRootElement().find(".add-task-id-to-form");
		addTaskIdToValue = this.getRootElement().find(".add-task-id-to-value");
		addTaskIdToId = this.getRootElement().find(".add-task-id-to-id");


		MustNot.beNull(gitRepoTaskSubmitUrl);
		MustNot.beNull(gitRepoTaskCloningRepo);
		MustNot.beNull(gitRepoTaskRepoCloned);
		MustNot.beNull(gitRepoTaskCloneFailed);

		MustNot.beNull(gitProgressionLink);
		MustNot.beNull(taskCompletedContainer);
		MustNot.beNull(taskCompletedCheckbox);
		MustNot.beNull(entryTasksContainer);
		MustNot.beNull(exitTasksContainer);

		Ntro.verify(that(addTaskIdToForm.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addTaskIdToId.size() > 0).isTrue());
		
		gitProgressionText = gitProgressionLink.text();

		gitRepoTaskSubmitUrl.hide();
		gitRepoTaskCloningRepo.hide();
		gitRepoTaskRepoCloned.hide();
		gitRepoTaskCloneFailed.hide();
	}

	@Override
	public void identifyCurrentTask(CoursePath coursePath, Task task) {
		T.call(this);

		addTaskIdToForm.appendToAttribute("form", task.getPath().toFileName());
		addTaskIdToValue.appendToAttribute("value", task.getPath().toFileName());
		addTaskIdToId.appendToAttribute("id", task.getPath().toFileName());
		
		gitProgressionLink.setAttribute("href", "/" + Constants.GIT_COMMIT_LIST_URL_SEGMENT 
				                                + coursePath.toUrlPath()
				                                + task.id()
												+ "?" + Constants.USER_URL_PARAM + "=" + Ntro.currentUser().getId()
												+ "&" + Constants.SEMESTER_URL_PARAM + "=" + coursePath.semesterId());

		gitProgressionLink.html(gitProgressionText + "&nbsp;&nbsp;" + coursePath.toUrlPath() + task.id());
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
	public void displayTaskEndTime(AquiletourDate endTime, boolean editable) {
		T.call(this);
		
	}

	@Override
	public void displayCompletionCheckbox(boolean show) {
		T.call(this);
		
		if(show) {

			taskCompletedContainer.show();

		}else {

			taskCompletedContainer.hide();
		}
	}

	@Override
	public void checkCompletion(boolean check) {
		T.call(this);
		
		if(check) {
			
			taskCompletedCheckbox.setAttribute("checked", "true");
			
		}else {

			taskCompletedCheckbox.removeAttribute("checked");
		}
	}

	@Override
	public void enableCompletionCheckbox(boolean enable) {
		T.call(this);

		if(enable) {
			
			taskCompletedCheckbox.removeAttribute("checked");
			
		}else {

			taskCompletedCheckbox.setAttribute("disabled", "true");

		}
		
	}

	@Override
	public void clearEntryTasks() {
		T.call(this);

		entryTasksContainer.deleteChildrenForever();
	}

	@Override
	public void clearExitTasks() {
		T.call(this);

		exitTasksContainer.deleteChildrenForever();
	}

	@Override
	public void appendEntryTask(String groupId, AtomicTask task, AtomicTaskCompletion completion) {
		T.call(this);
		
		if(task instanceof GitRepoTask) {
			
			appendGitRepoEntryTask(groupId, (GitRepoTask) task, completion);

		}else if(task instanceof GitExerciseTask) {
			
		}
	}

	private void appendGitRepoEntryTask(String groupId, GitRepoTask repoTask, AtomicTaskCompletion completion) {
		T.call(this);
		
		if(completion == null) {
			
			HtmlElement formElement = gitRepoTaskSubmitUrl.clone();
			
			identifyGitRepo(groupId, repoTask, formElement);

			entryTasksContainer.appendElement(formElement);
			formElement.show();

		}else if(completion instanceof GitRepoSubmitted){
			
			GitRepoSubmitted gitRepoSubmitted = (GitRepoSubmitted) completion;

			HtmlElement formElement = gitRepoTaskCloningRepo.clone();
			identifyGitRepo(groupId, repoTask, formElement);

			HtmlElements addRepoUrlToValue = formElement.find(".add-repo-url-to-value");
			addRepoUrlToValue.appendToAttribute("value", gitRepoSubmitted.getRepoUrl());

			HtmlElement repoUrl = formElement.find(".repo-url").get(0);
			
			repoUrl.text(gitRepoSubmitted.getRepoUrl());

			entryTasksContainer.appendElement(formElement);
			formElement.show();

		}else if(completion instanceof GitRepoCloned){

		}else if(completion instanceof GitRepoCloneFailed){

		}
	}

	private void identifyGitRepo(String groupId, GitRepoTask repoTask, HtmlElement formElement) {
		T.call(this);

		HtmlElements addStudentIdToValue = formElement.find(".add-student-id-to-value");
		HtmlElements addGroupIdToValue = formElement.find(".add-group-id-to-value");
		HtmlElements addRepoPathToValue = formElement.find(".add-repo-path-to-value");
		HtmlElements addAtomicTaskIdToValue = formElement.find(".add-atomic-task-id-to-value");
		
		addStudentIdToValue.appendToAttribute("value", ((User) Ntro.currentUser()).getRegistrationId());
		addGroupIdToValue.appendToAttribute("value", groupId);
		addRepoPathToValue.appendToAttribute("value", repoTask.getRepoPath().toString());
		addAtomicTaskIdToValue.appendToAttribute("value", repoTask.getId());
	}

	@Override
	public void appendExitTask(String groupId, AtomicTask task, AtomicTaskCompletion completion) {
		T.call(this);
		
	}



}
