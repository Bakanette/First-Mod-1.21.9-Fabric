package net.bakanne.firstmod.block.entity;

import net.bakanne.firstmod.FirstMod;
import net.bakanne.firstmod.block.ModBlocks;
import net.bakanne.firstmod.block.entity.custom.PulverizerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<PulverizerBlockEntity> PULVERIZER_BLOCK_ENTITY =
            register("pulverizer", PulverizerBlockEntity::new, ModBlocks.PULVERIZER_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(FirstMod.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize(){

    }
}
