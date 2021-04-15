package ca.aquiletour.web.pages.semester_list;


import ca.aquiletour.core.models.dates.SemesterDate;
import ca.aquiletour.core.models.dates.SemesterDay;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import static ca.ntro.assertions.Factory.that;

import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.pages.semester_list.views.SemesterView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class SemesterViewWeb extends NtroViewWeb implements SemesterView {

	private HtmlElement semesterIdHeader;
	private HtmlElements addIdToValue;
	private HtmlElement weeksTbody;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		semesterIdHeader = this.getRootElement().find("#semester-id").get(0);
		addIdToValue = this.getRootElement().find(".add-id-to-value");
		weeksTbody = this.getRootElement().find("#semester-weeks-tbody").get(0);
		
		MustNot.beNull(semesterIdHeader);
		MustNot.beNull(weeksTbody);
		Ntro.verify(that(addIdToValue.size() > 0).isTrue());
	}

	@Override
	public void displaySemester(SemesterModel item) {
		T.call(this);
		
		semesterIdHeader.text(item.getSemesterId());
		
		addIdToValue.forEach(e -> {
			String value = e.getAttribute("value");
			value += item.getSemesterId();
			e.setAttribute("value", value);
		});
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
			
			for(int dayOfWeek = SemesterDay.MONDAY; dayOfWeek <= SemesterDay.FRIDAY; dayOfWeek++) {
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
}
