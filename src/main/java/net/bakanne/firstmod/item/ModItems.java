package net.bakanne.firstmod.item;

import net.bakanne.firstmod.FirstMod;
import net.bakanne.firstmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    //Create Pulverizer item
//    public static final Item PULVERIZER = register(
//            "pulverizer",
//            PulverizerItem::new,
//            new Item.Settings()
//    );

    //Create the group key
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FirstMod.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.PULVERIZER_BLOCK.asItem()))
            .displayName(Text.translatable("itemGroup.firstMod"))
            .build();

//    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
//        // Create the item key.
//        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FirstMod.MOD_ID, name));
//
//        // Create the item instance.
//        Item item = itemFactory.apply(settings.registryKey(itemKey));
//
//        // Register the item.
//        Registry.register(Registries.ITEM, itemKey, item);
//
//        return item;
//    }

    public static void initialize(){
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        // Register items to the custom item group.
//        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
//            itemGroup.add(ModItems.PULVERIZER);
//        });
    }
}