package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.root.AiguilleurRootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class QueueController extends NtroController<AiguilleurRootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		// empty model loader until the ShowQueue message
		setModelLoader(new EmptyModelLoader());

		setViewLoader(viewClass(), currentContext().lang());

		installParentViewMessageHandler();

	}
	
	protected abstract Class<? extends QueueView> viewClass();
	protected abstract void installParentViewMessageHandler();
	
	
	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		T.call(this);
		
//		 TODO: we can automatize this!
//				      simply reset the tasks with the new lang
				if(!previousContext.hasSameLang(currentContext())) {
					setViewLoader(viewClass(), currentContext().lang());
					addSubViewLoader(AppointmentView.class, currentContext().lang());
				}
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
