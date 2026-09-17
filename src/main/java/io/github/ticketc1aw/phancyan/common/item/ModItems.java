package io.github.ticketc1aw.phancyan.common.item;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Phancyan.MOD_ID);

    public static final RegistryObject<Item> IDEA = ITEMS.register("idea",
            () -> new IdeaItem(new Item.Properties()));
    public static final RegistryObject<Item> LUNACY = ITEMS.register("lunacy",
            () -> new LunacyItem(new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_INGOT = ITEMS.register("empty_ingot",
            () -> new EmptyIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> DOOR_STICK = ITEMS.register("door_stick",
            () -> new DoorStickItem(new Item.Properties()));
    public static final RegistryObject<Item> DISPOSABLE_TORCH = ITEMS.register("disposable_torch",
            () -> new DisposableTorchItem(new Item.Properties()));
    public static final RegistryObject<Item> OMINOUS_STEW = ITEMS.register("ominous_stew",
            () -> new OminousStewItem(new Item.Properties()));
    public static final RegistryObject<Item> BOAT_PACKER_3000 = ITEMS.register("boat_packer_3000",
            () -> new BoatPackerItem(new Item.Properties()));
    public static final RegistryObject<Item> REVERSED_IRON_SWORD = ITEMS.register("reversed_iron_sword",
            () -> new ReversedIronSwordItem(new Item.Properties()));
    public static final RegistryObject<Item> STARGAZER_LANTERN = ITEMS.register("stargazer_lantern",
            () -> new StargazerLanternItem(new Item.Properties()));
    public static final RegistryObject<Item> BALANCING_TOY = ITEMS.register("balancing_toy",
            () -> new BalancingToyItem(new Item.Properties()));
    public static final RegistryObject<Item> THE_EYE = ITEMS.register("the_eye",
            () -> new TheEyeItem(new Item.Properties()));
    public static final RegistryObject<Item> THE_COIN_REPLICA = ITEMS.register("the_coin_replica",
            () -> new TheCoinReplicaItem(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
