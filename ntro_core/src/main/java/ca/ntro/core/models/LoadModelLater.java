package ca.ntro.core.models;

import ca.ntro.core.system.trace.T;

public class LoadModelLater extends ModelLoader {

	public LoadModelLater() {
		super(null);
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		// Never load. This task is meant
		// to be replaced by an actual model
		// loader
	}


}
