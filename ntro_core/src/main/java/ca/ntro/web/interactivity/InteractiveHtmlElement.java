package ca.ntro.web.interactivity;

import ca.ntro.messages.NtroMessage;
import ca.ntro.web.dom.HtmlElement;

/**
 * Wrapper around {@link ca.ntro.web.dom.HtmlElement} which contains interactivity information
 */
public class InteractiveHtmlElement {

    private HtmlElement htmlElement;
    Class<? extends NtroMessage> messageClass;

    public InteractiveHtmlElement(HtmlElement htmlElement, Class<? extends NtroMessage> messageClass) {
        this.htmlElement = htmlElement;
        this.messageClass = messageClass;
    }

    public HtmlElement getHtmlElement() {
        return htmlElement;
    }

    public Class<? extends NtroMessage> getMessageClass() {
        return messageClass;
    }

}
