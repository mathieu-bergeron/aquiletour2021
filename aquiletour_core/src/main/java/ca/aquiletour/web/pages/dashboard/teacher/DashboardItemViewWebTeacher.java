package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.views.DashboardCourseViewTeacher;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class DashboardItemViewWebTeacher extends DashboardItemViewWeb<CurrentTaskTeacher> implements DashboardCourseViewTeacher {
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		
	}

	@Override
	protected HtmlElement createTaskLi(int index, 
			                           CoursePath coursePath, 
			                           CurrentTaskTeacher currentTask) {
		T.call(this);
		
		String queueId = coursePath.teacherId();

		HtmlElement taskLi = getRootElement().createElement("<li class='list-group-item'></li>");
		HtmlElement container = taskLi.createElement("<div class='d-flex justify-content-start'></div>");
		taskLi.appendElement(container);
		
		HtmlElement taskHref = container.createElement("<a></a>");
		container.appendElement(taskHref);
		taskHref.setAttribute("href", "/" 
		                              + Constants.COURSE_URL_SEGMENT 
		                              + coursePath.toUrlPath() 
		                              + currentTask.getTaskPath().toString() 
		                              + "?" 
		                              + Constants.GROUP_URL_PARAM
		                              + "=" 
		                              + Constants.ALL_GROUPS_ID);

		taskHref.text(currentTask.getTaskTitle().getValue());
		
		HtmlElement hFill = container.createElement("<div class='d-fill me-3'></div>");
		container.appendElement(hFill);
		
		HtmlElement numberOfStudentsElement = container.createElement("<div class='number-of-students-container'></div>");
		container.appendElement(numberOfStudentsElement);
		int numberOfStudents = currentTask.getNumberOfStudents().getValue();
		updateNumberOfStudents(numberOfStudentsElement, numberOfStudents);

		return taskLi;
	}

	private void updateNumberOfStudents(HtmlElement numberOfStudentsElement, int numberOfStudents) {
		T.call(this);
		if(numberOfStudents == 1) {
			numberOfStudentsElement.text(numberOfStudents + " étudiant·e");
		}else {
			numberOfStudentsElement.text(numberOfStudents + " étudiant·es");
		}
	}
}
