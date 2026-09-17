package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReversedIronSwordItem extends SwordItem implements IPhancyanItem {
    public ReversedIronSwordItem(Properties properties) {
        super(Tiers.IRON, 1, 1.0F, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.reversed_iron_sword.shift.1"));
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.reversed_iron_sword.shift.2"));
        }
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt.crafting"));
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!(pAttacker.level().isClientSide)) {
            if (pAttacker instanceof Player player) {
                pAttacker.hurt(player.damageSources().playerAttack(player), 1);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!(pLevel.isClientSide)) {
            if(pPlayer.hurt(pPlayer.damageSources().playerAttack(pPlayer), 1)) {
                pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, player ->
                        player.broadcastBreakEvent(pUsedHand));
            }
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public int getPhase() {
        return 2;
    }
}
