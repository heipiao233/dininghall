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
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class POSScreenHandler extends ScreenHandler {

    private World world;
    private BlockPos pos;

    protected POSScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, BlockPos pos) {
        super(type, syncId);
        this.pos = pos;
        this.world = inventory.player.getWorld();
        layoutPlayerInventorySlots(inventory, 8, 84);
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

    private int addSlotRange(Inventory inventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(inventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private void addSlotBox(Inventory inventory, int index, int x, int y, int horAmount, int dx, int verAmount,
            int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(inventory, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    private void layoutPlayerInventorySlots(Inventory inventory, int leftCol, int topRow) {
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

}
