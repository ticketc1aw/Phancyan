package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LunacyItem extends Item implements IPhancyanItem {
    public LunacyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getPhase() {
        return 2;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.is(BlockTags.FLOWERS) && !state.is(Blocks.WITHER_ROSE)) {
            if (!level.isClientSide) {
                pContext.getItemInHand().shrink(1);
                level.setBlock(pos, Blocks.WITHER_ROSE.defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.0F, 1.0F);
                ServerLevel serverLevel = (ServerLevel) level;
                serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 1, 3, 0.5, 0.5, 0.5, 0.02);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
