package ca.ntro.web.interactivity;

import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.interactivity.builders.LinkBuilder;
import ca.ntro.web.interactivity.builders.FormBuilder;

public class DomProcessor {

    private final LinkBuilder linkBuilder;
    private final FormBuilder formBuilder;

    public DomProcessor(LinkBuilder linkBuilder, FormBuilder formBuilder) {
        MustNot.beNull(linkBuilder);
        MustNot.beNull(formBuilder);

        this.linkBuilder = linkBuilder;
        this.formBuilder = formBuilder;
    }

    public void runOnElement(HtmlElement rootElement) {
        InteractiveHtmlElement[] linkElements = getMessageElementsMatching(rootElement, "ntro-link");

        for (InteractiveHtmlElement element : linkElements) {
            this.linkBuilder.hookUpLinkElement(element);
        }

        InteractiveHtmlElement[] formElements = getMessageElementsMatching(rootElement, "ntro-form");

        // TODO process forms
    }

    private InteractiveHtmlElement[] getMessageElementsMatching(HtmlElement inElement, String withAttribute) {
        HtmlElements foundElements = inElement.find("*[" + withAttribute + "]");

        if (foundElements == null) {
            return new InteractiveHtmlElement[0];
        }

        InteractiveHtmlElement[] validElements = new InteractiveHtmlElement[foundElements.size()];

        int j = 0;
        for (int i = 0; i < foundElements.size(); i++) {
            HtmlElement candidateElement = foundElements.get(i);
            Class<? extends NtroMessage> messageClass = getMessageClassForElement(candidateElement);

            if (messageClass == null) {
                Log.error("[DomProcessor] Could not get message class for element");
            } else {
                InteractiveHtmlElement interactiveHtmlElement = new InteractiveHtmlElement(candidateElement, messageClass);

                validElements[j] = interactiveHtmlElement;
                j++;
            }
        }

        return validElements;
    }

    private Class<? extends NtroMessage> getMessageClassForElement(HtmlElement element) {
        String messageClassName = element.getAttribute("ntro-message");

        if (messageClassName == null) {
            return null;
        }

        return (Class<? extends NtroMessage>) Ntro.introspector().getClassFromName(messageClassName);
    }

}
