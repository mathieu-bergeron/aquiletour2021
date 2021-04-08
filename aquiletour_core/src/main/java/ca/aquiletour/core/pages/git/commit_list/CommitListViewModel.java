package ca.aquiletour.core.pages.git.commit_list;

import java.util.List;

import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.Path;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelSubModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class CommitListViewModel extends ModelSubModelViewSubViewHandler<CommitListModel, CourseModel, CommitListView>  {
	
	@Override
	protected void handle(CommitListModel model, CourseModel courseModel, CommitListView view, ViewLoader subViewLoader) {
		T.call(this);
		
		long deadline = findExerciseDeadline(courseModel, model.getExercisePath());
		System.out.println(deadline);

		view.displayCommitList(model);
		
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
	
	private long findExerciseDeadline(CourseModel courseModel, String exercisePath) {
		T.call(this);
		
		long deadline = -1;

		Path path = new Path(exercisePath);
		Task task = courseModel.findTaskByPath(path);
		
		if(task != null) {

			deadline = task.getEndTime();
			
		}else {
			
			Log.warning("\nTask not found " + exercisePath);
			
		}

		return deadline;
	}
}
