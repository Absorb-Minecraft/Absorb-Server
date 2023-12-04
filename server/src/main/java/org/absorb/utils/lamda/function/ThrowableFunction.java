package org.absorb.utils.lamda.function;

public interface ThrowableFunction<Value, To, T extends Throwable> {

    To apply(Value value) throws T;

}
