package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.OnExerciseCompleted;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_exercice.GitExerciseTask;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class OnExerciseCompletedHandler extends BackendMessageHandler<OnExerciseCompleted> {

	@Override
	public void handleNow(ModelStoreSync modelStore, OnExerciseCompleted message) throws BackendError {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnExerciseCompleted message) throws BackendError {
		T.call(this);

		GitExerciseCompletion gitExerciseCompletion = new GitExerciseCompletion();
		
		CourseManager.updateAtomicTaskCompletionStudent(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        AtomicTask.idFromType(GitExerciseTask.class),
				                                        gitExerciseCompletion);

		CourseManager.updateAtomicTaskCompletionTeacher(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        AtomicTask.idFromType(GitExerciseTask.class),
				                                        gitExerciseCompletion);
	}
}
