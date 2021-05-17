// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.aquiletour.core;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.messages.git.DeRegisterExercise;
import ca.aquiletour.core.messages.git.DeRegisterRepo;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.OnCloneFailed;
import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.messages.git.RegisterExercise;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.messages.user.ToggleStudentModeMessage;
import ca.aquiletour.core.messages.user.UpdateUserInfoMessage;
import ca.aquiletour.core.messages.user.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.user.UserLogsOutMessage;
import ca.aquiletour.core.messages.user.UserSendsLoginCodeMessage;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.atomic_tasks.GitExerciseTask;
import ca.aquiletour.core.models.courses.atomic_tasks.GitRepoTask;
import ca.aquiletour.core.models.courses.atomic_tasks.ShortTextTask;
import ca.aquiletour.core.models.courses.base.AtomicTask;
import ca.aquiletour.core.models.courses.base.CourseModelBase;
import ca.aquiletour.core.models.courses.base.ObservableCourseDate;
import ca.aquiletour.core.models.courses.base.ObservableTaskIdList;
import ca.aquiletour.core.models.courses.base.ObservableTaskMap;
import ca.aquiletour.core.models.courses.base.AtomicTasks;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskRelation;
import ca.aquiletour.core.models.courses.group.Group;
import ca.aquiletour.core.models.courses.group.ObservableGroupMap;
import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.models.courses.group_description.GroupDescriptionLists;
import ca.aquiletour.core.models.courses.group_description.StudentIdList;
import ca.aquiletour.core.models.courses.model.CompletionByStudentId;
import ca.aquiletour.core.models.courses.model.CompletionsByTaskId;
import ca.aquiletour.core.models.courses.model.Course;
import ca.aquiletour.core.models.courses.model.CourseIdList;
import ca.aquiletour.core.models.courses.model.EndTimeByTaskId;
import ca.aquiletour.core.models.courses.model.GroupDescription;
import ca.aquiletour.core.models.courses.model.TaskDatesByGroupId;
import ca.aquiletour.core.models.courses.student.CompletionByTaskId;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.task_completions.TaskCompletion;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.aquiletour.core.models.dates.CourseDateSemesterDay;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.StoredAquiletourDate;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItems;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.models.user_list.UserIdMap;
import ca.aquiletour.core.models.user_list.UserList;
import ca.aquiletour.core.models.user_registration.RegistrationId;
import ca.aquiletour.core.models.user_registration.UserId;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course.messages.TaskCompletedMessage;
import ca.aquiletour.core.pages.course.messages.UpdateTaskInfoMessage;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.ObservableCourseDescriptionList;
import ca.aquiletour.core.pages.course_list.models.ObservableGroupIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableTaskDescriptions;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.student.CourseListStudent;
import ca.aquiletour.core.pages.course_list.student.messages.SelectCourseListSubsetStudent;
import ca.aquiletour.core.pages.course_list.student.messages.ShowCourseListMessageStudent;
import ca.aquiletour.core.pages.course_list.teacher.CourseListTeacher;
import ca.aquiletour.core.pages.course_list.teacher.messages.SelectCourseListSubsetTeacher;
import ca.aquiletour.core.pages.course_list.teacher.messages.ShowCourseListMessageTeacher;
import ca.aquiletour.core.pages.dashboard.models.CurrentTasks;
import ca.aquiletour.core.pages.dashboard.models.DashboardItems;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardItemStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardStudent;
import ca.aquiletour.core.pages.dashboard.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardItemTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardTeacher;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.late_students.LateStudentsModel;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesModel;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.git.values.CommitFile;
import ca.aquiletour.core.pages.git.values.ObservableCommitList;
import ca.aquiletour.core.pages.group_list.models.ObservableCourseList;
import ca.aquiletour.core.pages.group_list.models.GroupItem;
import ca.aquiletour.core.pages.group_list.models.GroupList;
import ca.aquiletour.core.pages.group_list.models.ObservableGroupList;
import ca.aquiletour.core.pages.group_list.models.ObservableStudentList;
import ca.aquiletour.core.pages.group_list.models.SemesterCourses;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListModel;
import ca.aquiletour.core.pages.open_queue_list.values.ObservableQueueList;
import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentDurations;
import ca.aquiletour.core.pages.queue.messages.ModifyAppointmentTimes;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.ObservableTime;
import ca.aquiletour.core.pages.queue.models.ObservableAppointmentList;
import ca.aquiletour.core.pages.queue.models.ObservableTags;
import ca.aquiletour.core.pages.queue.models.Queue;
import ca.aquiletour.core.pages.git.values.ObservableStudentSummaryList;
import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.semester_list.models.SemesterList;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.aquiletour.core.pages.semester_list.models.ObservableSemesterWeekList;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListAdmin;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddScheduleItemMessage;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.ObservableCourseGroupList;
import ca.aquiletour.core.pages.semester_list.models.ObservableSemesterList;
import ca.ntro.core.mvc.ControllerFactory;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.messages.MessageHandler;
import ca.ntro.messages.ntro_messages.NtroSetUserMessage;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroInitializationTask;
import ca.ntro.users.NtroSession;

