package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.backend.BackendError;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;

public class ModelLocks {

	private static Map<DocumentPath, ModelLock> modelLockByPath = null;
	
	public static void initialize() {
		if(modelLockByPath == null) {
			modelLockByPath = Ntro.collections().concurrentMap(new HashMap<>());
		}
	}

	private static ModelLock getModelLock(DocumentPath documentPath) {
		T.call(ModelLocks.class);
		
		ModelLock modelLock = null;
		
		synchronized (modelLockByPath) {
			modelLock = Ntro.collections().getByKeyEquals(modelLockByPath, documentPath);
			if(modelLock == null) {
				modelLock = ModelLock.newLock();
				modelLockByPath.put(documentPath, modelLock);
			}
		}
		
		return modelLock;
	}
	
	public static <O extends Object> O acquireLockAndExecute(DocumentPath documentPath, ModelLockTask<O> task) throws BackendError {
		T.call(ModelLocks.class);

		O result = null;
		
		ModelLock modelLock = getModelLock(documentPath);

		synchronized (modelLock) {

			// XXX: it is very unlikely, but still possible
			//      for the lock to be destroyed after
			//      modelLock = getModelLock(...)
			//      but before
			//      synchronized (modelLock){...
			if(isLockStillValid(documentPath, modelLock)) {
				
				result = task.execute();
				
			}else {
				
				Log.warning("[aquireLockAndExecute] invalid lock for " + documentPath.toString() + " . Retrying... ");
				result = acquireLockAndExecute(documentPath, task);
			}
		}
		
		return result;
	}

	private static boolean isLockStillValid(DocumentPath documentPath, ModelLock candidateLock) {
		T.call(ModelLocks.class);
		
		boolean isValid = false;
		
		ModelLock currentLock = Ntro.collections().getByKeyEquals(modelLockByPath, documentPath);
		
		if(candidateLock == currentLock) {
			
			isValid = true;
		}
		
		return isValid;
	}

	public static void destroyLock(DocumentPath documentPath) throws BackendError {
		T.call(ModelLocks.class);
		
		acquireLockAndExecute(documentPath, new ModelLockTask<Void>() {
			@Override
			public Void execute() {

				Ntro.collections().removeByKeyEquals(modelLockByPath, documentPath);

				return null;
			}
		});
	}
}
