package org.absorb.geantyref;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class ParameterizedAnnotationType extends FakeAnnotatedType implements AnnotatedParameterizedType {

    protected ParameterizedAnnotationType(ParameterizedType type) {
        super(type);
    }

    @Override
    public @NotNull ParameterizedType getType() {
        return (ParameterizedType) super.getType();
    }

    @Nullable
    @Override
    public <T extends Annotation> T getAnnotation(@NotNull Class<T> aClass) {
        return FakeAnnotatedType.create(getType().getRawType()).getAnnotation(aClass);
    }


    @Override
    public AnnotatedType[] getAnnotatedActualTypeArguments() {
        return Arrays.stream(getType().getActualTypeArguments()).map(FakeAnnotatedType::create).toArray(AnnotatedType[]::new);
    }
}
