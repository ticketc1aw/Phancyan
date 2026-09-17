package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IdeaItem extends Item implements IPhancyanItem {
    public IdeaItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
        }
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.idea.alt.1"));
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.idea.alt.2"));
        }

    }

    @Override
    public int getPhase() {
        return 1;
    }
}