package net.heipiao.dininghall.network;

import net.heipiao.dininghall.DiningHall;
import net.minecraft.util.Identifier;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public enum Network {
    ;
    private static final String PROTOCOL_VERSION = "1";
    private static int index = 0;
    public static SimpleChannel INSTANCE;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
            new Identifier(DiningHall.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(index++, UpdatePOSAmountC2SPacket.class, UpdatePOSAmountC2SPacket::encode, UpdatePOSAmountC2SPacket::new, UpdatePOSAmountC2SPacket::handle);
    }

}
