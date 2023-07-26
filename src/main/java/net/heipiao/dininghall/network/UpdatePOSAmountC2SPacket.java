package net.heipiao.dininghall.network;

import java.util.function.Supplier;

import net.heipiao.dininghall.block.entity.ModBlockEntities;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkEvent;

public class UpdatePOSAmountC2SPacket {
    private BlockPos pos;
    private int val;
    private RegistryKey<World> world;

    public void encode(PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeInt(this.val);
        buf.writeRegistryKey(this.world);
    }

    public UpdatePOSAmountC2SPacket(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.val = buf.readInt();
        this.world = buf.readRegistryKey(RegistryKeys.WORLD);
    }

    public UpdatePOSAmountC2SPacket(BlockPos pos, int val, RegistryKey<World> world) {
        this.pos = pos;
        this.val = val;
        this.world = world;
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var optional = context.getSender().getServer().getWorld(this.world).getBlockEntity(pos, ModBlockEntities.POS_BLOCK_ENTITY.get());
            if (optional.isEmpty()) {
                context.getSender().sendMessage(Text.translatable("dininghall.chat.no_block_entity"));
                return;
            }
            var be = optional.get();
            be.setAmount(val);
        });
        context.setPacketHandled(true);
    }
}
