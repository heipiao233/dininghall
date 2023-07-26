package net.heipiao.dininghall.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class POSScreenHandler extends ScreenHandler {

    private World world;
    private BlockPos pos;

    protected POSScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, BlockPos pos) {
        super(type, syncId);
        this.pos = pos;
        this.world = inventory.player.getWorld();
    }

    public POSScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf data) {
        this(ModScreenHandlers.POS_SCREEN_HANDLER.get(), syncId, inventory, data.readBlockPos());
    }

    public POSScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(ModScreenHandlers.POS_SCREEN_HANDLER.get(), syncId, inventory, pos);
    }

    @Override
    public ItemStack quickMove(PlayerEntity var1, int var2) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        return true;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public RegistryKey<World> getWorld() {
        return this.world.getRegistryKey();
    }

}
