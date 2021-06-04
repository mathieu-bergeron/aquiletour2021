package ca.ntro.jdk;

import ca.ntro.core.NtroPromise;
import ca.ntro.core.NtroPromiseProvider;

import org.riversun.promise.Promise;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NtroPromiseJdk<T> implements NtroPromise<T> {

    Promise jdkPromise;

    public NtroPromiseJdk(BiConsumer<Consumer<T>, Consumer<Object>> consumer) {
        jdkPromise = Promise.resolve()
                .then((action, data) -> {
                    consumer.accept(action::resolve, action::reject);
                }).start();
    }

    public NtroPromiseJdk(Promise from) {
        jdkPromise = from;
    }

    @Override
    public <A> NtroPromise<A> then(Function<T, A> accept) {
        return new NtroPromiseJdk<>(jdkPromise.then((action, data) -> {
            action.resolve(accept.apply((T) data));
        }));
    }

    @Override
    public NtroPromise<Void> then(Consumer<T> accept) {
        return new NtroPromiseJdk<>(jdkPromise.then((action, data) -> {
            action.resolve(accept);
        }));
    }

    @Override
    public <A> NtroPromise<A> then(Function<T, A> acceptNext, Function<Object, A> acceptReject) {
        return new NtroPromiseJdk<>(jdkPromise.then((action, data) -> {
            action.resolve(acceptNext.apply((T) data));
        }, ((action, data) -> {
            action.resolve(acceptReject.apply(data));
        })));
    }

    @Override
    public NtroPromise<Void> then(Consumer<T> acceptNext, Consumer<Object> acceptReject) {
        return new NtroPromiseJdk<>(jdkPromise.then((action, data) -> {
            action.resolve(acceptNext);
        }, ((action, data) -> {
            acceptReject.accept(data);
            action.resolve();
        })));
    }

    public static class Provider implements NtroPromiseProvider {

        @Override
        public <Ty> NtroPromise<Ty> provide(BiConsumer<Consumer<Ty>, Consumer<Object>> consumer) {
            return new NtroPromiseJdk<>(consumer);
        }

    }
}
