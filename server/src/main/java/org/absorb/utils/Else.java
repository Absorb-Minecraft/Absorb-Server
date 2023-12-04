package org.absorb.utils;

import org.absorb.utils.lamda.ThrowableSupplier;

import java.util.function.Function;
import java.util.function.Supplier;

public class Else {

    public static <T extends Throwable> void tryFail(Supplier<Boolean> getter, Function<Boolean, T> func) throws T {
        tryFail(getter, false, func);
    }

    public static <T extends Throwable> void tryFail(Supplier<Boolean> getter, boolean fail, Function<Boolean, T> func) throws T {
        boolean get = getter.get();
        if (get == fail) {
            throw func.apply(get);
        }
    }

    public static <Value, T extends Throwable> Value tryGetOrElse(Class<T> clazz, Supplier<Value> supplier, ThrowableSupplier<Value, T> suppliers) {
        try {
            return tryGet(clazz, suppliers);
        } catch (Throwable e) {
            if (e instanceof RuntimeException runtime) {
                throw runtime;
            }
            return supplier.get();
        }
    }

    public static <Value, T extends Throwable> Value tryGet(Class<T> clazz, ThrowableSupplier<Value, T>... supplier) throws T {
        T exception = null;
        for (ThrowableSupplier<Value, T> sup : supplier) {
            try {
                return sup.get();
            } catch (Throwable ex) {
                if (clazz.isInstance(ex)) {
                    exception = (T) ex;
                    continue;
                }
                throw new RuntimeException("Read exception below", ex);
            }
        }
        if (exception == null) {
            throw new RuntimeException("No suppliers passed");
        }
        throw exception;
    }
}
