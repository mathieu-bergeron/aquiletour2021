package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherClosesQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.TeacherUsesQueueMessage;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;

public class TeacherCourseSummaryViewWeb extends CourseSummaryViewWeb implements TeacherCourseSummaryView {

	private HtmlElement title;
	private HtmlElement numberOfStudents;
	private HtmlElement deleteQueue;
	private HtmlElement closeQueue;
	private HtmlElement csvFileInput;
	private HtmlElement csvFileSubmit;
	private HtmlElement csvFileQueueId;

	private String openQueueHref;
	private String closeQueueHref;
	private String deleteQueueHref;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		title = this.getRootElement().find("#course-title-link").get(0);
		numberOfStudents = this.getRootElement().find("#number-of-students").get(0);
		deleteQueue = this.getRootElement().find("#delete-queue-link").get(0);
		closeQueue = this.getRootElement().find("#close-queue-link").get(0);
		csvFileInput = this.getRootElement().find("#csv-file-input").get(0);
		csvFileSubmit = this.getRootElement().find("#csv-file-submit").get(0);
		csvFileQueueId = this.getRootElement().find("#csv-file-queue-id").get(0);
		
		MustNot.beNull(title);
		MustNot.beNull(numberOfStudents);
		MustNot.beNull(closeQueue);
		MustNot.beNull(csvFileInput);
		MustNot.beNull(csvFileSubmit);
		MustNot.beNull(csvFileQueueId);
		
		openQueueHref = title.getAttribute("href");
		closeQueueHref = closeQueue.getAttribute("href");
		deleteQueueHref = deleteQueue.getAttribute("href");
		
		addListeners(context);
	}

	private void addListeners(NtroContext<?> context) {
		T.call(this);
		
	}

	@Override
	public void displayStatus(String QueueId, boolean myAppointment, boolean teacherAvailable) {
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);

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

		numberOfStudents.html(String.valueOf(course.getNumberOfStudents().getValue()));
		title.appendHtml(course.getTitle());
	}

	private void installQueueActions(CourseSummary course) {
		T.call(this);

		installerOpenQueueListener(course);

		installCloseQueueListener(course);

		adjustOpenCloseLinks(course);

		deleteQueue.setAttribute("href", deleteQueueHref + course.getCourseId());
	}

	private void adjustOpenCloseLinks(CourseSummary course) {
		T.call(this);

		title.setAttribute("href", openQueueHref + course.getCourseId());
		closeQueue.setAttribute("href", closeQueueHref + course.getCourseId());

		if(course.getIsQueueOpen().getValue()) {
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
				
				ShowTeacherQueueMessage showTeacherQueueMessage = Ntro.messages().create(ShowTeacherQueueMessage.class);
				showTeacherQueueMessage.setCourseId(course.getCourseId());

				Ntro.messages().send(showTeacherQueueMessage);
				
				TeacherUsesQueueMessage teacherUsesQueueMessage = Ntro.messages().create(TeacherUsesQueueMessage.class);
				teacherUsesQueueMessage.setCourseId(course.getCourseId());
				Ntro.messages().send(teacherUsesQueueMessage);
			}
		});
	}

}
