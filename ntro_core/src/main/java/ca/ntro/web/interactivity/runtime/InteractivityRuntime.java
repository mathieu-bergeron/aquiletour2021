package ca.ntro.web.interactivity.runtime;

import ca.ntro.core.NtroPromise;

import def.js.Promise;

public interface InteractivityRuntime {

    NtroPromise<String> evaluateExpression(String expression);

    void addElementToNtroObject(String key, int value);
    void addElementToNtroObject(String key, float value);
    void addElementToNtroObject(String key, double value);
    void addElementToNtroObject(String key, boolean value);
    void addElementToNtroObject(String key, String value);

}
