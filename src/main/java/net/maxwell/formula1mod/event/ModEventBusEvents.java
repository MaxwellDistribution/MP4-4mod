package net.maxwell.formula1mod.event;

import net.maxwell.formula1mod.Formula1Mod;
import net.maxwell.formula1mod.entity.ModEntities;
import net.maxwell.formula1mod.entity.client.ModModelLayers;
import net.maxwell.formula1mod.entity.client.mp4_4Model;
import net.maxwell.formula1mod.entity.client.mp4_4Renderer;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.maxwell.formula1mod.item.Moditems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Formula1Mod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents
{
    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ModModelLayers.MP4_4_LAYER, mp4_4Model::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(final EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.MP4_4.get(), mp4_4Entity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            EntityRenderers.register(ModEntities.MP4_4.get(), mp4_4Renderer::new);
        });
    }

    private static void spawnCustomEntity(Level world, BlockPos pos)
    {
        if (world instanceof ServerLevel serverWorld)
        {
            EntityType<mp4_4Entity> entityType = ModEntities.MP4_4.get();
            mp4_4Entity entity = entityType.create(serverWorld);
            if (entity != null)
            {
                entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
                serverWorld.addFreshEntity(entity);
            }
        }
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        Player player = event.getEntity();
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() == Moditems.MP4_4_SUMMONER.get())
        {
            if (!world.isClientSide())
            {
                spawnCustomEntity(world, pos.above());

                heldItem.shrink(1);
                //Отменяем дальнейшую обработку события, если необходимо
                event.setCanceled(true);
            }

            player.swing(hand, true);
            //Устанавливаем результат успешным, чтобы предотвратить другие обработчики
            event.setCancellationResult(net.minecraft.world.InteractionResult.SUCCESS);
        }
    }
}
