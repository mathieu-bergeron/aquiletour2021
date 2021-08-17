package ca.ntro.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.backend.BackendError;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.stores.DocumentPath;

public class ModelLocks {

	private static Map<DocumentPath, ModelLock> modelLockByPath = null;
	private static Map<String, List<DocumentPath>> currentPathsByThreadId = null;
	
	public static void initialize() {
		if(modelLockByPath == null) {
			modelLockByPath = Ntro.collections().concurrentMap(new HashMap<>());
		}

		if(currentPathsByThreadId == null) {
			currentPathsByThreadId = Ntro.collections().concurrentMap(new HashMap<>());
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
		
		List<DocumentPath> currentPaths = currentPathsByThreadId.get(Ntro.threadService().currentThread().threadId());
		if(currentPaths == null) {
			currentPaths = Ntro.collections().synchronizedList(new ArrayList<>());
			currentPathsByThreadId.put(Ntro.threadService().currentThread().threadId(), currentPaths);
		}
		
		synchronized (currentPaths) {
			for(DocumentPath currentPath : currentPaths) {
				if(!currentPath.equals(documentPath)) {
					Log.warning("\n\n[acquireLockAndExecute] trying to get TWO LOCKS: " + currentPath + " and " + documentPath + "\n\n");
					break;
				}
			}
		}

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
				
				currentPaths.add(documentPath);

				result = task.execute();

				currentPaths.remove(documentPath);
				
			}else {
				
				Log.warning("[aquireLockAndExecute] invalid lock for " + documentPath.toString() + " . Retrying... ");
				result = acquireLockAndExecute(documentPath, task);
			}
		}

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
		Ntro.collections().removeByKeyEquals(currentPathsByThreadId, Ntro.threadService().currentThread().threadId());
	}
}
