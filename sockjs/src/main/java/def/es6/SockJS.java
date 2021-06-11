package def.es6;

import def.dom.CloseEvent;
import def.dom.ErrorEvent;
import def.dom.Event;
import def.dom.EventListener;
import def.dom.EventListenerObject;
import def.dom.EventTarget;
import def.dom.MessageEvent;

public class SockJS extends EventTarget {
	
	// based on: https://github.com/cincheo/jsweet/blob/develop/core-lib/es6/src/main/java/def/dom/WebSocket.java
	
	// TODO: remove fields/methods that are in the WebSocket API but not in the SockJS API

    public SockJS(java.lang.String url){}
    public SockJS(java.lang.String url, java.lang.String protocols){}

    public java.util.function.Function<CloseEvent,java.lang.Object> onclose;
    public java.util.function.Function<Event,java.lang.Object> onerror;
    public java.util.function.Function<MessageEvent,java.lang.Object> onmessage;
    public java.util.function.Function<Event,java.lang.Object> onopen;
    native public void send(java.lang.Object data);
	
	/*
	
    public java.lang.String binaryType;
    public double bufferedAmount;
    public java.lang.String extensions;
    public java.lang.String protocol;
    public double readyState;
    public java.lang.String url;
    native public void close(double code, java.lang.String reason);
    public double CLOSED;
    public double CLOSING;
    public double CONNECTING;
    public double OPEN;
    native public void addEventListener(jsweet.util.StringTypes.close type, java.util.function.Function<CloseEvent,java.lang.Object> listener, java.lang.Boolean useCapture);
    native public void addEventListener(jsweet.util.StringTypes.error type, java.util.function.Function<ErrorEvent,java.lang.Object> listener, java.lang.Boolean useCapture);
    native public void addEventListener(jsweet.util.StringTypes.message type, java.util.function.Function<MessageEvent,java.lang.Object> listener, java.lang.Boolean useCapture);
    native public void addEventListener(jsweet.util.StringTypes.open type, java.util.function.Function<Event,java.lang.Object> listener, java.lang.Boolean useCapture);
    native public void addEventListener(java.lang.String type, EventListener listener, java.lang.Boolean useCapture);

    public static SockJS prototype;

    public SockJS(java.lang.String url, java.lang.String protocols){}
    public SockJS(java.lang.String url, java.lang.Object protocols){}

    native public void close(double code);
    native public void close();
    native public void addEventListener(jsweet.util.StringTypes.close type, java.util.function.Function<CloseEvent,java.lang.Object> listener);
    native public void addEventListener(jsweet.util.StringTypes.error type, java.util.function.Function<ErrorEvent,java.lang.Object> listener);
    native public void addEventListener(jsweet.util.StringTypes.message type, java.util.function.Function<MessageEvent,java.lang.Object> listener);
    native public void addEventListener(jsweet.util.StringTypes.open type, java.util.function.Function<Event,java.lang.Object> listener);
    native public void addEventListener(java.lang.String type, EventListener listener);
    native public void addEventListener(java.lang.String type, EventListenerObject listener, java.lang.Boolean useCapture);
    native public void addEventListener(java.lang.String type, EventListenerObject listener);

    protected SockJS(){}
    */

}
