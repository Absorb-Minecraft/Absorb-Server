package org.absorb.geantyref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class WildcardAnnotatedType extends FakeAnnotatedType implements AnnotatedWildcardType {

    protected WildcardAnnotatedType(@NotNull WildcardType type) {
        super(type);
    }

    @NotNull
    @Override
    public WildcardType getType() {
        return (WildcardType) super.getType();
    }

    @Override
    public AnnotatedType[] getAnnotatedLowerBounds() {
        return new AnnotatedType[0];
    }

    @Override
    public AnnotatedType[] getAnnotatedUpperBounds() {
        return new AnnotatedType[0];
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends Annotation> T getAnnotation(@NonNull @NotNull Class<T> aClass) {
        Stream<Type> lower = Arrays.stream(getType().getLowerBounds());
        Stream<Type> upper = Arrays.stream(getType().getUpperBounds());
        return Stream
                .concat(lower, upper)
                .map(FakeAnnotatedType::create)
                .map(type -> type.getAnnotation(aClass))
                .filter(Objects::nonNull).map(t -> (T) t)
                .findAny()
                .orElse(null);
    }
}
