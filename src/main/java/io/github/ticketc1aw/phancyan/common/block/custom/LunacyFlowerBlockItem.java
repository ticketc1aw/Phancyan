package io.github.ticketc1aw.phancyan.common.block.custom;

import io.github.ticketc1aw.phancyan.common.block.IPhancyanBlock;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LunacyFlowerBlockItem extends BlockItem implements IPhancyanItem {
    public LunacyFlowerBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {

        }
        pTooltip.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltip.add(Component.translatable("tooltip.phancyan.lunacy_flower.alt.1"));
        }
    }

    @Override
    public int getPhase() {
        return ((IPhancyanBlock) getBlock()).getPhase();
    }
}
