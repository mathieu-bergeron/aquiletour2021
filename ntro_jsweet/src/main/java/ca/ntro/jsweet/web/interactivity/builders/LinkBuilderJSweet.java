package ca.ntro.jsweet.web.interactivity.builders;

import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.interactivity.InteractiveHtmlElement;
import ca.ntro.web.interactivity.builders.LinkBuilder;

public class LinkBuilderJSweet implements LinkBuilder {

    @Override
    public void hookUpLinkElement(InteractiveHtmlElement linkElement) {
        HtmlEventListener eventListener = () -> {
            Ntro.messages().send(Ntro.messages().create(linkElement.getMessageClass()));
        };

        linkElement.getHtmlElement().addEventListener("click", eventListener);
    }

}
