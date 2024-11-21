package net.maxwell.formula1mod.item;

import net.maxwell.formula1mod.Formula1Mod;
import net.maxwell.formula1mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Formula1Mod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FORMULA_1 = CREATIVE_MOD_TABS.register("formula_1", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.TROPHEY.get()))
            .title(Component.translatable("creativetab.formula_1"))
            .displayItems((itemDisplayParameters, output) -> {
                //output.accept(Moditems.TROPHEY.get());
                //output.accept(Moditems.TITANIUM_INGOT.get());
                //output.accept(Moditems.RAW_ALUMINIUM.get());
                //output.accept(Moditems.ALUMINIUM_INGOT.get());
                //output.accept(Moditems.TESTITEM.get());

                //output.accept(ModBlocks.ASPHALT_BLOCK.get());
                //output.accept(ModBlocks.TITANIUM_BLOCK.get());
                //output.accept(ModBlocks.TITANIUM_ORE.get());
                //output.accept(ModBlocks.ALUMINIUM_ORE.get());
                //output.accept(ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get());
                //output.accept(ModBlocks.RUBBERED_ASPHALT_BLOCK.get());

                output.accept(Moditems.MP4_4_SUMMONER.get());
            })

            .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
