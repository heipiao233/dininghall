package net.heipiao.dininghall.block;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import net.heipiao.dininghall.block.entity.POSBlockEntity;
import net.heipiao.dininghall.item.ModItems;
import net.heipiao.dininghall.saveddata.AccountSavedData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkHooks;

public class POSBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public POSBlock(Settings arg) {
        super(arg);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld) {
            var be = (POSBlockEntity)Objects.requireNonNull(world.getBlockEntity(pos));
            if (hit.getSide().getOpposite() == state.get(FACING)) {
                NetworkHooks.openScreen((ServerPlayerEntity)player, be, pos);
            } else if (be.isAmountSet()) {
                if (!be.isReady()) {
                    player.sendMessage(Text.translatable("dininghall.text.money", be.getAmount()));
                    be.setReady();
                }
                var handItem = player.getStackInHand(hand);
                UUID account;
                if (handItem.isEmpty()) {
                    account = player.getUuid();
                } else if (handItem.isOf(ModItems.CARD.get())) {
                    var nbt = handItem.getOrCreateSubNbt("dininghall");
                    if (!nbt.containsUuid("account")) {
                        player.sendMessage(net.minecraft.text.Text.translatable("dininghall.text.no_account"));
                        be.clearAmount();
                        return ActionResult.FAIL;
                    }
                    account = nbt.getUuid("account");
                } else {
                    be.clearAmount();
                    return ActionResult.FAIL;
                }
                AccountSavedData savedData = serverWorld.getServer().getOverworld().getPersistentStateManager()
                        .getOrCreate(AccountSavedData::new, AccountSavedData::create, "account");
                if (!savedData.add(account, -be.getAmount())) {
                    be.clearAmount();
                    return ActionResult.FAIL;
                }
                be.clearAmount();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return new POSBlockEntity(var1, var2);
    }

}
