package ca.aquiletour.core.pages.queue;

import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class QueueController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		// empty model loader until the ShowQueue message
		setModelLoader(new EmptyModelLoader());

		setViewLoader(viewClass(), context().lang());

		installParentViewMessageHandler();

	}
	
	protected abstract Class<? extends QueueView> viewClass();
	protected abstract void installParentViewMessageHandler();
	
	
	@Override
	protected void onChangeContext(NtroContext<?> previousContext, NtroContext<?> context) {
		T.call(this);
		
//		 TODO: we can automatize this!
//				      simply reset the tasks with the new lang
				if(!previousContext.hasSameLang(context())) {
					setViewLoader(viewClass(), context().lang());
					addSubViewLoader(AppointmentView.class, context().lang());
				}
		
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}


}
