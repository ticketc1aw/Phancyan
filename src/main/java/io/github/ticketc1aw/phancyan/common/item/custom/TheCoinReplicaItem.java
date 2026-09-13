package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TheCoinReplicaItem extends Item implements IPhancyanItem {
    public TheCoinReplicaItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 8;
    }

    @Override
    public int getPhase() {
        return 3;
    }
}
