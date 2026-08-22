package io.github.ticketc1aw.phancyan.common.event;


import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class LunacyGiveEvent {
    @SubscribeEvent
    public static void LunacyGive(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide()) return;
        if (!level.canSeeSky(player.blockPosition())) return;
        if (!player.getUseItem().is(Items.SPYGLASS)) return;
        if (player.getXRot() > -89.5f) return;
        if (!(level.getDayTime() % 24000 == 18000)) return;

        player.addItem(new ItemStack(ModItems.LUNACY.get(),1));
    }

}

