package io.github.ticketc1aw.phancyan.mixin;

import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            method = "isStayingOnGroundSurface",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isStayingOnGroundSurface(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (player.getInventory().offhand.get(0).is(ModItems.BALANCING_TOY.get())) {
            cir.setReturnValue(true);
        }
    }

}
