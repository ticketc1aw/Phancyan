package io.github.ticketc1aw.phancyan.common.effect;

import io.github.ticketc1aw.phancyan.Phancyan;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Phancyan.MOD_ID);

    public static final RegistryObject<MobEffect> PHANTOM_PHASE_EFFECT = EFFECTS.register("phantom_phase", () -> new PhantomPhaseEffect(MobEffectCategory.NEUTRAL, 2039587));
}
