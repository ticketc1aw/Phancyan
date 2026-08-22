package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class IdeaGiveEvent {

    @SubscribeEvent
    public static void IdeaGive(AdvancementEvent.AdvancementEarnEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        Advancement advancement = event.getAdvancement();
        if (advancement.getDisplay() == null || advancement.getDisplay().isHidden()) {
            return;
        }
        if (advancement.getDisplay().getFrame() == FrameType.TASK){
            player.addItem(new ItemStack(ModItems.IDEA.get(),1));
        } else if (advancement.getDisplay().getFrame() == FrameType.GOAL){
            player.addItem(new ItemStack(ModItems.IDEA.get(),3));
        } else if (advancement.getDisplay().getFrame() == FrameType.CHALLENGE){
            player.addItem(new ItemStack(ModItems.IDEA.get(),5));
        }
    }

}
