package ca.aquiletour.server.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.services.Ntro;

// from https://github.com/jetty-project/embedded-jetty-websocket-examples
public class WebSocket extends WebSocketAdapter {
	
    private CountDownLatch closureLatch = new CountDownLatch(1);

    @Override
    public void onWebSocketConnect(Session sess){
        super.onWebSocketConnect(sess);
        
        sess.setIdleTimeout(10*60*1000); // 10 minutes
    }

    @Override
    public void onWebSocketText(String messageText){
        super.onWebSocketText(messageText);
        NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, messageText);

        if(message instanceof NtroRegisterSocketMessage) {
        	
        	NtroRegisterSocketMessage registerSocketSystemMessage = (NtroRegisterSocketMessage) message;
        	RegisteredSockets.registerUserSocket(registerSocketSystemMessage.getUser(), getSession());

        }else{

        	Ntro.backendService().sendMessageToBackend(message);
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason){
        super.onWebSocketClose(statusCode, reason);
        
        RegisteredSockets.deregisterSocket(getSession());

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
