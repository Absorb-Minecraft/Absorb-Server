package org.absorb.utils.lamda.consumer;

public interface ThrowableConsumer<V, T extends Throwable> {

    void apply(V value) throws T;
}
