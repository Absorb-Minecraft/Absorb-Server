package org.absorb.geantyref;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class VariableAnnotatedType extends FakeAnnotatedType implements AnnotatedTypeVariable {

    public VariableAnnotatedType(@NotNull TypeVariable<? extends GenericDeclaration> type) {
        super(type);
    }

    @NotNull
    @Override
    public TypeVariable<? extends GenericDeclaration> getType() {
        return (TypeVariable<? extends GenericDeclaration>) super.getType();
    }

    @Override
    public AnnotatedType[] getAnnotatedBounds() {
        return Arrays.stream(getType().getBounds()).map(FakeAnnotatedType::create).toArray(AnnotatedType[]::new);
    }

    @Nullable
    @Override
    public <T extends Annotation> T getAnnotation(@NotNull Class<T> aClass) {
        return getType().getGenericDeclaration().getAnnotation(aClass);
    }
}
