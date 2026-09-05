package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ReversedIronSwordItem extends SwordItem implements IPhancyanItem {
    public ReversedIronSwordItem(Properties properties) {
        super(Tiers.IRON, 1, 1.0F, properties);
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
