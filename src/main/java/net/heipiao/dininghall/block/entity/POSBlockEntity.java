package net.heipiao.dininghall.block.entity;

import javax.annotation.Nullable;

import net.heipiao.dininghall.screen.handler.POSScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class POSBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private boolean amountSet = false;
    private boolean ready = false;
    private int amount;

    public POSBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public POSBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.POS_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    @Nullable
    public ScreenHandler createMenu(int var1, PlayerInventory var2, PlayerEntity var3) {
        return new POSScreenHandler(var1, var2, this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("dininghall.screen.pos");
    }

    public boolean isAmountSet() {
        return this.amountSet;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.amountSet = true;
    }

    public void clearAmount() {
        this.amountSet = false;
    }

    public void setReady() {
        this.ready = true;
    }

    public void clearReady() {
        this.ready = false;
    }

    public boolean isReady() {
        return ready;
    }

}
