package io.github.ticketc1aw.phancyan.common.item;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.custom.DisposableTorchItem;
import io.github.ticketc1aw.phancyan.common.item.custom.DoorStickItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Phancyan.MOD_ID);

    public static final RegistryObject<Item> IDEA = ITEMS.register("idea",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUNACY = ITEMS.register("lunacy",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_INGOT = ITEMS.register("empty_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DOOR_STICK = ITEMS.register("door_stick",
            () -> new DoorStickItem(new Item.Properties()));
    public static final RegistryObject<Item> DISPOSABLE_TORCH = ITEMS.register("disposable_torch",
            () -> new DisposableTorchItem(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
