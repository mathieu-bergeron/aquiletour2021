package ca.ntro.jdk.web.interactivity.runtime;

import ca.ntro.services.Ntro;
import ca.ntro.web.interactivity.runtime.InteractivityRuntime;

import def.js.Promise;

import java.util.HashMap;
import java.util.Map;

public class InteractivityRuntimeJdk implements InteractivityRuntime {

    private final Map<String, V8RuntimeWrapper> v8RuntimesPerThread = new HashMap<>();

    @Override
    public String evaluateExpression(String expression) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        if (runtime == null) {
            runtime = createRuntimeForCurrentThread();
        }

        return runtime.getEngine().executeStringScript(expression);
    }

    @Override
    public Promise<String> evaluateExpressionAsync(String expression) {
        throw new IllegalStateException("Only synchronous expression evaluation is supported in JDK");
    }

    @Override
    public void addElementToNtroObject(String key, int value) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        runtime.addNtroObjectValue(key, value);
    }

    @Override
    public void addElementToNtroObject(String key, float value) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        runtime.addNtroObjectValue(key, value);
    }

    @Override
    public void addElementToNtroObject(String key, double value) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        runtime.addNtroObjectValue(key, value);
    }

    @Override
    public void addElementToNtroObject(String key, boolean value) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        runtime.addNtroObjectValue(key, value);
    }

    @Override
    public void addElementToNtroObject(String key, String value) {
        V8RuntimeWrapper runtime = runtimeForCurrentThread();

        runtime.addNtroObjectValue(key, value);
    }

    private V8RuntimeWrapper runtimeForCurrentThread() {
        return v8RuntimesPerThread.get(Ntro.threadService().currentThread().getThreadId());
    }

    private V8RuntimeWrapper createRuntimeForCurrentThread() {
        V8RuntimeWrapper runtime = new V8RuntimeWrapper();

        v8RuntimesPerThread.put(Ntro.threadService().currentThread().getThreadId(), runtime);

        return runtime;
    }

    public void releaseAllRuntimes() {
        for (Map.Entry<String, V8RuntimeWrapper> entry : v8RuntimesPerThread.entrySet()) {
            v8RuntimesPerThread.remove(entry.getKey());

            System.out.println("[InteractivityRuntime] Released V8 runtime for thread " + entry.getKey());

            entry.getValue().release();
        }
    }

}
