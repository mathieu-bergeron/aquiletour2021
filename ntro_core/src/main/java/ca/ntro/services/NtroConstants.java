package ca.ntro.services;

import java.util.HashMap;
import java.util.Map;

public class NtroConstants {

    private final Map<String, String> hashMap = new HashMap<>();

    public String get(String key) {
        return hashMap.get(key);
    }

    public String put(String key, String value) {
        return hashMap.put(key, value);
    }

    public boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

}
