package ca.ntro.web.interactivity;

import ca.ntro.core.system.log.Log;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

import static def.js.Globals.undefined;

public class DomProcessor {

    public static void processDom(HtmlElement rootElement) {
        InteractiveHtmlElement[] linkElements = getMessageElementsMatching(rootElement, "ntro-link");

        for (InteractiveHtmlElement element : linkElements) {
            LinkBuilder.hookUpLinkElement(element);
        }

        InteractiveHtmlElement[] formElements = getMessageElementsMatching(rootElement, "ntro-form");

        // TODO process forms
    }

    private static InteractiveHtmlElement[] getMessageElementsMatching(HtmlElement inElement, String withAttribute) {
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
                Log.fatalError("[DomProcessor] Could not get message class for element");
                throw new RuntimeException();
            } else {
                InteractiveHtmlElement interactiveHtmlElement = new InteractiveHtmlElement(candidateElement, messageClass);

                validElements[j] = interactiveHtmlElement;
                j++;
            }
        }

        return validElements;
    }

    private static Class<? extends NtroMessage> getMessageClassForElement(HtmlElement element) {
        String messageClassName = element.getAttribute("ntro-message");

        if (messageClassName == undefined) {
            return null;
        }

        return (Class<? extends NtroMessage>) Ntro.introspector().getClassFromName(messageClassName);
    }

}
