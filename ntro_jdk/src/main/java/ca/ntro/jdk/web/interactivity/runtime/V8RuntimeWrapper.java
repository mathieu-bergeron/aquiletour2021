package ca.ntro.jdk.web.interactivity.runtime;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

public class V8RuntimeWrapper {

    private V8       runtime;
    private V8Object ntroObject;

    public V8RuntimeWrapper() {
        runtime = V8.createV8Runtime();

        ntroObject = new V8Object(runtime);

        runtime.add("Ntro", ntroObject);
    }

    public V8 getEngine() {
        return runtime;
    }

    public void addNtroObjectValue(String key, int value) {
        ntroObject.add(key, value);
    }

    public void addNtroObjectValue(String key, float value) {
        ntroObject.add(key, value);
    }

    public void addNtroObjectValue(String key, double value) {
        ntroObject.add(key, value);
    }

    public void addNtroObjectValue(String key, boolean value) {
        ntroObject.add(key, value);
    }

    public void addNtroObjectValue(String key, String value) {
        ntroObject.add(key, value);
    }

    public void release() {
        this.runtime.release();
        this.ntroObject.release();
    }

}
