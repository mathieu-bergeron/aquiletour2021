package ca.aquiletour.server.http;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jetty.server.Request;

import ca.aquiletour.web.HtmlWriterTask;
import ca.aquiletour.web.RequestHandlerTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskImpl;

public class WriteResponseTask extends NtroTaskImpl {

	private OutputStream out;
	private Request baseRequest;
	
	public WriteResponseTask(Request baseRequest, OutputStream out) {
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
		getSubTask(HtmlWriterTask.class, "RootController").writeHtml(builder);

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
