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
}
