package net.maxwell.formula1mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.maxwell.formula1mod.entity.ModEntities;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class MP4_4summoner  extends Item
{
    public MP4_4summoner(Properties pProperties)
    {
        super(pProperties);
    }

    private void spawnCustomEntity(Level world, BlockPos pos)
    {
        EntityType<mp4_4Entity> entityType = ModEntities.MP4_4.get();
        ServerLevel serverWorld = (ServerLevel) world;
        mp4_4Entity entity = entityType.create(serverWorld);
        if (entity != null)
        {
            entity.moveTo(pos, 0.0F, 0.0F);
            serverWorld.addFreshEntity(entity);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level world = context.getLevel();

        BlockPos pos = context.getClickedPos();

        Player player = context.getPlayer();

        ItemStack stack = context.getItemInHand();

        if (!world.isClientSide && player != null)
        {
            // Спавним сущность на позиции блока
            spawnCustomEntity(world, pos.above());

            // Уменьшаем количество предметов в стеке
            stack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
