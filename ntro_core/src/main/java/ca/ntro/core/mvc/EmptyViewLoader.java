package ca.ntro.core.mvc;

public class EmptyViewLoader extends ViewLoader {

	@Override
	protected NtroView createViewImpl() {
		throw new RuntimeException("should not be called!");
	}

	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		
		// empty view loader: 
		// never finishes
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
