package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.block.ModBlocks;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LunacyItem extends Item implements IPhancyanItem {
    public LunacyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.shift").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasShiftDown()) {
        }
        pTooltipComponents.add(Component.translatable("tooltip.phancyan.alt").withStyle(ChatFormatting.DARK_GRAY));
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.phancyan.lunacy.alt.1"));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(BlockTags.FLOWERS) && !state.is(ModBlocks.LUNACY_FLOWER.get())) {
            if (!level.isClientSide) {
                pContext.getItemInHand().shrink(1);
                level.setBlock(pos, ModBlocks.LUNACY_FLOWER.get().defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.0F, 1.0F);
                ServerLevel serverLevel = (ServerLevel) level;
                serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 1, 3, 0.5, 0.5, 0.5, 0.02);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getPhase() {
        return 2;
    }
}
