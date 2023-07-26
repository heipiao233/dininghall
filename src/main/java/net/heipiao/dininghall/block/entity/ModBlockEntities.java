package net.heipiao.dininghall.block.entity;

import net.heipiao.dininghall.DiningHall;
import net.heipiao.dininghall.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum ModBlockEntities {
    ;
    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, DiningHall.MODID);
    public static final RegistryObject<BlockEntityType<POSBlockEntity>> POS_BLOCK_ENTITY = MOD_BLOCK_ENTITIES
            .register("pos",
                    () -> BlockEntityType.Builder.create(POSBlockEntity::new, ModBlocks.POS.get())
                            .build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "pos")));
}
