package ca.aquiletour.web;

import java.util.Map;

import ca.aquiletour.core.AiguilleurApp;
import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.student.StudentDashboardView;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.queue.student.StudentAppointmentView;
import ca.aquiletour.core.pages.queue.student.StudentQueueView;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.TeacherAppointmentView;
import ca.aquiletour.core.pages.queue.teacher.TeacherQueueView;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queues.QueueSummaryView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.aquiletour.web.pages.dashboard.student.StudentCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.student.StudentDashboardViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherCourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.teacher.TeacherDashboardViewWeb;
import ca.aquiletour.web.pages.home.HomeViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.student.StudentQueueViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherAppointmentViewWeb;
import ca.aquiletour.web.pages.queue.teacher.TeacherQueueViewWeb;
import ca.aquiletour.web.pages.queues.QueueSummaryViewWeb;
import ca.aquiletour.web.pages.queues.QueuesViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.aquiletour.web.pages.users.UserViewWeb;
import ca.aquiletour.web.pages.users.UsersViewWeb;
import ca.ntro.web.RouterRegistrar;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.messages.MessageFactory;
import ca.ntro.services.Ntro;
import ca.ntro.web.NtroAppWeb;
import ca.ntro.web.NtroRouter;
import ca.ntro.web.ViewRegistrarWeb;

public class AiguilleurAppWeb extends AiguilleurApp implements NtroAppWeb {

	@Override
	public void registerRouters(RouterRegistrar registrar) {

		// Auto-router matching path and user role
		registrar.registerRouter("/mescours", Teacher.class, ShowTeacherDashboardMessage.class);
		registrar.registerRouter("/mescours", Student.class, ShowStudentDashboardMessage.class);

		registrar.registerRouter("/billetterie", Teacher.class, ShowTeacherQueueMessage.class);
		registrar.registerRouter("/billetterie", Student.class, ShowStudentQueueMessage.class);

		registrar.registerRouter("/billetteries", User.class, ShowQueuesMessage.class);

		registrar.registerRouter("/usagers", User.class, ShowUsersMessage.class);
		registrar.registerRouter("/connexion", User.class, ShowLoginMessage.class);
		registrar.registerRouter("/accueil", User.class, ShowHomeMessage.class);
		
		// Manual router
		registrar.registerRouter(new NtroRouter<User>() {
			@Override
			public void route(Path path, 
					          Map<String,String[]> parameters, 
					          NtroContext<User> context) {

				if(path.startsWith("mescours")
						&& context.user() instanceof Teacher) {
					
					Ntro.messageService().sendMessage(ShowTeacherDashboardMessage.class);

				} else if(path.startsWith("mescours")
						&& context.user() instanceof Student) {
					
					Ntro.messageService().sendMessage(ShowStudentDashboardMessage.class);

				} else if(path.startsWith("csv")) {
					if(parameters.containsKey("queueId")) {

						String queueId = parameters.get("queueId")[0];
					
						AddStudentCsvMessage addStudentCsvMessage = new AddStudentCsvMessage();
						addStudentCsvMessage.setQueueId(queueId);
						addStudentCsvMessage.setUser(context.user());
						
						// [...]
					}
				}
			}
		});
	}

	@Override
	public void registerViews(ViewRegistrarWeb registrar) {
		
		registrar.registerView(RootView.class,
				               "fr",
			     	           "/views/root/root.html",
			     	           "/views/root/root.css",
			     	           "/i18n/fr/string.json",
			     	           RootViewWeb.class);

		registrar.registerView(TeacherDashboardView.class,
				               "fr",
			     	           "/views/dashboards/teacher_dashboard/teacher_dashboard.html",
			     	           "/views/dashboards/teacher_dashboard/teacher_dashboard.css",
			     	           "/i18n/fr/string.json",
			     	           TeacherDashboardViewWeb.class);

		registrar.registerView(StudentDashboardView.class,
				               "fr",
			     	           "/views/dashboards/student_dashboard/student_dashboard.html",
			     	           "/views/dashboards/student_dashboard/student_dashboard.css",
			     	           "/i18n/fr/string.json",
			     	           StudentDashboardViewWeb.class);

		registrar.registerView(TeacherCourseSummaryView.class,
				              "fr",
				              "/views/course_summaries/teacher_course_summary/course_summary.html",
		     	              "/views/course_summaries/teacher_course_summary/course_summary.css",
				              "/i18n/fr/string.json",
				              TeacherCourseSummaryViewWeb.class);
		
		registrar.registerView(StudentCourseSummaryView.class,
				               "fr",
			     	           "/views/course_summaries/student_course_summary/course_summary.html",
			     	           "/views/course_summaries/student_course_summary/course_summary.css",
			     	           "/i18n/fr/string.json",
			     	           StudentCourseSummaryViewWeb.class);

		registrar.registerView(StudentQueueView.class,
				               "fr",
				               "/views/queue/student_queue/queue.html",
				               "/views/queue/student_queue/queue.css",
				               "/i18n/fr/string.json",
				               StudentQueueViewWeb.class);
		
		registrar.registerView(TeacherQueueView.class,
				               "fr",
			     	           "/views/queue/teacher_queue/queue.html",
			     	           "/views/queue/teacher_queue/queue.css",
			     	           "/i18n/fr/string.json",
			     	           TeacherQueueViewWeb.class);

		registrar.registerView(QueuesView.class,
				               "fr",
			     	           "/views/queues/queues.html",
			     	           "/views/queues/queues.css",
			     	           "/i18n/fr/string.json",
			     	           QueuesViewWeb.class);

		registrar.registerView(QueueSummaryView.class,
				               "fr",
			     	           "/views/queue_summary/queue_summary.html",
			     	           "/views/queue_summary/queue_summary.css",
			     	           "/i18n/fr/string.json",
			     	           QueueSummaryViewWeb.class);

		registrar.registerView(TeacherAppointmentView.class,
				               "fr",
				               "/views/appointment/teacher_appointment/appointment.html",
				               "/views/appointment/teacher_appointment/appointment.css",
				               "/i18n/fr/string.json",
				               TeacherAppointmentViewWeb.class);
		
		registrar.registerView(StudentAppointmentView.class,
				               "fr",
			     	           "/views/appointment/student_appointment/appointment.html",
			     	           "/views/appointment/student_appointment/appointment.css",
			     	           "/i18n/fr/string.json",
			     	           StudentAppointmentViewWeb.class);
		
		registrar.registerView(UsersView.class,
				               "fr",
			     	           "/views/users/users.html",
			     	           "/views/users/users.css",
			     	           "/i18n/fr/string.json",
           			     	   UsersViewWeb.class);

		registrar.registerView(UserView.class,
				               "fr",
				               "/views/user/user.html",
				               "/views/user/user.css",
				               "/i18n/fr/string.json",
				               UserViewWeb.class);
		
		registrar.registerView(LoginView.class,
				               "fr",
				               "/views/login/login.html",
				               "/views/login/login.css",
				               "/i18n/fr/string.json",
				               LoginViewWeb.class);
		
		registrar.registerView(HomeView.class,
				               "fr",
			     	           "/views/home/home.html",
           			     	   "/views/home/home.css",
			     	           "/i18n/fr/string.json",
			     	           HomeViewWeb.class);
		
	}


}
