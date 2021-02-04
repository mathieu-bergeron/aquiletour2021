package ca.aquiletour.server.http;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jetty.server.Request;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;
import ca.ntro.web.HtmlWriter;
import ca.ntro.web.NtroWindowWeb;
import ca.ntro.web.RequestHandler;

public class WriteResponseTask extends NtroTaskImpl {

	private NtroWindowWeb window;
	private OutputStream out;
	private Request baseRequest;
	
	public WriteResponseTask(NtroWindowWeb window, Request baseRequest, OutputStream out) {
		this.window = window;
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
		window.writeHtml(builder);

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
