package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class CourseSummaryViewWebTeacher extends CourseSummaryViewWeb implements TeacherCourseSummaryView {

	private HtmlElement title;
	private HtmlElement numberOfStudentsElement;
	private HtmlElement numberOfAppointmentsElement;
	private HtmlElement deleteQueue;
	private HtmlElement closeQueue;
	private HtmlElement csvFileInput;
	private HtmlElement csvFileSubmit;
	private HtmlElement csvFileQueueId;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		title = this.getRootElement().find("#course-title-link").get(0);
		numberOfStudentsElement = this.getRootElement().find("#number-of-students").get(0);
		numberOfAppointmentsElement = this.getRootElement().find("#number-of-appointments").get(0);
		deleteQueue = this.getRootElement().find("#delete-queue-link").get(0);
		closeQueue = this.getRootElement().find("#close-queue-link").get(0);
		csvFileInput = this.getRootElement().find("#csv-file-input").get(0);
		csvFileSubmit = this.getRootElement().find("#csv-file-submit").get(0);
		csvFileQueueId = this.getRootElement().find("#csv-file-queue-id").get(0);
		
		MustNot.beNull(title);
		MustNot.beNull(numberOfStudentsElement);
		MustNot.beNull(numberOfAppointmentsElement);
		MustNot.beNull(closeQueue);
		MustNot.beNull(csvFileInput);
		MustNot.beNull(csvFileSubmit);
		MustNot.beNull(csvFileQueueId);
		
	}

	@Override
	public void displayStatus(String QueueId, boolean myAppointment, boolean teacherAvailable) {
		T.call(this);

		showOrHideCloseQueue(teacherAvailable);
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		super.displaySummary(course);

		displayQueueInfo(course);

		installQueueActions(course);

		setUpCsvFileUpload(course);
	}

	private void setUpCsvFileUpload(CourseSummary course) {
		T.call(this);

		csvFileSubmit.addEventListener("click", new CsvSubmitListener(course.getCourseId(), csvFileInput));
		csvFileQueueId.value(course.getCourseId());
	}

	private void displayQueueInfo(CourseSummary course) {
		T.call(this);

		title.appendHtml(course.getTitle());
		displayNumberOfAppointments(course.getNumberOfAppointments().getValue());
		displayNumberOfStudents(course.getNumberOfStudents().getValue());
	}

	private void installQueueActions(CourseSummary course) {
		T.call(this);

		installerOpenQueueListener(course);

		installCloseQueueListener(course);

		adjustOpenCloseLinks(course);

		installDeleteQueueListener(course);
		
	}

	private void installDeleteQueueListener(CourseSummary course) {
		T.call(this);
		
		Path coursePath = new Path(course.getCourseId());

		deleteQueue.setAttribute("href", "?" + Constants.DELETE_QUEUE_URL_PARAM  + "=" + coursePath.toFileName());
		
		deleteQueue.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				DeleteCourseMessage deleteCourseMessage = Ntro.messages().create(DeleteCourseMessage.class);
				deleteCourseMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(deleteCourseMessage);
			}
		});
	}


	private void adjustOpenCloseLinks(CourseSummary course) {
		T.call(this);
		
		Path coursePath = new Path(course.getCourseId());

		title.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT + "/" + coursePath.toFileName());
		closeQueue.setAttribute("href", "?" + Constants.CLOSE_QUEUE_URL_PARAM + "=" + coursePath.toFileName());

		showOrHideCloseQueue(course.getIsQueueOpen().getValue());
	}

	private void showOrHideCloseQueue(boolean isQueueOpen) {
		T.call(this);

		if(isQueueOpen) {
			closeQueue.show();
		} else {
			closeQueue.hide();
		}
	}

	private void installCloseQueueListener(CourseSummary course) {
		T.call(this);

		closeQueue.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				
				TeacherClosesQueueMessage teacherClosesQueueMessage = Ntro.messages().create(TeacherClosesQueueMessage.class);
				teacherClosesQueueMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(teacherClosesQueueMessage);
			}
		});
	}

	private void installerOpenQueueListener(CourseSummary course) {
		T.call(this);

		title.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.here();
				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(showTeacherQueueMessage);

				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(teacherUsesQueueMessage);
			}
		});
	}

	@Override
	public void displayNumberOfAppointments(int numberOfAppointments) {
		T.call(this);

		numberOfAppointmentsElement.html(String.valueOf(numberOfAppointments));
	}

	@Override
	public void displayNumberOfStudents(int numberOfStudents) {
		T.call(this);

		numberOfStudentsElement.html(String.valueOf(numberOfStudents));
	}
}
