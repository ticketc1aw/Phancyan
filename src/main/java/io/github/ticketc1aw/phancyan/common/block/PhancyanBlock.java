package io.github.ticketc1aw.phancyan.common.block;

import net.minecraft.world.level.block.Block;

public class PhancyanBlock extends Block implements IPhancyanBlock {
    private final int phase;

    public PhancyanBlock(Properties properties, int phase) {
        super(properties);
        this.phase = phase;
    }

    @Override
    public int getPhase() {
        return phase;
    }
}
