package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoorStickItem extends Item implements IPhancyanItem {
    public DoorStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getPhase() {
        return 1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, 20);
            for (ItemStack stack : getInventory(pPlayer)) {
                if (stack.is(ItemTags.WOODEN_DOORS)) {
                    if (pPlayer.getAirSupply() == pPlayer.getMaxAirSupply()) {
                        break;
                    }
                    stack.shrink(1);
                    usingDoorStick(pLevel, pPlayer);
                    break;
                }
            }
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    public void usingDoorStick(Level level, Player player) {
        player.setAirSupply(Math.min(player.getMaxAirSupply(), player.getAirSupply() + 150));
        level.playSound(null, player.blockPosition(), SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, SoundSource.NEUTRAL, 0.4F, 1.0F);
    }

    public static List<ItemStack> getInventory(Player player) {
        ArrayList<ItemStack> inventory = new ArrayList<>(player.getInventory().offhand);
        inventory.addAll(player.getInventory().items);
        return inventory;
    }
}

