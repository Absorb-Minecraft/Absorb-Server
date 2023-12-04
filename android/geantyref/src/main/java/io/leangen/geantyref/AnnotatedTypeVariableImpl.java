/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or at <a href="http://www.apache.org/licenses/LICENSE-2">apache.org</a>.
 */

package io.leangen.geantyref;

import org.absorb.geantyref.FakeAnnotatedType;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

class AnnotatedTypeVariableImpl extends AnnotatedTypeImpl implements AnnotatedTypeVariable {

    private AnnotatedType[] annotatedBounds;

    AnnotatedTypeVariableImpl(TypeVariable<?> type) {
        this(type, type.getGenericDeclaration().getAnnotations());
    }

    AnnotatedTypeVariableImpl(TypeVariable<?> type, Annotation[] annotations) {
        super(type, annotations);
        AnnotatedType[] annotatedBounds = Arrays.stream(type.getBounds()).map(FakeAnnotatedType::create).toArray(AnnotatedType[]::new);
        if (annotatedBounds == null || annotatedBounds.length == 0) {
            annotatedBounds = new AnnotatedType[0];
        }
        this.annotatedBounds = annotatedBounds;
    }

    void init(AnnotatedType[] annotatedBounds) {
        this.type = new TypeVariableImpl<>((TypeVariable<?>) this.type, this.getAnnotations(), annotatedBounds);
        this.annotatedBounds = annotatedBounds;
    }

    @Override
    public AnnotatedType[] getAnnotatedBounds() {
        return annotatedBounds.clone();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AnnotatedTypeVariable && super.equals(other);
    }

    @Override
    public String toString() {
        return annotationsString() + ((TypeVariable) type).getName();
    }
}
