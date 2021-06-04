package ca.aquiletour.core.pages.git.commit_list.handlers;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
import ca.aquiletour.core.pages.git.commit_list.views.CommitView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.Path;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelSubModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class CommitListViewModel extends ModelSubModelViewSubViewHandler<CommitListModel, CourseModelStudent, CommitListView>  {
	
	@Override
	protected void handle(CommitListModel model, CourseModelStudent courseModel, CommitListView view, ViewLoader subViewLoader) {
		T.call(this);
		
		long deadline = findExerciseDeadline(courseModel, model.getExercisePath());
		deadline = 981133000000L;
		if(deadline != -1) {
			view.displayCommitList(model, deadline);
		}
		
		model.getCommits().observe(new ListObserver<Commit>() {
			
			@Override
			public void onItemRemoved(int index, Commit item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, Commit item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, Commit item) {
				T.call(this);

				CommitView commitView = (CommitView) subViewLoader.createView();
				
				commitView.displayCommit(item);
				
				view.appendCommit(item, commitView);
			}
			
			@Override
			public void onDeleted(List<Commit> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<Commit> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<Commit> oldValue, List<Commit> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private long findExerciseDeadline(CourseModelStudent courseModel, String exercisePath) {
		T.call(this);
		
		long deadline = -1;

		Path path = Path.fromRawPath(exercisePath);
		Task task = courseModel.findTaskByPath(path);
		
		if(task != null) {

			// FIXME: has to involve student schedule
			//deadline = task.getEndTime().getValue().getCalendarDate().getEpochSeconds();
			
		}else {
			
			Log.warning("\nTask not found " + exercisePath);
			
		}

		return deadline;
	}
}
