package net.maxwell.formula1mod.item.custom;

import net.maxwell.formula1mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class TestItem extends Item
{
    public TestItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        if(!pContext.getLevel().isClientSide())
        {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            BlockState clickedBlockState = pContext.getLevel().getBlockState(positionClicked);

            if(clickedBlockState.getBlock() == ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get())
            {
                BlockState rubbered_asphalt_block = ModBlocks.RUBBERED_ASPHALT_BLOCK.get().defaultBlockState();
                pContext.getLevel().setBlockAndUpdate(positionClicked, rubbered_asphalt_block);
            }

        }

        if(!pContext.getLevel().isClientSide())
        {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            BlockState clickedBlockState = pContext.getLevel().getBlockState(positionClicked);

            if(clickedBlockState.getBlock() == ModBlocks.ASPHALT_BLOCK.get())
            {
                BlockState half_rubbered_asphalt_block = ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get().defaultBlockState();
                pContext.getLevel().setBlockAndUpdate(positionClicked, half_rubbered_asphalt_block);
            }

        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), player ->
                player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }
}
