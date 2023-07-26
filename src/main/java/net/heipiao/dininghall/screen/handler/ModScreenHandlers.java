package net.heipiao.dininghall.screen.handler;

import net.heipiao.dininghall.DiningHall;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public enum ModScreenHandlers {
    ;
    public static final DeferredRegister<ScreenHandlerType<?>> MOD_SCREEN_HANDLERS = DeferredRegister
            .create(RegistryKeys.SCREEN_HANDLER, DiningHall.MODID);
    public static final RegistryObject<ScreenHandlerType<POSScreenHandler>> POS_SCREEN_HANDLER = MOD_SCREEN_HANDLERS
            .register("pos",
                    () -> IForgeMenuType.create(POSScreenHandler::new));}