public abstract class AquiletourMain extends NtroTaskSync {
	
	public static String currentSemester() {
		T.call(AquiletourMain.class);
		
		SessionData sessionData = (SessionData) Ntro.currentSession().getSessionData();
		
		return sessionData.getCurrentSemester();
	}

	protected abstract void registerViewLoaders();

	@Override
	protected void runTask() {
		T.call(this);
		
		// FIXME
		Constants.LANG = getPreviousTask(NtroInitializationTask.class, "initializationTask").getOption("lang");
		Constants.LANG = "fr";

		registerViewLoaders();

		@SuppressWarnings("unchecked")
		NtroContext<User, SessionData> context = (NtroContext<User, SessionData>) Ntro.context();
		
		// XXX: "/**" means: execute every subController
		// XXX: "/*/*/*" means: execute every subController down 3 levels
		// XXX: "/settings/*" means: execute the settings controller, then subController of settings
		RootController rootController = ControllerFactory.createRootController(RootController.class, "*", getWindow(), context);  

		rootController.execute();
		
		Ntro.backendService().handleMessageFromBackend(NtroSetUserMessage.class, new MessageHandler<NtroSetUserMessage>() {
			@Override
			public void handle(NtroSetUserMessage message) {
				Ntro.currentSession().setUser(message.getUser());
				Ntro.sessionService().registerCurrentSession(Ntro.currentSession()); // XXX: saves session

				rootController.changeUser(message.getUser());
			}
		});
		
		Ntro.messages().registerHandler(ToggleStudentModeMessage.class, new MessageHandler<ToggleStudentModeMessage>() {
			@Override
			public void handle(ToggleStudentModeMessage message) {
				T.call(this);
				
				NtroSession session = Ntro.currentSession();
				User user = (User) session.getUser();

				if(user instanceof Teacher) {

					Teacher teacher = (Teacher) user;
					teacher.toggleStudentMode();
					
					session.setUser(teacher);
					Ntro.sessionService().registerCurrentSession(session); // XXX: saves session

					rootController.changeUser(teacher);
				}
			}
		});
	}
	
