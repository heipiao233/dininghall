package net.heipiao.dininghall.block;

import net.heipiao.dininghall.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CardIssuerBlock extends Block {
    public CardIssuerBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            var stack = new ItemStack(ModItems.CARD.get(), 1);
            stack.getOrCreateSubNbt("dininghall").putUuid("account", player.getUuid());
            player.giveItemStack(stack);
        }
        return ActionResult.SUCCESS;
    }
}
