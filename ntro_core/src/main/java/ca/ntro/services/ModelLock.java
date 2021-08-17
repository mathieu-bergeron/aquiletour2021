package ca.ntro.services;

public class ModelLock {
	
	private static long nextLockId = 0;

	public static ModelLock newLock() {
		ModelLock modelLock = new ModelLock(nextLockId);
		
		nextLockId++;
		
		return modelLock;
	}

	private final long lockId;

	private ModelLock(long lockId) {
		this.lockId = lockId;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null) return false;
		if(other instanceof ModelLock) {
			return lockId == ((ModelLock) other).lockId;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) (lockId % 2147483647);
	}
}
