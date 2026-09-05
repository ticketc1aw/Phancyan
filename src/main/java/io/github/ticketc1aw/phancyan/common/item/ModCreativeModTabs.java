package io.github.ticketc1aw.phancyan.common.item;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Phancyan.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PHANCYAN_TAB = CREATIVE_MODE_TAB.register("phancyan_tab",
    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.IDEA.get()))
            .title(Component.translatable("creativetab.phancyan_tab"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.IDEA.get());
                output.accept(ModItems.LUNACY.get());
                output.accept(ModItems.EMPTY_INGOT.get());
                output.accept(ModItems.DOOR_STICK.get());
                output.accept(ModItems.DISPOSABLE_TORCH.get());
                output.accept(ModItems.OMINOUS_STEW.get());
                output.accept(ModItems.BOAT_PACKER_3000.get());
                output.accept(ModItems.REVERSED_IRON_SWORD.get());
                output.accept(ModItems.STARGAZER_LANTERN.get());

                output.accept(ModBlocks.LUNACY_FLOWER.get());
            })
            .build());

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TAB.register(modBus);
    }
}
