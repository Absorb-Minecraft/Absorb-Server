package org.absorb.utils.lamda;

public interface ThrowableTriConsumer<A, B, C, T extends Throwable> {

    void accept(A a, B b, C c) throws T;
}
