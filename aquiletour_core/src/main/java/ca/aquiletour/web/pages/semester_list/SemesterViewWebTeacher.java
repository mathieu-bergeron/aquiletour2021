package ca.aquiletour.web.pages.semester_list;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.teacher.views.SemesterViewTeacher;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public class SemesterViewWebTeacher extends SemesterViewWeb implements SemesterViewTeacher {

	private HtmlElement courseGroupSelect;
	private HtmlElement scheduleTbody;
	private HtmlElement scheduleSummary;
	private HtmlElement availabilitySummary;
	private HtmlElement calendarSummary;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		
		super.initializeViewWeb(context);

		courseGroupSelect = this.getRootElement().find("#course-group-select").get(0);
		scheduleTbody = this.getRootElement().find("#schedule-tbody").get(0);
		scheduleSummary = this.getRootElement().find("#schedule-summary").get(0);
		availabilitySummary = this.getRootElement().find("#availability-summary").get(0);
		calendarSummary = this.getRootElement().find("#calendar-summary").get(0);
		MustNot.beNull(calendarSummary);

		MustNot.beNull(courseGroupSelect);
		MustNot.beNull(scheduleTbody);
	}

	@Override
	public void displayScheduleSummary(String scheduleSummaryText) {
		T.call(this);
		
		scheduleSummary.text(scheduleSummaryText);
	}

	@Override
	public void displayAvailabilitySummary(String availabilitySummaryText) {
		T.call(this);
		
		availabilitySummary.text(availabilitySummaryText);
	}

	@Override
	public void appendCourseGroupe(CourseGroup courseGroup) {
		T.call(this);
		
		HtmlElement option = courseGroupSelect.createElement("<option></option>");
		option.setAttribute("name", "courseGroup");
		
		option.text(courseGroup.toString());

		courseGroupSelect.appendElement(option);
	}

	@Override
	public void appendScheduleItem(ScheduleItem item) {
		T.call(this);

		int numberOfRows = scheduleTbody.find("tr").size();

		if(numberOfRows > 0) {

			HtmlElement lastRow = scheduleTbody.find("tr").get(numberOfRows-1);

			HtmlElement dataRow = scheduleTbody.createTag("tr");

			HtmlElement dayCell = dataRow.createTag("td");
			HtmlElement startTimeCell = dataRow.createTag("td");
			HtmlElement endTimeCell = dataRow.createTag("td");
			HtmlElement courseGroupCell = dataRow.createTag("td");
			HtmlElement scheduleItemIdCell = dataRow.createTag("td");
			
			dayCell.text(item.getDayOfWeek().shortName());
			startTimeCell.text(item.getStartTime().toString());
			endTimeCell.text(item.getEndTime().toString());
			courseGroupCell.text(item.getCourseGroup().toString());
			scheduleItemIdCell.text(item.getScheduleItemId());
			
			dataRow.appendElement(dayCell);
			dataRow.appendElement(startTimeCell);
			dataRow.appendElement(endTimeCell);
			dataRow.appendElement(courseGroupCell);
			dataRow.appendElement(scheduleItemIdCell);
			
			dataRow.insertBefore(lastRow);
		}
		
	}

	@Override
	public void displayCalendarSummary(String semesterSummaryText) {
		T.call(this);
		
		calendarSummary.text(semesterSummaryText);
	}
}
