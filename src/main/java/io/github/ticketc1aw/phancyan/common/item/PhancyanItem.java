package io.github.ticketc1aw.phancyan.common.item;

import net.minecraft.world.item.Item;

public class PhancyanItem extends Item implements IPhancyanItem {

    private final int phase;

    public PhancyanItem(Properties properties, int phase) {
        super(properties);
        this.phase = phase;
    }

    @Override
    public int getPhase() {
        return phase;
    }
}