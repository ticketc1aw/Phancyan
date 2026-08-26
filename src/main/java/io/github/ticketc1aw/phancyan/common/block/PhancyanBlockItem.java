package io.github.ticketc1aw.phancyan.common.block;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PhancyanBlockItem extends BlockItem implements IPhancyanItem {

    public PhancyanBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public int getPhase() {
        if (getBlock() instanceof IPhancyanBlock phancyanBlock) {
            return phancyanBlock.getPhase();
        }
        return 0;
    }
}
