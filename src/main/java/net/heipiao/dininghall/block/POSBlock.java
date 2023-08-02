package net.heipiao.dininghall.block;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import net.heipiao.dininghall.block.entity.POSBlockEntity;
import net.heipiao.dininghall.item.ModItems;
import net.heipiao.dininghall.saveddata.AccountSavedData;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.command.EffectCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class POSBlock extends Block implements BlockEntityProvider {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static final VoxelShape NS_SHAPE = Block.createCuboidShape(1.0, 0.0, 6.0, 15.0, 12.0, 10.0);
    protected static final VoxelShape EW_SHAPE = Block.createCuboidShape(6.0, 0.0, 1.0, 10.0, 12.0, 15.0);

    public POSBlock(Settings arg) {
        super(arg);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case EAST, WEST -> EW_SHAPE;
            case NORTH, SOUTH -> NS_SHAPE;
            default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
        };
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
                        player.sendMessage(Text.translatable("dininghall.text.no_account"));
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
                    player.sendMessage(Text.translatable("dininghall.text.no_money"));
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
