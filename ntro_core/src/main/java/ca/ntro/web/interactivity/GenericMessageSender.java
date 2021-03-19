package ca.ntro.web.interactivity;

import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class GenericMessageSender {

    public static void send(Class<? extends NtroMessage> messageClass) {
        NtroMessage messageInstance = Ntro.messages().create(messageClass);;

        Ntro.messages().send(messageInstance);
    }

}
