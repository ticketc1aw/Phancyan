package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.capability.IgnoranceProvider;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class IgnoranceEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide) return;
        if (!(player.tickCount % 20 == 0)) return;

        player.getCapability(IgnoranceProvider.PLAYER_DATA).ifPresent(data -> {
            if (player.getInventory().contains(ModItems.THE_EYE.get().getDefaultInstance())) {
                data.setIgnorance(Math.min(data.getIgnorance() + 1, 100));
            } else {
                data.setIgnorance(Math.max(data.getIgnorance() - 1, 0));
            }

            if(data.getIgnorance() >= 100) {
                player.kill();
            }
        });
    }
}
