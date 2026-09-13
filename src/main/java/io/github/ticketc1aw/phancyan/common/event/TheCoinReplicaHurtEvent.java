package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class TheCoinReplicaHurtEvent {
    @SubscribeEvent
    public static void TheCoinReplicaHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        if (entity.getOffhandItem().is(ModItems.THE_COIN_REPLICA.get())) {
            event.setAmount((float) Math.sqrt(event.getAmount()));
            entity.getOffhandItem().shrink(1);
            entity.level().playSound(null,entity.blockPosition(),SoundEvents.TOTEM_USE, SoundSource.AMBIENT,1.0f,1.25f);
        }
    }
}
