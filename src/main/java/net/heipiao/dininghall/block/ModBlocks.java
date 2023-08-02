package net.heipiao.dininghall.block;

import net.heipiao.dininghall.DiningHall;
import net.minecraft.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum ModBlocks {
    ;
    public static final DeferredRegister<Block> MOD_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            DiningHall.MODID);
    public static final RegistryObject<Block> POS = MOD_BLOCKS.register("pos",
            () -> new POSBlock(Block.Settings.create()));
    public static final RegistryObject<Block> CHARGER = MOD_BLOCKS.register("charger",
            () -> new ChargerBlock(Block.Settings.create()));
    public static final RegistryObject<Block> CARD_ISSUER = MOD_BLOCKS.register("card_issuer",
        () -> new CardIssuerBlock(Block.Settings.create()));
}
