package ca.ntro.services;

import ca.ntro.backend.BackendError;

public interface ModelLockTask<O extends Object> {

	O execute() throws BackendError;

}
