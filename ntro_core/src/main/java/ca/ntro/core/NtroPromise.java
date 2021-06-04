package ca.ntro.core;

import java.util.function.Consumer;
import java.util.function.Function;

public interface NtroPromise<T> {

    <A> NtroPromise<A> then(Function<T, A> accept);

    NtroPromise<Void> then(Consumer<T> accept);

    <A> NtroPromise<A> then(Function<T, A> acceptNext, Function<Object, A> acceptReject);

    NtroPromise<Void> then(Consumer<T> acceptNext, Consumer<Object> acceptReject);

}
