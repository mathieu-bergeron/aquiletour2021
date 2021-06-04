package ca.ntro.web.interactivity.runtime;

import ca.ntro.core.NtroPromise;
import ca.ntro.services.Ntro;
import def.dom.Worker;
import def.js.Object;

public class InteractivityRuntimeJSweet implements InteractivityRuntime {

    Worker worker = new Worker("/_resources/js/script_worker.js");

    @Override
    public NtroPromise<String> evaluateExpression(String expression) {
        return Ntro.promise((resolve, reject) -> {
            worker.addEventListener("message", (event) -> {
                Object data = event.$get("data");

                resolve.accept(data.$get("result"));
            });

            Object message = new Object();
            message.$set("command", expression);

            worker.postMessage(message);
        });
    }

    @Override
    public void addElementToNtroObject(String key, int value) {
        throw new IllegalStateException("NYI");
    }

    @Override
    public void addElementToNtroObject(String key, float value) {
        throw new IllegalStateException("NYI");
    }

    @Override
    public void addElementToNtroObject(String key, double value) {
        throw new IllegalStateException("NYI");
    }

    @Override
    public void addElementToNtroObject(String key, boolean value) {
        throw new IllegalStateException("NYI");
    }

    @Override
    public void addElementToNtroObject(String key, String value) {
        throw new IllegalStateException("NYI");
    }

}
