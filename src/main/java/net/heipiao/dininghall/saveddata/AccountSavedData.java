package net.heipiao.dininghall.saveddata;

import java.util.UUID;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;

public class AccountSavedData extends PersistentState {
    private final NbtCompound compound;

    public AccountSavedData(NbtCompound compound) {
        this.compound = compound;
    }

    public static AccountSavedData create() {
        return new AccountSavedData(new NbtCompound());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound var1) {
        return compound;
    }

    public boolean add(UUID player, int val) {
        int remains = this.compound.getInt(player.toString());
        if (remains + val < 0) return false;
        this.compound.putInt(player.toString(), remains + val);
        this.markDirty();
        return true;
    }

    public int get(ServerPlayerEntity player) {
        return this.compound.getInt(player.getUuidAsString());
    }
}
