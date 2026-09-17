package io.github.ticketc1aw.phancyan.client.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import io.github.ticketc1aw.phancyan.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class PhancyanTooltipEvents {
    @SubscribeEvent
    public static void addPhaseTooltip(ItemTooltipEvent event) {
        if(!(event.getItemStack().getItem() instanceof IPhancyanItem item)){
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }
        long gameTime = minecraft.level.getGameTime();
        int phase = item.getPhase();
        MutableComponent phaseText;

        switch (phase) {
            case 1 -> phaseText = TextUtil.sineGradient(
                    "Phancyan Phase 1",
                    gameTime,
                    0xF8C953,
                    0xF8F8F6,
                    0.125,
                    0.5
            );

            case 2 -> phaseText = TextUtil.sineGradient(
                    "Phancyan Phase 2",
                    gameTime,
                    0xBD0900,
                    0x520400,
                    0.125,
                    0.5
            );

            case 3 -> phaseText = TextUtil.sineGradient(
                    "Phancyan Phase 3",
                    gameTime,
                    0xFFFFFF,
                    0xABABAB,
                    0.125,
                    0.5
            );

            default -> phaseText = Component.literal(
                    "Phancyan Phase " + phase
            );
        }

        event.getToolTip().add(1, phaseText);
    }
}

