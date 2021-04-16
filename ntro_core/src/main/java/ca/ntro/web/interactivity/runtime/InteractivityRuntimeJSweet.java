package ca.ntro.web.interactivity.runtime;

import def.dom.Worker;
import def.js.JSON;
import def.js.Promise;

import java.util.function.Consumer;

public class InteractivityRuntimeJSweet implements InteractivityRuntime {

    Worker worker = new Worker("/_R/js/script_worker.js");

    @Override
    public String evaluateExpression(String expression) {
        throw new IllegalStateException("Only async expression evaluation is supported in JSweet");
    }

    @Override
    public Promise<String> evaluateExpressionAsync(String expression) {
        return new Promise<>((Consumer<String> resolve, Consumer<Object> reject) -> {
            worker.addEventListener("message", message -> {
                resolve.accept(JSON.stringify(message));
            });

            worker.postMessage("test");
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
