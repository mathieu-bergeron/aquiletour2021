package ca.aquiletour.server.http;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.core.Ntro;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.messages.NtroMessage;
import ca.ntro.messages.ntro_messages.RegisterSocketNtroMessage;

// from https://github.com/jetty-project/embedded-jetty-websocket-examples
public class WebSocket extends WebSocketAdapter {
	
    private CountDownLatch closureLatch = new CountDownLatch(1);

    @Override
    public void onWebSocketConnect(Session sess){
        super.onWebSocketConnect(sess);
    }

    @Override
    public void onWebSocketText(String messageText){
        super.onWebSocketText(messageText);
        
        NtroMessage message = Ntro.jsonService().fromString(NtroMessage.class, messageText);

        if(message instanceof RegisterSocketNtroMessage) {
        	
        	RegisterSocketNtroMessage registerSocketSystemMessage = (RegisterSocketNtroMessage) message;
        	RegisteredSockets.registerUserSocket(registerSocketSystemMessage.getUser(), getSession());

        }else if(message instanceof AddCourseMessage) {

        	AddCourseMessage addCourseMessage = (AddCourseMessage) message;
        	User fromUser = addCourseMessage.getUser();

        	Ntro.backendService().sendMessageToBackend(addCourseMessage);

        	ModelLoader modelLoader = LocalStore.getLoader(DashboardModel.class, fromUser.getAuthToken(), fromUser.getId());
        	modelLoader.execute();
        	
        	DashboardModel dashboardModel = (DashboardModel) modelLoader.getModel();

        	/*
			if(dashboardModel != null) {
				try {
					
					DocumentPath documentPath = new DocumentPath(Ntro.introspector().getSimpleNameForClass(DashboardModel.class), fromUser.getId());
					
					UpdateModelMessage updateModelMessage = new UpdateModelMessage(documentPath, dashboardModel);
					this.getRemote().sendString(Ntro.jsonService().toString(updateModelMessage));
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
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
