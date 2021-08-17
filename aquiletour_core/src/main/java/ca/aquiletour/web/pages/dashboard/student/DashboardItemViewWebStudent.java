package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.views.DashboardCourseViewStudent;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class DashboardItemViewWebStudent extends DashboardItemViewWeb<CurrentTaskStudent> implements DashboardCourseViewStudent {

	@Override
	protected HtmlElement createTaskLi(int index, 
			                           CoursePath coursePath, 
			                           CurrentTaskStudent currentTask) {
		T.call(this);
		
		String queueId = coursePath.teacherId();

		HtmlElement taskLi = getRootElement().createElement("<li class='list-group-item'></li>");
		HtmlElement container = taskLi.createElement("<div class='d-flex justify-content-start'></div>");
		taskLi.appendElement(container);
		
		HtmlElement taskHref = container.createElement("<a></a>");
		container.appendElement(taskHref);
		taskHref.setAttribute("href", "/" + Constants.COURSE_URL_SEGMENT + coursePath.toUrlPath() + currentTask.getTaskPath().toString());
		taskHref.text(currentTask.getTaskTitle().getValue());
		
		HtmlElement hFill = container.createElement("<div class='d-fill ms-3 me-3'></div>");
		container.appendElement(hFill);
		
		HtmlElement makeAppointmentForm = container.createElement("<form method='post'></form>");
		container.appendElement(makeAppointmentForm);
		makeAppointmentForm.setAttribute("action", "/" + Constants.QUEUE_URL_SEGMENT + "/" + queueId);

		HtmlElement coursePathInput = container.createElement("<input name='coursePath'></input>");
		makeAppointmentForm.appendElement(coursePathInput);
		coursePathInput.hide();
		coursePathInput.setAttribute("value", coursePath.toKey());

		HtmlElement taskPathInput = container.createElement("<input name='taskPath'></input>");
		makeAppointmentForm.appendElement(taskPathInput);
		taskPathInput.hide();
		taskPathInput.setAttribute("value", currentTask.getTaskPath().toKey());

		HtmlElement taskTitleInput = container.createElement("<input name='taskTitle'></input>");
		makeAppointmentForm.appendElement(taskTitleInput);
		taskTitleInput.hide();
		taskTitleInput.setAttribute("value", currentTask.getTaskTitle().getValue());

		HtmlElement makeAppointmentButton = container.createElement("<input class='btn btn-secondary btn-sm' type='submit'></input>");
		makeAppointmentForm.appendElement(makeAppointmentButton);
		makeAppointmentButton.value("Poser une question");
		makeAppointmentButton.setAttribute("name", "makeAppointment");

		return taskLi;
	}
}
