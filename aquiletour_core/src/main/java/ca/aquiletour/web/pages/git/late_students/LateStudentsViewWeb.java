package ca.aquiletour.web.pages.git.late_students;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsModel;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public class LateStudentsViewWeb extends NtroViewWeb implements LateStudentsView {
	@Override
	public void initializeViewWeb(NtroContext<?> context) {

	}

	@Override
	public void displayLateStudents(LateStudentsModel lateStudentsModel) {
		// TODO Auto-generated method stub
		HtmlElement semesterId = this.getRootElement().find("#semesterId").get(0);
		HtmlElement courseId = this.getRootElement().find("#courseId").get(0);
		HtmlElement exercisePath = this.getRootElement().find("#exercisePath").get(0);
		HtmlElement studentIds = this.getRootElement().find("#studentIds").get(0);
		HtmlElement groupId = this.getRootElement().find("#groupId").get(0);
		HtmlElement deadline = this.getRootElement().find("#deadline").get(0);

		MustNot.beNull(semesterId);
		MustNot.beNull(courseId);
		MustNot.beNull(exercisePath);
		MustNot.beNull(studentIds);
		MustNot.beNull(groupId);
		MustNot.beNull(deadline);
		
		semesterId.text(lateStudentsModel.getSemesterId());
		courseId.text(lateStudentsModel.getCourseId());
		exercisePath.text(lateStudentsModel.getExercisePath());
		groupId.text(lateStudentsModel.getGroupId());
		deadline.text(lateStudentsModel.getDeadline());
		for (int i = 0; i < lateStudentsModel.getStudentIds().size(); i++) {
			
			studentIds.appendHtml(lateStudentsModel.getStudentIds().get(i));
			
			if(i == lateStudentsModel.getStudentIds().size() - 1) {
				studentIds.appendHtml(".");  
			}else {
				studentIds.appendHtml(", ");
			}
		} 
	}
}
