package org.absorb.world.block.update;

import org.absorb.world.block.property.BlockProperties;
import org.absorb.world.block.property.properties.RedstoneConnectedProperty;
import org.absorb.world.block.property.values.ConnectedState;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.block.type.BlockTypes;
import org.absorb.world.location.Direction;
import org.absorb.world.location.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class RedstoneWireUpdate {

    public static RedstoneConnectedProperty toProperty(@NotNull Direction direction) {
        return switch (direction) {
            case NORTH -> BlockProperties.CONNECTED_NORTH_REDSTONE;
            case WEST -> BlockProperties.CONNECTED_WEST_REDSTONE;
            case SOUTH -> BlockProperties.CONNECTED_SOUTH_REDSTONE;
            case EAST -> BlockProperties.CONNECTED_EAST_REDSTONE;
            default -> throw new RuntimeException("Should not hit");
        };
    }

    public static void updateRedstoneWireStates(Position position, boolean placed) {
        Direction.fourFacingValues(false).forEach(direction -> {
            for (int relativeHeight : new int[]{0, 1, -1}) {
                var relativePosition = position.add(direction).add(0, relativeHeight, 0);
                var blockState = relativePosition.blockState();
                if (!blockState.type().equals(BlockTypes.REDSTONE_WIRE)) {
                    continue;
                }

                var property = toProperty(direction.opposite());
                ConnectedState state = connectedState(relativeHeight, placed);

                blockState = blockState.with(property, state).orElseThrow();
                relativePosition.setBlockState(blockState, false);
            }
        });
    }

    private static ConnectedState connectedState(int relativeHeight, boolean placed) {
        if (!placed) {
            return ConnectedState.NONE;
        }
        if (relativeHeight == -1) {
            return ConnectedState.UP;
        }
        return ConnectedState.SIDE;
    }

    public static BlockState updateRedstoneState(Position originalPosition, BlockState originalState) {
        if (!originalState.type().equals(BlockTypes.REDSTONE_WIRE)) {
            return originalState;
        }
        var newStates = Direction
                .fourFacingValues(false)
                .map(direction -> {
                    var property = toProperty(direction);
                    for (int relativeHeight : new int[]{0, 1, -1}) {
                        var relativeBlock = originalPosition.add(direction).add(0, relativeHeight, 0);
                        var relativeState = relativeBlock.blockState();

                        if (!relativeState.type().redstoneConnects()) {
                            continue;
                        }
                        if (!relativeState.type().equals(BlockTypes.REDSTONE_WIRE)) {
                            return Map.entry(property, relativeHeight == 1 ? ConnectedState.UP : ConnectedState.SIDE);
                        }
                        var newProperty = toProperty(direction.opposite());
                        relativeState = relativeState.with(newProperty, relativeHeight == -1 ? ConnectedState.UP : ConnectedState.SIDE).orElseThrow();
                        relativeBlock.setBlockState(relativeState, true);
                        return Map.entry(property, relativeHeight == 1 ? ConnectedState.UP : ConnectedState.SIDE);
                    }
                    return Map.entry(property, ConnectedState.NONE);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (var newState : newStates.entrySet()) {
            originalState = originalState.with(newState.getKey(), newState.getValue()).orElseThrow();
        }
        return originalState;
    }

    public static void updateRelativePower(Position position) {
        var toUpdate = Direction.fourFacingValues(false).map(direction -> {
                    for (int relativeHeight : new int[]{0, 1, -1}) {
                        var relativePosition = position.add(direction).add(0, relativeHeight, 0);
                        var property = toProperty(direction.opposite());
                        var relativeState = relativePosition.blockState();
                        if (!relativeState.type().equals(BlockTypes.REDSTONE_WIRE)) {
                            continue;
                        }
                        var value = relativeState.value(property).orElseThrow();
                        if (value.equals(ConnectedState.NONE)) {
                            continue;
                        }
                        return relativePosition;
                    }
                    return position.add(direction);
                })
                .filter(relativePosition -> relativePosition.blockState().type().equals(BlockTypes.REDSTONE_WIRE))
                .toList();
        toUpdate.forEach(RedstoneWireUpdate::updateWirePower);
    }

    public static void updateWirePower(Position position) {
        var thisState = position.blockState();
        if (!thisState.type().equals(BlockTypes.REDSTONE_WIRE)) {
            return;
        }
        var currentPower = thisState.value(BlockProperties.POWER).orElse(0);
        var relativePower = position.relativeRedstonePower();
        if (currentPower == relativePower && currentPower == 0) {
            return;
        }
        if ((currentPower + 1 != relativePower)) {
            position.setBlockState(thisState.with(BlockProperties.POWER, Math.max(relativePower - 1, 0)).orElseThrow());
            updateRelativePower(position);
        }

    }
}
