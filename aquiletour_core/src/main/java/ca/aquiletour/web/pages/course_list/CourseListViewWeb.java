package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.course_list.views.CourseDescriptionView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.web.pages.bases.ListViewWeb;
import ca.ntro.core.system.trace.T;

public class CourseListViewWeb extends ListViewWeb<CourseDescriptionView> implements CourseListView {

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
		super.identifyCurrentSemester(semesterId);
		
		if(semesterId.equals(Constants.COURSE_DRAFTS)) {
			
			String text = "Ajouter un cours aux brouillons";

			getAddItemButton().text(text);
			getModelTitle().text(text);
			
		}else {
			
			String text = "Ajouter un cours à la session " + semesterId;

			getAddItemButton().text(text);
			getModelTitle().text(text);
		}
	}
}
