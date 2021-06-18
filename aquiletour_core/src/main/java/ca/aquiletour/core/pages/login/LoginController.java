package ca.aquiletour.core.pages.login;



import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class LoginController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?,?> context) {
		T.call(this);

		setViewLoader(LoginView.class, "fr");
		
		addParentViewMessageHandler(ShowLoginMessage.class, new ShowLoginHandler());
	}

	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);

		LoginView view = (LoginView) getView();
		
		if(view != null) {
			view.hideLoginMessage();
			view.selectLoginStep(context);
		}
	}


	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
