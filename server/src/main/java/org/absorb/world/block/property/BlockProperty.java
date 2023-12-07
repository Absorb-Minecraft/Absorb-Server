package org.absorb.world.block.property;

import com.google.errorprone.annotations.CheckReturnValue;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface BlockProperty<P> {

    @CheckReturnValue
    default @NotNull P value(@NotNull Block block) {
        return parser(block.getProperty(propertyName()));
    }

    @CheckReturnValue
    @NotNull String propertyName();

    @ApiStatus.OverrideOnly
    @NotNull P parser(@NotNull String value);

    interface Writable<P> extends BlockProperty<P> {

        @CheckReturnValue
        default Block withValue(@NotNull Block block, P value) {
            String valueString = parser(value);
            try {
                return block.withProperty(propertyName(), valueString);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Block of '" + block.name() + "' does not contain property of '" + propertyName() + "' that allows the value of '" + valueString + "'");
            }
        }

        @ApiStatus.OverrideOnly
        default @NotNull String parser(@NotNull P value) {
            return value.toString();
        }
    }

    interface Readable<P> extends BlockProperty<P> {

    }
}
