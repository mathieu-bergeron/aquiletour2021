package ca.aquiletour.server.http;

import javax.servlet.ServletException;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.NativeWebSocketServletContainerInitializer;
import org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter;

import ca.ntro.core.Constants;

// from  https://github.com/jetty-project/embedded-jetty-websocket-examples
public class WebSocketHandler extends ServletContextHandler {

	public static ContextHandler createWebSocketHandler(String contextPath) throws ServletException {
		
		WebSocketHandler handler = new WebSocketHandler();
		handler.setContextPath(contextPath);
		
        // Configure specific websocket behavior
        NativeWebSocketServletContainerInitializer.configure(handler,
            (servletContext, nativeWebSocketConfiguration) ->
            {
                // Configure default max size
                nativeWebSocketConfiguration.getPolicy().setMaxTextMessageBufferSize(65535);
                
                // Add websockets
                nativeWebSocketConfiguration.addMapping("/" + Constants.SOCKET_URL_SEGMENT, WebSocket.class);
                
                //nativeWebSocketConfiguration.setStopTimeout(10*60*1000); // 10 minutes
            });

        // Add generic filter that will accept WebSocket upgrade.
        WebSocketUpgradeFilter.configure(handler);
        
        
		
		return handler;
	}

}
