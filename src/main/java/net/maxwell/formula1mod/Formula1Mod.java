package net.maxwell.formula1mod;

import com.mojang.logging.LogUtils;
import net.maxwell.formula1mod.block.ModBlocks;
import net.maxwell.formula1mod.entity.ModEntities;
import net.maxwell.formula1mod.entity.animation.ModAnimationDefinitions;
import net.maxwell.formula1mod.entity.client.ModModelLayers;
import net.maxwell.formula1mod.entity.client.mp4_4Renderer;
import net.maxwell.formula1mod.item.ModCreativeModeTabs;
import net.maxwell.formula1mod.item.Moditems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Formula1Mod.MOD_ID)
public class Formula1Mod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "formula1mod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Formula1Mod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        Moditems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.accept(Moditems.TROPHEY);
            event.accept(Moditems.TITANIUM_INGOT);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.MP4_4.get(), mp4_4Renderer::new);
        }

        @SubscribeEvent
        public void onEntityMount(EntityMountEvent event)
        {
            if (event.getEntityMounting() instanceof Player)
            {
                Player player = (Player) event.getEntityMounting();
                Entity mount = event.getEntityBeingMounted();

                // Проверяем, что игрок садится на сущность
                if (event.isMounting())
                {
                    // Изменяем позицию игрока относительно сущности
                    player.setPos(mount.getX(), mount.getY() - 0.5, mount.getZ());
                }
            }
        }
    }
}
