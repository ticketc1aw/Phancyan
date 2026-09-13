package io.github.ticketc1aw.phancyan.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.ticketc1aw.phancyan.common.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @Inject(at = @At("HEAD"), method = "renderScreenEffect", cancellable = true)
    private static void renderScreenEffect(Minecraft pMinecraft, PoseStack pPoseStack, CallbackInfo cir) {
        Player player = pMinecraft.player;
        if (Objects.requireNonNull(player).hasEffect(ModEffects.PHANTOM_PHASE_EFFECT.get())) {
            cir.cancel();
        }
    }

}
