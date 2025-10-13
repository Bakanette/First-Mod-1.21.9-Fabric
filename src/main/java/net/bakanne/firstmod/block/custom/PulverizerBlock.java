package net.bakanne.firstmod.block.custom;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import net.bakanne.firstmod.block.entity.custom.PulverizerBlockEntity;

public class PulverizerBlock extends BlockWithEntity {

    public PulverizerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PulverizerBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PulverizerBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof PulverizerBlockEntity pulverizerBlockEntity)) {
            return super.onUse(state, world, pos, player, hit);
        }

        pulverizerBlockEntity.incrementClicks();
        player.sendMessage(Text.literal("You've clicked the block for the " + pulverizerBlockEntity.getClicks() + "th time."), true);

        return ActionResult.SUCCESS;
    }
}
