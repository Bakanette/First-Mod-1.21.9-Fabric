package net.bakanne.firstmod.block.entity.custom;

import com.google.common.collect.ImmutableSet;
import net.bakanne.firstmod.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PulverizerBlockEntity extends BlockEntity {

    private int tick = 0;

    // Map to track damage progress (0-100) for all surrounding blocks
    private final Map<BlockPos, Float> blockDamageProgress = new HashMap<>();

    // A unique ID for the block breaking animation.
    // Usually the Block Entity's position is used, converted to an int.
    private final int breakAnimationId;

    private static final Set<Block> VALID_TARGET_BLOCKS = ImmutableSet.of(
            Blocks.COBBLESTONE,
            Blocks.STONE,
            Blocks.GRANITE,
            Blocks.DIORITE,
            Blocks.ANDESITE,
            Blocks.NETHERRACK,
            Blocks.OBSIDIAN
    );


    public PulverizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PULVERIZER_BLOCK_ENTITY, pos, state);

        // Generate a unique ID for the animation using the position
        this.breakAnimationId = pos.hashCode();
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return this.tick;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PulverizerBlockEntity pulverizerBlockEntity) {
        if (pulverizerBlockEntity.getTick() < 0) return;

        pulverizerBlockEntity.setTick(pulverizerBlockEntity.getTick() + 1);
        // Call the new block breaking logic here
        handleBlockBreaking(world, pulverizerBlockEntity);
        //checkSurroundings(world, pos, Blocks.COBBLESTONE);
    }

//    public static void checkSurroundings(World world, BlockPos pos, Block blockToCheck){
//
//        MinecraftClient client = MinecraftClient.getInstance();
//
//        for (Direction direction : Direction.values()) {
//
//            // 1. Calculate the adjacent position
//            BlockPos adjacentPos = pos.offset(direction);
//
//            // 2. Get the BlockState
//            BlockState adjacentState = world.getBlockState(adjacentPos);
//
//            // 3. Perform your check
//            if (adjacentState.isOf(blockToCheck) && client.player != null) {
//                //Debug mess in game chat : block + direction
//                Text blockName = blockToCheck.asItem().getName();
//                MutableText message = Text.empty()
//                    .append(blockName.copy())
//                    .append(" found at ")
//                    .append(direction.asString());
//                client.player.sendMessage(message, false);
//            }
//        }
//    }

    private static void handleBlockBreaking(World world, PulverizerBlockEntity entity) {

        if (world.isClient()) return; // Only run on the server

        BlockPos currentPos = entity.getPos();
        boolean dirty = false;

        // Create a temporary list of positions to clear, since we can't modify the map while iterating it.
        Map<BlockPos, Float> targetsToClear = new HashMap<>();

        // 1. ITERATE and UPDATE: Loop through all 6 adjacent directions
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = currentPos.offset(direction);
            BlockState adjacentState = world.getBlockState(adjacentPos);

            // --- Damage Logic ---

            // Ensure the block is mineable (not air, bedrock, or indestructible) and check if the block is in our valid target set
            if (!VALID_TARGET_BLOCKS.contains(adjacentState.getBlock()) || adjacentState.isAir() || adjacentState.getHardness(world, adjacentPos) < 0) {

                // If the block is *not* mineable, but we were tracking damage, clear it.
                if (entity.blockDamageProgress.containsKey(adjacentPos)) {
                    targetsToClear.put(adjacentPos, -1f); // Stage -1 clears animation
                    dirty = true;
                }
                continue; // Move to the next direction
            }

            // --- Calculate Progress ---

            // Get the current damage progress (defaults to 0 if not yet in the map)
            float currentProgress = entity.blockDamageProgress.getOrDefault(adjacentPos, 0f);

            // Get block hardness
            float hardness = adjacentState.getHardness(world, adjacentPos);

            // Damage amount to apply per tick (adjust 5 to change speed)
            // Ensure damage is positive, though getHardness should be positive here
            float damagePerTick = Math.max(0.1f, (5 / hardness));

            // 2. Update Damage Progress
            currentProgress += damagePerTick;

            // 3. Check if Block is Broken
            if (currentProgress >= 100) {

                // Break the block and place the drops in the world
                world.breakBlock(adjacentPos, true);

                // Mark for clearing the damage animation and map state
                targetsToClear.put(adjacentPos, -1f);

            } else {

                // 4. Update Animation (Map to 0-9 for cracked texture)
                int animationStage = (int) ( currentProgress / 100f * 9f);

                // Set the block damage stage visible to players
                world.setBlockBreakingInfo(entity.breakAnimationId + adjacentPos.hashCode(), adjacentPos, animationStage);

                // Update the map with the new progress
                entity.blockDamageProgress.put(adjacentPos, currentProgress);
            }

            dirty = true;
        }

        // 5. Cleanup: Clear any blocks that were broken or are no longer valid targets
        for (Map.Entry<BlockPos, Float> entry : targetsToClear.entrySet()) {
            world.setBlockBreakingInfo(entity.breakAnimationId + entry.getKey().hashCode(), entry.getKey(), -1);
            entity.blockDamageProgress.remove(entry.getKey());
        }

        if (dirty) {
            entity.markDirty();
        }
    }
}
