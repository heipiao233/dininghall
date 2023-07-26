package net.heipiao.dininghall.item;

import net.heipiao.dininghall.DiningHall;
import net.heipiao.dininghall.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(Dist.CLIENT)
public enum ModItems {
    ;
    public static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            DiningHall.MODID);
    public static final RegistryObject<Item> CARD = MOD_ITEMS.register("card",
            () -> new Item(new Item.Settings().maxCount(1)));
    public static final RegistryObject<Item> POS = MOD_ITEMS.register("pos", () -> new BlockItem(ModBlocks.POS.get(), new Item.Settings()));

    public static final DeferredRegister<ItemGroup> MOD_ITEM_GROUPS = DeferredRegister.create(RegistryKeys.ITEM_GROUP,
            DiningHall.MODID);

    public static final RegistryObject<ItemGroup> MAIN_ITEM_GROUP = MOD_ITEM_GROUPS.register(
        "main",
        () -> ItemGroup.builder()
            .icon(CARD.lazyMap(ItemStack::new))
            .entries((context, entries) -> {
                entries.add(CARD.get());
                entries.add(POS.get());
            })
            .build()
    );
}
