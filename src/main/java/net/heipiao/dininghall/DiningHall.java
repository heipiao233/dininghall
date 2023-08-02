package net.heipiao.dininghall;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.registries.DeferredRegister;

import net.heipiao.dininghall.block.ModBlocks;
import net.heipiao.dininghall.block.entity.ModBlockEntities;
import net.heipiao.dininghall.item.ModItems;
import net.heipiao.dininghall.network.Network;
import net.heipiao.dininghall.screen.POSScreen;
import net.heipiao.dininghall.screen.handler.ModScreenHandlers;
import net.heipiao.dininghall.screen.handler.POSScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DiningHall.MODID)
public class DiningHall {
    public static final String MODID = "dininghall";
    public static final DeferredRegister<PaintingVariant> MOD_PAINTING_VARIANTS = DeferredRegister.create(RegistryKeys.PAINTING_VARIANT, MODID);

    public DiningHall() {

        // This is our mod's event bus, used for things like registry or lifecycle
        // events
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MOD_PAINTING_VARIANTS.register(MOD_BUS);
        ModItems.MOD_ITEMS.register(MOD_BUS);
        ModItems.MOD_ITEM_GROUPS.register(MOD_BUS);
        ModBlocks.MOD_BLOCKS.register(MOD_BUS);
        ModBlockEntities.MOD_BLOCK_ENTITIES.register(MOD_BUS);
        ModScreenHandlers.MOD_SCREEN_HANDLERS.register(MOD_BUS);
        MOD_BUS.addListener(this::commonSetup);
        MOD_BUS.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(Network::register);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            HandledScreens.<POSScreenHandler, POSScreen>register(ModScreenHandlers.POS_SCREEN_HANDLER.get(), POSScreen::new);
        });
    }
}
