package net.maxwell.formula1mod.block.custom;

import net.maxwell.formula1mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TestBlock extends Block
{
    public TestBlock(Properties pProperties)
    {
        super(pProperties);
    }

    double playerX;
    double playerY;
    double playerZ;
    double blockX;
    double blockY;
    double blockZ;

    boolean hate = false;

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity)
    {
        super.stepOn(pLevel, pPos, pState, pEntity);

        playerX = pPos.getX();
        playerY = pPos.getY();
        playerZ = pPos.getZ();

        if (hate == false)
        {
            blockX = pPos.getX();
            blockY = pPos.getY();
            blockZ = pPos.getZ();
            hate = true;
        }

        if (pState.getBlock() == ModBlocks.ASPHALT_BLOCK.get())
        {
            BlockState half_rubbered_asphalt_block = ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get().defaultBlockState();
            pLevel.setBlock(pPos, half_rubbered_asphalt_block, 3);
            hate = false;
            return;
        }

        if (playerX == blockX && playerY == blockY && playerZ == blockZ)
        {
            return;
        }
        else
        {
            if (pState.getBlock() == ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get())
            {
                BlockState rubbered_asphalt_block = ModBlocks.RUBBERED_ASPHALT_BLOCK.get().defaultBlockState();
                pLevel.setBlock(pPos, rubbered_asphalt_block, 3);
            }
            hate = false;

        }

    }

}
