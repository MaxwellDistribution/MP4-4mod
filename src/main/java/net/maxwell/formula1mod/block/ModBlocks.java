package net.maxwell.formula1mod.block;

import net.maxwell.formula1mod.Formula1Mod;
import net.maxwell.formula1mod.block.custom.TestBlock;
import net.maxwell.formula1mod.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Formula1Mod.MOD_ID);

    public static final RegistryObject<Block> ASPHALT_BLOCK = registerBlock("asphalt_block", () -> new TestBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_CONCRETE)));
    public static final RegistryObject<Block> HALF_RUBBERED_ASPHALT_BLOCK = registerBlock("half_rubbered_asphalt_block", () -> new TestBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_CONCRETE)));
    public static final RegistryObject<Block> RUBBERED_ASPHALT_BLOCK = registerBlock("rubbered_asphalt_block", () -> new TestBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_CONCRETE)));
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> ALUMINIUM_ORE = registerBlock("aluminium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return Moditems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
