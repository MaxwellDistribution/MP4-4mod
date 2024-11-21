package net.maxwell.formula1mod.item;

import net.maxwell.formula1mod.entity.ModEntities;
import net.maxwell.formula1mod.item.custom.MP4_4summoner;
import net.maxwell.formula1mod.item.custom.TestItem;
import net.maxwell.formula1mod.Formula1Mod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Moditems
    {
        public static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, Formula1Mod.MOD_ID);

        public static final RegistryObject<Item> TROPHEY = ITEMS.register("trophey", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> RAW_ALUMINIUM = ITEMS.register("raw_aluminium", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> TESTITEM = ITEMS.register("test_item", () -> new TestItem(new Item.Properties().durability(1000)));

        public static final RegistryObject<Item> MP4_4_SUMMONER = ITEMS.register("mp4_4_summoner", () -> new MP4_4summoner(new Item.Properties()));

        public static void register(IEventBus eventBus)
        {
            ITEMS.register(eventBus);
        }
    }
