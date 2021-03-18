package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import def.dom.FileList;
import def.dom.FormData;

public class TeacherCourseSummaryViewWeb extends CourseSummaryViewWeb implements TeacherCourseSummaryView {

	private HtmlElement title;
	private HtmlElement courseId;
	private HtmlElement nbAppointment;
	private HtmlElement numberOfStudents;
	private HtmlElement makeAppointmentLink;
	private HtmlElement deleteCourseLink;
	private HtmlElement closeQueue;
	private HtmlElement csvFileInput;
	private HtmlElement csvFileSubmit;
	private HtmlElement csvFileQueueId;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title").get(0);
		courseId = this.getRootElement().find("#courseId").get(0);
		nbAppointment = this.getRootElement().find("#nbAppointment").get(0);
		numberOfStudents = this.getRootElement().find("#number-of-students").get(0);
		makeAppointmentLink = this.getRootElement().find("#availableLink").get(0);
		deleteCourseLink = this.getRootElement().find("#deleteLink").get(0);
		closeQueue = this.getRootElement().find("#closeQueue").get(0);
		csvFileInput = this.getRootElement().find("#csv-file-input").get(0);
		csvFileSubmit = this.getRootElement().find("#csv-file-submit").get(0);
		csvFileQueueId = this.getRootElement().find("#csv-file-queue-id").get(0);
		
		MustNot.beNull(title);
		MustNot.beNull(courseId);
		MustNot.beNull(nbAppointment);
		MustNot.beNull(numberOfStudents);
		MustNot.beNull(makeAppointmentLink);
		MustNot.beNull(closeQueue);
		MustNot.beNull(csvFileInput);
		MustNot.beNull(csvFileSubmit);
		MustNot.beNull(csvFileQueueId);
		
		addListeners(context);
	}

	private void addListeners(NtroContext<?> context) {
		T.call(this);
		
		csvFileSubmit.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				
				// see: https://stackoverflow.com/questions/5587973/javascript-upload-file
				// and: https://web.dev/read-files/
				FormData formData = new FormData();
			}
		});
	}

	@Override
	public void displayStatus(boolean myAppointment, boolean teacherAvailable) {
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		
		numberOfStudents.html(String.valueOf(course.getNumberOfAppointments()));

		title.appendHtml(course.getTitle());
		//courseId.appendHtml(course.getCourseId());

		nbAppointment.appendHtml(Integer.toString(course.getNumberOfAppointments()));
		makeAppointmentLink.setAttribute("href","/billetterie/" + course.getTitle() + "?makeAppointment");
		deleteCourseLink.setAttribute("href", "/mescours/" + course.getTitle() + "?deleteCourse");

		makeAppointmentLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				
				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(showTeacherQueueMessage);
				
				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(teacherUsesQueueMessage);
			}
		});

		if(course.getIsQueueOpen().getValue()) {
			closeQueue.setAttribute("href", "/billetterie/" + course.getCourseId() + "?teacherClosesQueue");
			closeQueue.appendHtml("CLOSE QUEUE");
		} else {
			closeQueue.setAttribute("href", "/billetterie/" + course.getCourseId());
			closeQueue.appendHtml("OPEN QUEUE");

		}
	}
}
