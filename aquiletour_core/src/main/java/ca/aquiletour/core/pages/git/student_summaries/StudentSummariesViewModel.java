package ca.aquiletour.core.pages.git.student_summaries;

import java.util.List;

import ca.aquiletour.core.pages.git.values.StudentSummary;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class StudentSummariesViewModel extends ModelViewSubViewHandler<StudentSummariesModel,StudentSummariesView>  {
	
	@Override
	protected void handle(StudentSummariesModel model, StudentSummariesView view, ViewLoader subViewLoader) {
		T.call(this);
		
		view.displayStudentSummaries(model);
		
		model.getSummaries().observe(new ListObserver<StudentSummary>() {
			
			@Override
			public void onItemRemoved(int index, StudentSummary item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, StudentSummary item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, StudentSummary item) {
				T.call(this);
				
				StudentSummaryView studentSummaryView = (StudentSummaryView) subViewLoader.createView();
				
				studentSummaryView.displayStudentSummary(item);
				
				view.appendStudentSummary(item, studentSummaryView);
			}
			
			@Override
			public void onDeleted(List<StudentSummary> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<StudentSummary> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<StudentSummary> oldValue, List<StudentSummary> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
