package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.capability.IgnoranceProvider;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheEyeItem extends Item implements IPhancyanItem {
    public TheEyeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.the_eye.shift.1"));
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.the_eye.shift.2"));
        }
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt.crafting"));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide) {
            if (!(pEntity instanceof Player pPlayer)) {
                return;
            }
            pPlayer.getCapability(IgnoranceProvider.PLAYER_DATA).ifPresent(data -> {
                pPlayer.displayClientMessage(Component.literal("Ignorance:"+ data.getIgnorance()),true);
            });
            pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, true));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, true, true));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, 0, true, true));
            pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, true, true));
        }
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getPhase() {
        return 3;
    }
}
