package ca.aquiletour.server.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.ntro.core.Ntro;
import ca.ntro.core.NtroUser;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;


// from https://github.com/jetty-project/embedded-jetty-websocket-examples
public class WebSocket extends WebSocketAdapter {
	
    private CountDownLatch closureLatch = new CountDownLatch(1);

    @Override
    public void onWebSocketConnect(Session sess){
        super.onWebSocketConnect(sess);

        System.out.println("Socket Connected: " + sess);
    }

    @Override
    public void onWebSocketText(String messageText){
        super.onWebSocketText(messageText);
        
        NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, messageText);
        
        if(message instanceof AddCourseMessage) {
        	AddCourseMessage addCourseMessage = (AddCourseMessage) message;
        	
        	Teacher alice = new Teacher();
        	alice.setId("alice");
        	alice.setAuthToken("aliceToken");
        	addCourseMessage.setUser(alice);
        	
        	Ntro.backendService().sendMessageToBackend(addCourseMessage);
        }


    }

    @Override
    public void onWebSocketClose(int statusCode, String reason){
        super.onWebSocketClose(statusCode, reason);

        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
        closureLatch.countDown();
    }

    @Override
    public void onWebSocketError(Throwable cause){
        super.onWebSocketError(cause);

        cause.printStackTrace(System.err);
    }

    public void awaitClosure() throws InterruptedException{
        System.out.println("Awaiting closure from remote");

        closureLatch.await();
    }
}
