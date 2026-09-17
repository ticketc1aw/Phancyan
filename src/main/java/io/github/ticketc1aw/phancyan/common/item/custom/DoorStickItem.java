package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DoorStickItem extends Item implements IPhancyanItem {
    public DoorStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.door_stick.shift.1"));
        }
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt.crafting"));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            for (ItemStack stack : getInventory(pPlayer)) {
                if (stack.is(ItemTags.WOODEN_DOORS)) {
                    if (pPlayer.getAirSupply() == pPlayer.getMaxAirSupply()) {
                        break;
                    }
                    pPlayer.getCooldowns().addCooldown(this, 20);
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

    @Override
    public int getPhase() {
        return 1;
    }
}

