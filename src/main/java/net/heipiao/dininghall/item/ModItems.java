package net.heipiao.dininghall.item;

import net.heipiao.dininghall.DiningHall;
import net.heipiao.dininghall.block.ModBlocks;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
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
    public static final RegistryObject<Item> CHARGER = MOD_ITEMS.register("charger", () -> new BlockItem(ModBlocks.CHARGER.get(), new Item.Settings()));
    public static final RegistryObject<Item> CARD_ISSUER = MOD_ITEMS.register("card_issuer", () -> new BlockItem(ModBlocks.CARD_ISSUER.get(), new Item.Settings()));

    public static final RegistryObject<Item> ITALY_NOODLE = MOD_ITEMS.register("italy_noodle", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(14).saturationModifier(0.5f).build())));
    public static final RegistryObject<Item> BONE_NOODLE = MOD_ITEMS.register("bone_noodle", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(14).saturationModifier(0.5f).build())));
    public static final RegistryObject<Item> RICE_SET_MEAL = MOD_ITEMS.register("rice_set_meal", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(14).saturationModifier(0.5f).build())));
    public static final RegistryObject<Item> RICE_NOODLE = MOD_ITEMS.register("rice_noodle", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(7).saturationModifier(1).build())));
    public static final RegistryObject<Item> CHIPS = MOD_ITEMS.register("chips", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(7).saturationModifier(1).snack().build())));
    public static final RegistryObject<Item> MUNG_BEAN_SOUP = MOD_ITEMS.register("mung_bean_soup", () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(7).saturationModifier(1).snack().build())));

    public static final DeferredRegister<ItemGroup> MOD_ITEM_GROUPS = DeferredRegister.create(RegistryKeys.ITEM_GROUP,
            DiningHall.MODID);

    public static final RegistryObject<ItemGroup> UTILS_ITEM_GROUP = MOD_ITEM_GROUPS.register(
        "utils",
        () -> ItemGroup.builder()
            .icon(CARD.lazyMap(ItemStack::new))
            .entries((context, entries) -> {
                entries.add(CARD.get());
                entries.add(POS.get());
                entries.add(CHARGER.get());
                entries.add(CARD_ISSUER.get());
            })
            .displayName(Text.translatable("dininghall.tab.utils"))
            .build()
    );
    public static final RegistryObject<ItemGroup> FOOD_ITEM_GROUP = MOD_ITEM_GROUPS.register(
        "food",
        () -> ItemGroup.builder()
            .icon(CARD.lazyMap(ItemStack::new))
            .entries((context, entries) -> {
                entries.add(ITALY_NOODLE.get());
                entries.add(BONE_NOODLE.get());
                entries.add(RICE_SET_MEAL.get());
                entries.add(RICE_NOODLE.get());
                entries.add(CHIPS.get());
                entries.add(MUNG_BEAN_SOUP.get());
            })
            .displayName(Text.translatable("dininghall.tab.food"))
            .build()
    );
}
