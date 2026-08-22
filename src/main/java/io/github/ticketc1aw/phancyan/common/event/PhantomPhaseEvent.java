package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.effect.ModEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class PhantomPhaseEvent {
    @SubscribeEvent
    public static void cancelWallDamage(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.hasEffect(ModEffects.PHANTOM_PHASE_EFFECT.get()))){
            return;
        }
        if (!(event.getSource().is(DamageTypes.IN_WALL))){
            return;
        }
        event.setCanceled(true);
    }
}
