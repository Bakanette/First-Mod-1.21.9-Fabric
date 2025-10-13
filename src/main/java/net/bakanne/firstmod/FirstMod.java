package net.bakanne.firstmod;

import net.bakanne.firstmod.block.ModBlocks;
import net.bakanne.firstmod.block.entity.ModBlockEntities;
import net.bakanne.firstmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstMod implements ModInitializer {
	public static final String MOD_ID = "firstmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        LOGGER.info("Loading " + MOD_ID);

        ModBlocks.initialize();
        ModBlocks.registerModBlocks();

        ModBlockEntities.initialize();

        ModItems.initialize();
	}
}