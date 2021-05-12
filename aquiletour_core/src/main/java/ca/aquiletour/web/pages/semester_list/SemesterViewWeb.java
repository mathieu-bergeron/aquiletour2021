package ca.aquiletour.web.pages.semester_list;


import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;

import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class SemesterViewWeb extends NtroViewWeb implements SemesterView {

	private HtmlElement semesterIdHeader;
	private HtmlElement currentSemesterCheckbox;

	private HtmlElement weeksTbody;


	private HtmlElements addIdToValue;
	private HtmlElements addIdToId;
	private HtmlElements addIdToForm;
	private HtmlElements addIdToDataTarget;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		semesterIdHeader = this.getRootElement().find("#semester-id").get(0);
		weeksTbody = this.getRootElement().find("#semester-weeks-tbody").get(0);
		currentSemesterCheckbox = this.getRootElement().find(".aquiletour-checkbox").get(0);

		MustNot.beNull(semesterIdHeader);
		MustNot.beNull(weeksTbody);
		MustNot.beNull(currentSemesterCheckbox);

		addIdToValue = this.getRootElement().find(".add-id-to-value");
		addIdToId = this.getRootElement().find(".add-id-to-id");
		addIdToForm = this.getRootElement().find(".add-id-to-form");
		addIdToDataTarget = this.getRootElement().find(".add-id-to-data-target");
	}

	@Override
	public void displaySemester(SemesterModel item, boolean isCurrentSemester) {
		T.call(this);
		
		semesterIdHeader.text(item.getSemesterId());
		
		addIdToValue.appendToAttribute("value", item.getSemesterId());
		addIdToId.appendToAttribute("id", item.getSemesterId());
		addIdToForm.appendToAttribute("form", item.getSemesterId());
		addIdToDataTarget.appendToAttribute("data-target", item.getSemesterId());

		if(isCurrentSemester) {
			currentSemesterCheckbox.setAttribute("checked", "");
		}else {
			currentSemesterCheckbox.removeAttribute("checked");
		}
	}

	@Override
	public void appendSemesterWeek(CalendarWeek semesterWeek) {
		T.call(this);
		
		// Insert before the second to last (the form takes two rows)
		int numberOfRows = weeksTbody.find("tr").size();
		
		if(numberOfRows > 1) {

			HtmlElement lastRow = weeksTbody.find("tr").get(numberOfRows-2);

			HtmlElement dataRow = weeksTbody.createTag("tr");

			HtmlElement weekOfCell = dataRow.createTag("td");

			weekOfCell.html(semesterWeek.getMondayDate().format("d MMM"));
			dataRow.appendElement(weekOfCell);
			
			for(int dayOfWeek = NtroDayOfWeek.MONDAY; dayOfWeek <= NtroDayOfWeek.FRIDAY; dayOfWeek++) {
				SemesterDate day = semesterWeek.getDays().get(dayOfWeek);

				HtmlElement dayCell = dataRow.createTag("td");
				if(day.hasDifferentSchedule()) {
					dayCell.html(String.valueOf(day.getSemesterWeek() + " (" + day.getScheduleOf().shortName()) + ")");
				}else {
					dayCell.html(String.valueOf(day.getSemesterWeek()));
				}

				dataRow.appendElement(dayCell);
			}
			
			dataRow.insertBefore(lastRow);
		}
	}

	@Override
	public void displayCalendarSummary(String semesterSummaryText) {
		T.call(this);
		// XXX: not supported here
	}
	
	protected HtmlElement currentSemesterCheckbox() {
		T.call(this);
		
		return currentSemesterCheckbox;
	}
}
