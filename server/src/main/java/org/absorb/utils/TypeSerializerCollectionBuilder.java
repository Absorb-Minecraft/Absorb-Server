package org.absorb.utils;

import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

public class TypeSerializerCollectionBuilder {

    private TypeSerializerCollection.Builder collection = TypeSerializerCollection.builder().registerAll(TypeSerializerCollection.defaults());

    public TypeSerializerCollection build() {
        return this.collection.build();
    }

    public <T> TypeSerializerCollectionBuilder register(Class<T> clazz, TypeSerializer<T> serializer) {
        this.collection.register(clazz, serializer);
        return this;
    }
}
