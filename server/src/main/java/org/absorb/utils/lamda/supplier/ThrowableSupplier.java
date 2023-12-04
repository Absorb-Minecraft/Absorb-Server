package org.absorb.utils.lamda;

public interface ThrowableSupplier<Value, T extends Throwable> {

    Value get() throws T;

}
