package net.heipiao.dininghall.block;

import java.util.UUID;

import javax.annotation.Nullable;

import net.heipiao.dininghall.item.ModItems;
import net.heipiao.dininghall.saveddata.AccountSavedData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ChargerBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public ChargerBlock(Settings arg) {
        super(arg);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld) {
            var handItem = player.getStackInHand(Hand.MAIN_HAND);
            var emerald = player.getStackInHand(Hand.OFF_HAND);
            if (!emerald.isOf(Items.EMERALD)) {
                return ActionResult.FAIL;
            }
            var amount = emerald.getCount() * 5;
            UUID account;
            if (handItem.isEmpty()) {
                account = player.getUuid();
            } else if (handItem.isOf(ModItems.CARD.get())) {
                var nbt = handItem.getOrCreateSubNbt("dininghall");
                if (!nbt.containsUuid("account")) {
                    player.sendMessage(net.minecraft.text.Text.translatable("dininghall.text.no_account"));
                    return ActionResult.FAIL;
                }
                account = nbt.getUuid("account");
            } else {
                return ActionResult.FAIL;
            }
            AccountSavedData savedData = serverWorld.getServer().getOverworld().getPersistentStateManager()
                    .getOrCreate(AccountSavedData::new, AccountSavedData::create, "account");
            savedData.add(account, amount);
            if (!player.getAbilities().creativeMode) {
                emerald.setCount(0);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
