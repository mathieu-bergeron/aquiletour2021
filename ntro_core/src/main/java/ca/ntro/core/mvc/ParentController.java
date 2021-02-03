package ca.ntro.core.mvc;

import ca.ntro.core.system.trace.T;

public class ParentController {
	
	private NtroView view;

	public ParentController(NtroView view) {
		T.call(this);
		this.view = view;
	}
	
	public NtroView getView() {
		T.call(this);

		return view;
	}
	
	

}
