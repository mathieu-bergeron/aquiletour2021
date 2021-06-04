package ca.ntro.core;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface NtroPromiseProvider {

    <T> NtroPromise<T> provide(BiConsumer<Consumer<T>, Consumer<Object>> consumer);

}
