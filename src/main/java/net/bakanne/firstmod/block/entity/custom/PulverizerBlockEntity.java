package net.bakanne.firstmod.block.entity.custom;

import net.bakanne.firstmod.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PulverizerBlockEntity extends BlockEntity {

    private int tick = 0;

    public PulverizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PULVERIZER_BLOCK_ENTITY, pos, state);
    }

//    private int clicks = 0;
//    public int getClicks() {
//        return clicks;
//    }
//
//    public void incrementClicks() {
//        clicks++;
//        markDirty();
//    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return this.tick;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PulverizerBlockEntity pulverizerBlockEntity) {
        if (pulverizerBlockEntity.getTick() < 0) return;

        pulverizerBlockEntity.setTick(pulverizerBlockEntity.getTick() + 1);
        checkSurroundings(world, pos, Blocks.COBBLESTONE);
    }

    public static void checkSurroundings(World world, BlockPos pos, Block blockToCheck){

        MinecraftClient client = MinecraftClient.getInstance();

        for (Direction direction : Direction.values()) {

            // 1. Calculate the adjacent position
            BlockPos adjacentPos = pos.offset(direction);

            // 2. Get the BlockState
            BlockState adjacentState = world.getBlockState(adjacentPos);

            // 3. Perform your check
            if (adjacentState.isOf(blockToCheck) && client.player != null) {
                //Debug mess in game chat : block + direction
                Text blockName = blockToCheck.asItem().getName();
                MutableText message = Text.empty()
                    .append(blockName.copy())
                    .append(" found at " + direction.asString());
                client.player.sendMessage(message, false);
            }
        }
    }
}
