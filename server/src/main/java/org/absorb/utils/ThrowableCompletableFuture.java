package org.absorb.utils;

import org.absorb.utils.lamda.function.ThrowableFunction;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class ThrowableCompletableFuture<V, T extends Throwable> extends CompletableFuture<V> {

    private final @NotNull Class<T> clazz;
    private @NotNull ThrowableFunction<T, V, ? extends Throwable> onThrow = (ex) -> {
        throw ex;
    };

    public ThrowableCompletableFuture(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    public ThrowableCompletableFuture<V, T> setOnThrow(@NotNull ThrowableFunction<T, V, ? extends Throwable> onThrow) {
        this.onThrow = onThrow;
        return this;
    }

    public @NotNull ThrowableFunction<T, V, ? extends Throwable> onThrow() {
        return this.onThrow;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        try {
            return super.get();
        } catch (CompletionException e) {
            return process(e);
        }
    }

    private V process(CompletionException e) {
        var cause = e.getCause();
        if (!this.clazz.isInstance(cause)) {
            throw e;
        }
        try {
            return this.onThrow.apply((T) cause);
        } catch (Throwable ex) {
            throw new CompletionException(ex);
        }
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            return super.get(timeout, unit);
        } catch (CompletionException e) {
            return process(e);
        }
    }

    @Override
    public V join() {
        try {
            return super.join();
        } catch (CompletionException e) {
            return process(e);
        }
    }

    @Override
    public V getNow(V valueIfAbsent) {
        try {
            return super.getNow(valueIfAbsent);
        } catch (CompletionException e) {
            return process(e);
        }
    }
}
