package ca.aquiletour.server.http;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jetty.server.Request;

import ca.aquiletour.web.HandlerTask;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;

public class WriteResponseTask extends NtroTask {
	
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
	protected void runTask() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		getPreviousTask(HandlerTask.class).writeHtml(builder);
		
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
