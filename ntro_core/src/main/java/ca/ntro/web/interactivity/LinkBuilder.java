package ca.ntro.web.interactivity;

import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlEventListener;

public class LinkBuilder {

    public static void hookUpLinkElement(InteractiveHtmlElement linkElement) {
        HtmlEventListener eventListener = () -> {
            Ntro.messages().send(Ntro.messages().create(linkElement.messageClass));
        };

        linkElement.getHtmlElement().addEventListener("click", eventListener);
    }

}
