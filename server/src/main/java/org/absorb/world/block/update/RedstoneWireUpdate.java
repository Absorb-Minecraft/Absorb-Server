package org.absorb.world.block.update;

import org.absorb.world.block.property.BlockProperties;
import org.absorb.world.block.property.properties.RedstoneConnectedProperty;
import org.absorb.world.block.property.values.ConnectedState;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.block.type.BlockTypes;
import org.absorb.world.location.Direction;
import org.absorb.world.location.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedstoneWireUpdate {

    public static void tickUpdate(Position originalPosition) {
        boolean redstoneConnects = originalPosition.blockState().type().redstoneConnects();
        for (int relativeHeight = -1; relativeHeight < 2; relativeHeight++) {
            for (Direction direction : Direction.fourFacingValues(false).toList()) {
                var relative = originalPosition.add(direction);
                if (!relative.blockState().type().equals(BlockTypes.REDSTONE_WIRE)) {
                    continue;
                }
                var state = placeWire(relative, relative.blockState(), false, true);

                if (!redstoneConnects) {
                    continue;
                }

                RedstoneConnectedProperty property = switch (direction.opposite()) {
                    case NORTH -> BlockProperties.CONNECTED_NORTH_REDSTONE;
                    case EAST -> BlockProperties.CONNECTED_EAST_REDSTONE;
                    case SOUTH -> BlockProperties.CONNECTED_SOUTH_REDSTONE;
                    case WEST -> BlockProperties.CONNECTED_WEST_REDSTONE;
                    default ->
                            throw new RuntimeException("Unknown 4 facing direction of " + direction.opposite().name());
                };

                var targetConnectionType = switch (relativeHeight) {
                    case -1 -> ConnectedState.UP;
                    case 0, 1 -> ConnectedState.SIDE;
                    default ->
                            throw new RuntimeException("Should be impossible to hit this: height: " + relativeHeight);
                };

                var opWireState = state.with(property, targetConnectionType);
                if (opWireState.isPresent()) {
                    relative.setBlockState(state, false);
                }
            }
        }
    }

    public static BlockState placeWire(Position originalPosition, BlockState originalState) {
        return placeWire(originalPosition, originalState, true, true);
    }

    public static BlockState placeWire(Position originalPosition, BlockState originalState, boolean extend, boolean update) {
        if (!originalState.type().equals(BlockTypes.REDSTONE_WIRE)) {
            return originalState;
        }
        Map<RedstoneConnectedProperty, ConnectedState> connectedDirections = new HashMap<>();
        for (int relativeHeight = -1; relativeHeight < 2; relativeHeight++) {
            var targetHeight = originalPosition.add(0, relativeHeight, 0);
            for (Direction direction : Direction.fourFacingValues(false).toList()) {
                var target = targetHeight.add(direction);
                var targetState = target.blockState();
                var targetType = targetState.type();

                RedstoneConnectedProperty property = switch (direction) {
                    case NORTH -> BlockProperties.CONNECTED_NORTH_REDSTONE;
                    case EAST -> BlockProperties.CONNECTED_EAST_REDSTONE;
                    case SOUTH -> BlockProperties.CONNECTED_SOUTH_REDSTONE;
                    case WEST -> BlockProperties.CONNECTED_WEST_REDSTONE;
                    default -> throw new RuntimeException("Unknown 4 facing direction of " + direction.name());
                };

                var currentValue = connectedDirections.get(property);
                if (currentValue != null && !currentValue.equals(ConnectedState.NONE)) {
                    continue;
                }

                if (!targetType.redstoneConnects()) {
                    connectedDirections.put(property, ConnectedState.NONE);
                    continue;
                }
                var connectionType = switch (relativeHeight) {
                    case -1, 0 -> ConnectedState.SIDE;
                    case 1 -> ConnectedState.UP;
                    default ->
                            throw new RuntimeException("Should be impossible to hit this: height: " + relativeHeight);
                };

                connectedDirections.put(property, connectionType);
                if (!targetType.equals(BlockTypes.REDSTONE_WIRE)) {
                    continue;
                }
                if (!extend) {
                    continue;
                }
                targetState = placeWire(target, targetState, false, false);
                var targetConnectionType = switch (relativeHeight) {
                    case -1 -> ConnectedState.UP;
                    case 0, 1 -> ConnectedState.SIDE;
                    default ->
                            throw new RuntimeException("Should be impossible to hit this: height: " + relativeHeight);
                };
                var updatedTargetState = targetState.with(property.opposite(), targetConnectionType).orElseThrow(() -> new RuntimeException("Redstone no longer supports redstone connection properties"));
                target.setBlockState(updatedTargetState, false);
            }
        }
        if (update) {
            if (connectedDirections.isEmpty()) {
                var allDirections = Stream.of(
                                BlockProperties.CONNECTED_NORTH_REDSTONE,
                                BlockProperties.CONNECTED_EAST_REDSTONE,
                                BlockProperties.CONNECTED_SOUTH_REDSTONE,
                                BlockProperties.CONNECTED_WEST_REDSTONE)
                        .collect(Collectors.toMap(direction -> direction, direction -> ConnectedState.SIDE));
                connectedDirections.putAll(allDirections);
            }
            if (connectedDirections.size() == 1) {
                var property = connectedDirections.keySet().iterator().next().opposite();
                connectedDirections.put(property, ConnectedState.SIDE);
            }
        }
        for (var entry : connectedDirections.entrySet()) {
            originalState = originalState.with(entry.getKey(), entry.getValue()).orElseThrow(() -> new RuntimeException("redstone no longer supports redstone connected " + entry.getKey().propertyName() + " property"));
        }
        return originalState;
    }
}
