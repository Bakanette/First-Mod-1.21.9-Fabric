package net.bakanne.firstmod.block.entity.custom;

import net.bakanne.firstmod.block.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PulverizerBlockEntity extends BlockEntity {

    public PulverizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PULVERIZER_BLOCK_ENTITY, pos, state);
    }

    private int clicks = 0;
    public int getClicks() {
        return clicks;
    }

    public void incrementClicks() {
        clicks++;
        markDirty();
    }
}
