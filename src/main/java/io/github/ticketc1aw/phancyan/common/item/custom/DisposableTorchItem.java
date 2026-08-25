package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DisposableTorchItem extends FlintAndSteelItem implements IPhancyanItem {
    public DisposableTorchItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public int getPhase() {
        return 1;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide) {
            pTarget.setSecondsOnFire(8);
            pStack.shrink(1);
        }
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            ItemStack itemstack = pContext.getItemInHand();
            Player player = pContext.getPlayer();
            itemstack.shrink(1);
            Objects.requireNonNull(player).getCooldowns().addCooldown(this, 5);
            return super.useOn(pContext);
        } else  {
            return super.useOn(pContext);
        }
    }
}
