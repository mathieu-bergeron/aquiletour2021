package ca.aquiletour.web.pages.semester_list;


import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.pages.semester_list.models.CourseGroup;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import static ca.ntro.assertions.Factory.that;

import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterViewWeb extends NtroViewWeb implements SemesterView {

	private HtmlElement semesterIdHeader;
	private HtmlElement weeksTbody;
	private HtmlElement currentSemesterCheckbox;
	private HtmlElement courseGroupSelect;

	private HtmlElements addIdToValue;
	private HtmlElements addIdToId;
	private HtmlElements addIdToForm;
	private HtmlElements addIdToHref;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		semesterIdHeader = this.getRootElement().find("#semester-id").get(0);
		weeksTbody = this.getRootElement().find("#semester-weeks-tbody").get(0);
		currentSemesterCheckbox = this.getRootElement().find(".current-semester-checkbox").get(0);
		courseGroupSelect = this.getRootElement().find("#course-group-select").get(0);

		MustNot.beNull(semesterIdHeader);
		MustNot.beNull(weeksTbody);
		MustNot.beNull(currentSemesterCheckbox);
		MustNot.beNull(courseGroupSelect);

		addIdToValue = this.getRootElement().find(".add-id-to-value");
		addIdToId = this.getRootElement().find(".add-id-to-id");
		addIdToForm = this.getRootElement().find(".add-id-to-form");
		addIdToHref = this.getRootElement().find(".add-id-to-href");
		
		Ntro.verify(that(addIdToValue.size() > 0).isTrue());
		Ntro.verify(that(addIdToId.size() > 0).isTrue());
		Ntro.verify(that(addIdToForm.size() > 0).isTrue());
		Ntro.verify(that(addIdToHref.size() > 0).isTrue());
	}

	@Override
	public void displaySemester(SemesterModel item) {
		T.call(this);
		
		semesterIdHeader.text(item.getSemesterId());
		
		addIdToValue.appendToAttribute("value", item.getSemesterId());
		addIdToId.appendToAttribute("id", item.getSemesterId());
		addIdToForm.appendToAttribute("form", item.getSemesterId());
		addIdToHref.appendToAttribute("href", item.getSemesterId());
		
		SessionData sessionData = (SessionData) Ntro.currentSession().getSessionData();
		
		if(sessionData.getCurrentSemester().equals(item.getSemesterId())) {
			currentSemesterCheckbox.setAttribute("checked", "");
		}else {
			currentSemesterCheckbox.removeAttribute("checked");
		}
	}

	@Override
	public void appendSemesterWeek(SemesterWeek semesterWeek) {
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
	public void appendCourseGroupe(CourseGroup courseGroup) {
		T.call(this);
		
		HtmlElement option = courseGroupSelect.createElement("<option></option>");
		option.setAttribute("name", "courseGroup");
		
		option.text(courseGroup.toPath().toString());

		courseGroupSelect.appendElement(option);
	}
}
