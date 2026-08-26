package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.effect.ModEffects;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class OminousStewItem extends Item implements IPhancyanItem {
    public OminousStewItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.addEffect(new MobEffectInstance(ModEffects.PHANTOM_PHASE_EFFECT.get(),300,0,true,true));
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,400,0,true,true));
        if (pLivingEntity instanceof Player player && player.getAbilities().instabuild) {
            return pStack;
        }
        pStack.shrink(1);
        if (pLivingEntity instanceof Player player) {
            if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
                player.drop(new ItemStack(Items.BOWL), false);
            }
        }
        return pStack;
    }

    @Override
    public int getPhase() {
        return 2;
    }
}
