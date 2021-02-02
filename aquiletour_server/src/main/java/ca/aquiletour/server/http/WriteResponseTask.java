package ca.aquiletour.server.http;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jetty.server.Request;

import ca.aquiletour.server.pages.root.RootControllerServer;
import ca.aquiletour.web.pages.root.RootControllerWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.web.HtmlWriter;
import ca.ntro.web.RequestHandler;

public class WriteResponseTask extends NtroTaskImpl {

	private RootControllerServer rootController;
	private OutputStream out;
	private Request baseRequest;
	
	public WriteResponseTask(RootControllerServer rootController, Request baseRequest, OutputStream out) {
		this.rootController = rootController;
		this.baseRequest = baseRequest;
		this.out = out;
	}
	
	@Override
	protected void initializeTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		rootController.writeHtml(builder);

		try {

			out.write(builder.toString().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		baseRequest.setHandled(true);
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
