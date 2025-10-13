package net.bakanne.firstmod.block;

import net.fabricmc.api.ModInitializer;

public class FirstModBlocks implements ModInitializer {

    @Override
    public void onInitialize() {

        ModBlocks.initialize();
        ModBlocks.registerModBlocks();
    }
}
