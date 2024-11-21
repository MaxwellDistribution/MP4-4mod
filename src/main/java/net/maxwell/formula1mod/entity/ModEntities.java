package net.maxwell.formula1mod.entity;

import net.maxwell.formula1mod.Formula1Mod;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Formula1Mod.MOD_ID);

    public static final RegistryObject<EntityType<mp4_4Entity>> MP4_4 =
            ENTITY_TYPES.register("mp4_4", () -> EntityType.Builder.of(mp4_4Entity::new, MobCategory.MISC)
                    .sized(1.6F, 0.6F).build("mp4_4"));

    public static void register(IEventBus bus)
    {
        ENTITY_TYPES.register(bus);
    }
}
