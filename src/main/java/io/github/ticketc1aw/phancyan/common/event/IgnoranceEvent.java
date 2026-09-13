package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.capability.IgnoranceProvider;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class IgnoranceEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;
        ServerPlayer player = (ServerPlayer) event.player;
        if (!(player.tickCount % 20 == 0)) return;

        player.getCapability(IgnoranceProvider.PLAYER_DATA).ifPresent(data -> {
            if (player.getInventory().contains(ModItems.THE_EYE.get().getDefaultInstance())) {
                data.setIgnorance(Math.min(data.getIgnorance() + 1, 100));
            } else {
                data.setIgnorance(Math.max(data.getIgnorance() - 1, 0));
            }

            if (data.getIgnorance() >= 100) {
                ResourceKey<Level> spawnLevel = player.getRespawnDimension();
                BlockPos spawnPos = player.getRespawnPosition();
                if (player.server == null) {
                    return;
                }
                if (spawnPos != null) {
                    if (player.server.getLevel(spawnLevel) == null) {
                        return;
                    }
                    player.teleportTo(
                            player.server.getLevel(spawnLevel),
                            spawnPos.getX(),
                            spawnPos.getY(),
                            spawnPos.getZ(),
                            Set.of(),
                            player.getYRot(),
                            player.getXRot()
                    );
                }
                player.level().playSound(null,player.blockPosition(), SoundEvents.ANVIL_PLACE, SoundSource.AMBIENT, 1.0F, 0.5F);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
                player.giveExperiencePoints(-(player.totalExperience / 2));
                data.setIgnorance(0);
            }
        });
    }
}
