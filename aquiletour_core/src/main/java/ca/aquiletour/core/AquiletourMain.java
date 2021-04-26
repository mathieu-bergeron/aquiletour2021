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
import ca.aquiletour.core.messages.UserInitiatesLoginMessage;
import ca.aquiletour.core.messages.UserLogsOutMessage;
import ca.aquiletour.core.messages.UserSendsLoginCodeMessage;
import ca.aquiletour.core.messages.git.DeRegisterExercise;
import ca.aquiletour.core.messages.git.DeRegisterRepo;
import ca.aquiletour.core.messages.git.GetCommitsForPath;
import ca.aquiletour.core.messages.git.OnCloneFailed;
import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.messages.git.RegisterExercise;
import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.ObservableCourseDate;
import ca.aquiletour.core.models.courses.base.ObservableTaskIdList;
import ca.aquiletour.core.models.courses.base.ObservableTaskMap;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskRelation;
import ca.aquiletour.core.models.courses.group.Group;
import ca.aquiletour.core.models.courses.group.ObservableGroupMap;
import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.models.courses.student.CompletionByTaskId;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.student.TaskCompletion;
import ca.aquiletour.core.models.courses.teacher.CompletionsByGroup;
import ca.aquiletour.core.models.courses.teacher.CompletionsByStudent;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.courses.teacher.DateByTaskId;
import ca.aquiletour.core.models.courses.teacher.GroupTaskCompletions;
import ca.aquiletour.core.models.courses.teacher.InfoByStudent;
import ca.aquiletour.core.models.courses.teacher.TaskDatesByGroupId;
import ca.aquiletour.core.models.dates.CalendarDate;
import ca.aquiletour.core.models.dates.CourseDateScheduleItem;
import ca.aquiletour.core.models.dates.CourseDateSemesterDay;
import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItems;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Admin;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.messages.AddNextTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddPreviousTaskMessage;
import ca.aquiletour.core.pages.course.messages.AddSubTaskMessage;
import ca.aquiletour.core.pages.course_list.models.CourseListModel;
import ca.aquiletour.core.pages.course_list.messages.AddCourseMessage;
import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.ObservableCourseDescriptionList;
import ca.aquiletour.core.pages.course_list.models.ObservableGroupIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.aquiletour.core.pages.course_list.models.ObservableTaskDescriptions;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.aquiletour.core.pages.dashboards.values.ObservableDashboardCourseList;
import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.git.values.ObservableCommitList;
import ca.aquiletour.core.pages.group_list.models.ObservableCourseList;
import ca.aquiletour.core.pages.group_list.models.GroupItem;
import ca.aquiletour.core.pages.group_list.models.GroupListModel;
import ca.aquiletour.core.pages.group_list.models.ObservableGroupList;
import ca.aquiletour.core.pages.group_list.models.ObservableStudentList;
import ca.aquiletour.core.pages.group_list.models.SemesterCourses;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListModel;
import ca.aquiletour.core.pages.open_queue_list.values.ObservableQueueList;
import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.core.pages.queue.student.messages.AddAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.DeleteAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.aquiletour.core.pages.queue.values.ObservableAppointmentList;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.models.ObservableSemesterWeekList;
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
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroInitializationTask;

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
				rootController.changeUser(message.getUser());
			}
		});
	}
	
	public static void registerSerializableClasses() {
		T.call(AquiletourMain.class);

		Ntro.registerSerializableClass(DashboardModel.class);
		Ntro.registerSerializableClass(ObservableDashboardCourseList.class);
		Ntro.registerSerializableClass(DashboardItem.class);

		Ntro.registerSerializableClass(QueueModel.class);
		Ntro.registerSerializableClass(ObservableAppointmentList.class);
		Ntro.registerSerializableClass(Appointment.class);

		Ntro.registerSerializableClass(OpenQueueListModel.class);
		Ntro.registerSerializableClass(ObservableQueueList.class);
		Ntro.registerSerializableClass(OpenQueue.class);
		Ntro.registerSerializableClass(CommitListModel.class);
		Ntro.registerSerializableClass(ObservableCommitList.class);
		Ntro.registerSerializableClass(Commit.class);

		Ntro.registerSerializableClass(CourseModel.class);
		Ntro.registerSerializableClass(CoursePath.class);
		Ntro.registerSerializableClass(ObservableTaskMap.class);
		Ntro.registerSerializableClass(ObservableTaskIdList.class);
		Ntro.registerSerializableClass(Task.class);
		Ntro.registerSerializableClass(TaskRelation.class);

		Ntro.registerSerializableClass(Group.class);
		Ntro.registerSerializableClass(StudentDescription.class);
		Ntro.registerSerializableClass(ObservableGroupMap.class);

		Ntro.registerSerializableClass(SemesterModel.class);
		Ntro.registerSerializableClass(SemesterListModel.class);
		Ntro.registerSerializableClass(ObservableSemesterList.class);
		Ntro.registerSerializableClass(ObservableSemesterWeekList.class);
		Ntro.registerSerializableClass(SemesterWeek.class);

		Ntro.registerSerializableClass(SemesterSchedule.class);
		Ntro.registerSerializableClass(TeacherSchedule.class);

		Ntro.registerSerializableClass(CourseModelTeacher.class);
		Ntro.registerSerializableClass(CourseModelStudent.class);
		Ntro.registerSerializableClass(CompletionsByGroup.class);
		Ntro.registerSerializableClass(CompletionsByStudent.class);
		Ntro.registerSerializableClass(DateByTaskId.class);
		Ntro.registerSerializableClass(GroupTaskCompletions.class);
		Ntro.registerSerializableClass(InfoByStudent.class);
		Ntro.registerSerializableClass(TaskDatesByGroupId.class);
		Ntro.registerSerializableClass(TaskCompletion.class);
		Ntro.registerSerializableClass(CompletionByTaskId.class);

		Ntro.registerSerializableClass(CourseItem.class);
		Ntro.registerSerializableClass(CourseListModel.class);
		Ntro.registerSerializableClass(ObservableCourseDescriptionList.class);
		Ntro.registerSerializableClass(ObservableSemesterIdList.class);
		Ntro.registerSerializableClass(ObservableCourseGroupList.class);

		Ntro.registerSerializableClass(ObservableGroupIdList.class);
		Ntro.registerSerializableClass(ObservableGroupList.class);
		Ntro.registerSerializableClass(ObservableStudentList.class);

		Ntro.registerSerializableClass(GroupListModel.class);
		Ntro.registerSerializableClass(SemesterCourses.class);
		Ntro.registerSerializableClass(ObservableCourseList.class);
		Ntro.registerSerializableClass(GroupItem.class);

		Ntro.registerSerializableClass(CalendarDate.class);
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
	}
	
	protected abstract NtroWindow getWindow();
	

	@Override
	protected void onFailure(Exception e) {
		System.err.println("[FATAL] Initialization error");
		e.printStackTrace(System.err);
	}
}