	public static void registerSerializableClasses() {
		T.call(AquiletourMain.class);

		Ntro.registerSerializableClass(DashboardTeacher.class);
		Ntro.registerSerializableClass(DashboardStudent.class);
		Ntro.registerSerializableClass(DashboardItems.class);

		Ntro.registerSerializableClass(Queue.class);
		Ntro.registerSerializableClass(ObservableAppointmentList.class);
		Ntro.registerSerializableClass(Appointment.class);

		Ntro.registerSerializableClass(OpenQueueListModel.class);
		Ntro.registerSerializableClass(ObservableQueueList.class);
		Ntro.registerSerializableClass(OpenQueue.class);
		Ntro.registerSerializableClass(CommitListModel.class);
		Ntro.registerSerializableClass(ObservableCommitList.class);
		Ntro.registerSerializableClass(Commit.class);
		Ntro.registerSerializableClass(CommitFile.class);
		
		Ntro.registerSerializableClass(LateStudentsModel.class);
		
		Ntro.registerSerializableClass(StudentSummariesModel.class);
		Ntro.registerSerializableClass(StudentSummary.class);	
		Ntro.registerSerializableClass(ObservableStudentSummaryList.class);	

		Ntro.registerSerializableClass(CourseModelBase.class);
		Ntro.registerSerializableClass(CoursePath.class);
		Ntro.registerSerializableClass(ObservableTaskMap.class);
		Ntro.registerSerializableClass(ObservableTaskIdList.class);
		Ntro.registerSerializableClass(Task.class);
		Ntro.registerSerializableClass(TaskRelation.class);

		Ntro.registerSerializableClass(Group.class);
		Ntro.registerSerializableClass(StudentDescription.class);
		Ntro.registerSerializableClass(ObservableGroupMap.class);

		Ntro.registerSerializableClass(SemesterModelAdmin.class);
		Ntro.registerSerializableClass(SemesterModelTeacher.class);
		Ntro.registerSerializableClass(SemesterList.class);
		Ntro.registerSerializableClass(SemesterListTeacher.class);
		Ntro.registerSerializableClass(SemesterListAdmin.class);
		Ntro.registerSerializableClass(ObservableSemesterList.class);
		Ntro.registerSerializableClass(ObservableSemesterWeekList.class);
		Ntro.registerSerializableClass(CalendarWeek.class);

		Ntro.registerSerializableClass(SemesterSchedule.class);
		Ntro.registerSerializableClass(TeacherSchedule.class);

		Ntro.registerSerializableClass(Course.class);
		Ntro.registerSerializableClass(CourseModelStudent.class);
		Ntro.registerSerializableClass(EndTimeByTaskId.class);
		Ntro.registerSerializableClass(TaskDatesByGroupId.class);
		Ntro.registerSerializableClass(TaskCompletion.class);
		Ntro.registerSerializableClass(CompletionByTaskId.class);

		Ntro.registerSerializableClass(CourseListItem.class);
		Ntro.registerSerializableClass(CourseListTeacher.class);
		Ntro.registerSerializableClass(CourseListStudent.class);
		Ntro.registerSerializableClass(ObservableCourseDescriptionList.class);
		Ntro.registerSerializableClass(ObservableSemesterIdList.class);
		Ntro.registerSerializableClass(ObservableCourseGroupList.class);

		Ntro.registerSerializableClass(ObservableGroupIdList.class);
		Ntro.registerSerializableClass(ObservableGroupList.class);
		Ntro.registerSerializableClass(ObservableStudentList.class);

		Ntro.registerSerializableClass(GroupList.class);
		Ntro.registerSerializableClass(SemesterCourses.class);
		Ntro.registerSerializableClass(ObservableCourseList.class);
		Ntro.registerSerializableClass(GroupItem.class);

		Ntro.registerSerializableClass(AquiletourDate.class);
		Ntro.registerSerializableClass(CourseDateScheduleItem.class);
		Ntro.registerSerializableClass(CourseDateSemesterDay.class);
		Ntro.registerSerializableClass(SemesterDate.class);

		Ntro.registerSerializableClass(ScheduleItem.class);
		Ntro.registerSerializableClass(ScheduleItems.class);
		Ntro.registerSerializableClass(CourseGroup.class);
		Ntro.registerSerializableClass(AddScheduleItemMessage.class);

		Ntro.registerSerializableClass(User.class);
		Ntro.registerSerializableClass(Teacher.class);
		Ntro.registerSerializableClass(TeacherGuest.class);
		Ntro.registerSerializableClass(Student.class);
		Ntro.registerSerializableClass(StudentGuest.class);
		Ntro.registerSerializableClass(Admin.class);
		Ntro.registerSerializableClass(Guest.class);
		Ntro.registerSerializableClass(SessionData.class);

		Ntro.registerSerializableClass(AddCourseMessage.class);
		Ntro.registerSerializableClass(DeleteCourseMessage.class);
		Ntro.registerSerializableClass(AddAppointmentMessage.class);
		Ntro.registerSerializableClass(DeleteAppointmentMessage.class);
		Ntro.registerSerializableClass(MoveAppointmentMessage.class);
		Ntro.registerSerializableClass(TeacherClosesQueueMessage.class);
		Ntro.registerSerializableClass(TeacherUsesQueueMessage.class);

		Ntro.registerSerializableClass(UserInitiatesLoginMessage.class);
		Ntro.registerSerializableClass(UserLogsOutMessage.class);

		Ntro.registerSerializableClass(UserSendsLoginCodeMessage.class);
		Ntro.registerSerializableClass(AddStudentCsvMessage.class);
		Ntro.registerSerializableClass(AddSubTaskMessage.class);
		Ntro.registerSerializableClass(AddPreviousTaskMessage.class);
		Ntro.registerSerializableClass(AddNextTaskMessage.class);
		Ntro.registerSerializableClass(RegisterExercise.class);
		Ntro.registerSerializableClass(DeRegisterExercise.class);
		Ntro.registerSerializableClass(RegisterRepo.class);
		Ntro.registerSerializableClass(DeRegisterRepo.class);
		Ntro.registerSerializableClass(OnClone.class);
		Ntro.registerSerializableClass(OnCloneFailed.class);
		Ntro.registerSerializableClass(GetCommitsForPath.class);

		Ntro.registerSerializableClass(ObservableTaskDescriptions.class);
		Ntro.registerSerializableClass(TaskDescription.class);

		Ntro.registerSerializableClass(ObservableCourseDate.class);
		Ntro.registerSerializableClass(AtomicTasks.class);
		Ntro.registerSerializableClass(AtomicTask.class);
		Ntro.registerSerializableClass(GitRepoTask.class);
		Ntro.registerSerializableClass(GitExerciseTask.class);

		Ntro.registerSerializableClass(UpdateUserInfoMessage.class);
		Ntro.registerSerializableClass(UpdateTaskInfoMessage.class);
		Ntro.registerSerializableClass(TaskCompletedMessage.class);

		Ntro.registerSerializableClass(ShowCourseListMessageTeacher.class);
		Ntro.registerSerializableClass(ShowCourseListMessageStudent.class);

		Ntro.registerSerializableClass(SelectCourseListSubsetTeacher.class);
		Ntro.registerSerializableClass(SelectCourseListSubsetStudent.class);

		Ntro.registerSerializableClass(GroupDescription.class);
		Ntro.registerSerializableClass(StudentIdList.class);
		Ntro.registerSerializableClass(GroupDescriptionLists.class);

		Ntro.registerSerializableClass(CompletionByStudentId.class);
		Ntro.registerSerializableClass(CompletionsByTaskId.class);

		Ntro.registerSerializableClass(ObservableTime.class);
		Ntro.registerSerializableClass(ObservableTags.class);

		Ntro.registerSerializableClass(ModifyAppointmentDurations.class);
		Ntro.registerSerializableClass(ModifyAppointmentTimes.class);
		Ntro.registerSerializableClass(ToggleStudentModeMessage.class);

		Ntro.registerSerializableClass(DashboardItemStudent.class);
		Ntro.registerSerializableClass(DashboardItemTeacher.class);
		Ntro.registerSerializableClass(CurrentTasks.class);
		Ntro.registerSerializableClass(CurrentTaskStudent.class);
		Ntro.registerSerializableClass(CurrentTaskTeacher.class);

		Ntro.registerSerializableClass(StoredAquiletourDate.class);

		Ntro.registerSerializableClass(UserList.class);
		Ntro.registerSerializableClass(RegistrationId.class);
		Ntro.registerSerializableClass(UserId.class);
		Ntro.registerSerializableClass(UserIdMap.class);

		Ntro.registerSerializableClass(CourseIdList.class);
		Ntro.registerSerializableClass(ShortTextTask.class);
	}
	
	protected abstract NtroWindow getWindow();

	@Override
	protected void onFailure(Exception e) {
		System.err.println("[FATAL] Initialization error");
		e.printStackTrace(System.err);
	}
}
