package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.backend.BackendError;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;

public class ModelLocks {

	private static Map<DocumentPath, ModelLock> modelLockByPath = null;
	private static Map<String, DocumentPath> currentPathByThreadId = null;
	
	public static void initialize() {
		if(modelLockByPath == null) {
			modelLockByPath = Ntro.collections().concurrentMap(new HashMap<>());
		}

		if(currentPathByThreadId == null) {
			currentPathByThreadId = Ntro.collections().concurrentMap(new HashMap<>());
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
				currentPathByThreadId.put(Ntro.threadService().currentThread().threadId(), documentPath);
			}
		}
		
		return modelLock;
	}
	
	public static <O extends Object> O acquireLockAndExecute(DocumentPath documentPath, ModelLockTask<O> task) throws BackendError {
		T.call(ModelLocks.class);

		O result = null;

		DocumentPath currentPath = currentPathByThreadId.get(Ntro.threadService().currentThread().threadId());
		
		if(currentPath != null && !currentPath.equals(documentPath)) {

			Log.warning("[acquireLockAndExecute] trying to get TWO LOCKS: " + currentPath + " and "  + documentPath);
		}

		/*
		Log.info("<locks>");
		for(String threadId : currentPathByThreadId.keySet()) {
			Log.info(threadId + " " + currentPathByThreadId.get(threadId) );
		}
		Log.info(Ntro.threadService().currentThread().threadId() + " is trying for " + documentPath);
		Log.info("</locks>");
		*/
		
		ModelLock modelLock = getModelLock(documentPath);

		synchronized (modelLock) {
			// XXX: it is possible for the lock
			//      to be superseded (or destroyed)
			//      after
			//      modelLock = getModelLock(...)
			//      but before
			//      synchronized (modelLock){...
			if(isLockStillValid(documentPath, modelLock)) {
				
				//Log.info("[ModelLocks] lock AQUIRED " + documentPath);
				
				currentPathByThreadId.put(Ntro.threadService().currentThread().threadId(), documentPath);

				result = task.execute();
				
			}else {
				
				Log.warning("[aquireLockAndExecute] invalid lock for " + documentPath.toString() + " . Retrying... ");
				result = acquireLockAndExecute(documentPath, task);
			}
		}

		currentPathByThreadId.remove(Ntro.threadService().currentThread().threadId());

		//Log.info("[ModelLocks] lock RELEASED " + documentPath);
		
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
		
		Ntro.collections().removeByKeyEquals(modelLockByPath, documentPath);
		Ntro.collections().removeByKeyEquals(currentPathByThreadId, Ntro.threadService().currentThread().threadId());
	}
}
