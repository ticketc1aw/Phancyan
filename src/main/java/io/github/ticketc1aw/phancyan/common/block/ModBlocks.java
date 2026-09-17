package io.github.ticketc1aw.phancyan.common.block;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.block.custom.LunacyFlowerBlock;
import io.github.ticketc1aw.phancyan.common.block.custom.LunacyFlowerBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Phancyan.MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Phancyan.MOD_ID);

    public static final RegistryObject<Block> LUNACY_FLOWER = BLOCKS.register("lunacy_flower",
            () -> new LunacyFlowerBlock(BlockBehaviour.Properties.copy(Blocks.POPPY)));
    public static final RegistryObject<Item> LUNACY_FLOWER_ITEM = ITEMS.register("lunacy_flower",
            () -> new LunacyFlowerBlockItem(LUNACY_FLOWER.get(),new Item.Properties()));





    private static <T extends Block & IPhancyanBlock> RegistryObject<T> registerPhancyanBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerPhancyanBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block & IPhancyanBlock> void registerPhancyanBlockItem(String name, RegistryObject<T> block) {
        ITEMS.register(name, () -> new PhancyanBlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
