package org.absorb.geantyref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;

public class ArrayClassAnnotatedType extends FakeAnnotatedType implements AnnotatedArrayType {

    public ArrayClassAnnotatedType(@NotNull Class<?> type) {
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

    @Override
    public AnnotatedType getAnnotatedGenericComponentType() {
        Class<?> componentType = getType().getComponentType();
        if (componentType == null) {
            return null;
        }
        return FakeAnnotatedType.create(componentType);
    }
}
