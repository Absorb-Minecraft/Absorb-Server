package org.absorb.geantyref;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public abstract class FakeAnnotatedType implements AnnotatedType {

    private final @NotNull Type type;

    protected FakeAnnotatedType(@NotNull Type type) {
        this.type = type;
    }

    public static AnnotatedType create(@Nullable Type type) {
        if(type == null){
            return null;
        }
        if (type instanceof ParameterizedType) {
            return new ParameterizedAnnotationType((ParameterizedType) type);
        }
        if (type instanceof TypeVariable) {
            return new VariableAnnotatedType((TypeVariable<? extends GenericDeclaration>) type);
        }
        if (type instanceof WildcardType) {
            return new WildcardAnnotatedType((WildcardType) type);
        }
        if (type instanceof Class<?>) {
            if (((Class<?>) type).isArray()) {
                return new ArrayClassAnnotatedType((Class<?>) type);
            }
            return new ClassAnnotatedType((Class<?>) type);
        }
        throw new RuntimeException("Not supported type of " + type.getClass().getName());
    }

    @Override
    public @NotNull Type getType() {
        return type;
    }

    @NotNull
    @Override
    public Annotation[] getAnnotations() {
        if (!(type instanceof AnnotatedElement)) {
            return new Annotation[0];
        }

        return ((AnnotatedElement) this.type).getAnnotations();
    }

    @NotNull
    @Override
    public Annotation[] getDeclaredAnnotations() {
        if (!(type instanceof AnnotatedElement)) {
            return new Annotation[0];
        }

        return ((AnnotatedElement) this.type).getDeclaredAnnotations();
    }
}
