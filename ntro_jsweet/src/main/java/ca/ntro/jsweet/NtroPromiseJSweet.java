package ca.ntro.jsweet;

import ca.ntro.core.NtroPromise;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import ca.ntro.core.NtroPromiseProvider;

import def.js.Promise;
import def.js.PromiseLike;

public class NtroPromiseJSweet<T> implements NtroPromise<T> {

    Promise<T> jsPromise;

    public NtroPromiseJSweet(BiConsumer<Consumer<T>, Consumer<Object>> consumer) {
        jsPromise = new Promise<>(consumer);
    }

    public NtroPromiseJSweet(Promise<T> from) {
        jsPromise = from;
    }

    @Override
    public <A> NtroPromise<A> then(Function<T, A> accept) {
        return new NtroPromiseJSweet<>(jsPromise.then(accept));
    }

    @Override
    public NtroPromise<Void> then(Consumer<T> accept) {
        return new NtroPromiseJSweet<>(jsPromise.then(accept));
    }

    @Override
    public <A> NtroPromise<A> then(Function<T, A> acceptNext, Function<Object, A> acceptReject) {
        return new NtroPromiseJSweet<>(jsPromise.then(acceptNext).catchAsync(o -> {
            return new NtroPromiseJSweet<A>((resolve, reject) -> {
                resolve.accept(acceptReject.apply(o));
            }).jsPromise;
        }));
    }

    @Override
    public NtroPromise<Void> then(Consumer<T> acceptNext, Consumer<Object> acceptReject) {
        return new NtroPromiseJSweet<>(jsPromise.then(acceptNext).catchAsync(o -> {
            acceptReject.accept(o);

            return Promise.resolve();
        }));
    }

    public static class Provider implements NtroPromiseProvider {

        @Override
        public <Ty> NtroPromise<Ty> provide(BiConsumer<Consumer<Ty>, Consumer<Object>> consumer) {
            return new NtroPromiseJSweet<>(consumer);
        }

    }

}
