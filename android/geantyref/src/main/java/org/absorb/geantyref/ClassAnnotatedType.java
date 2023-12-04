package org.absorb.geantyref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ClassAnnotatedType extends FakeAnnotatedType {

    public ClassAnnotatedType(@NotNull Class<?> type) {
        super(type);
    }

    @NotNull
    @Override
    public Class<?> getType() {
        return (Class<?>) super.getType();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends Annotation> T getAnnotation(@NonNull @NotNull Class<T> aClass) {
        return getType().getAnnotation(aClass);
    }
}
