package io.github.ticketc1aw.phancyan.common.event;

import io.github.ticketc1aw.phancyan.Phancyan;
import io.github.ticketc1aw.phancyan.common.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = Phancyan.MOD_ID)
public class EmptyIngotCutEvent {
    @SubscribeEvent
    public static void EmptyIngotCut(PlayerInteractEvent.RightClickItem event) {
        if(event.getLevel().isClientSide) return;
        if(!(event.getItemStack().getItem() instanceof ShearsItem)) return;
        Level level = event.getLevel();
        LivingEntity livingEntity = event.getEntity();
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, livingEntity.getBoundingBox().inflate(3.0),itemEntity -> !itemEntity.getItem().is(ModItems.EMPTY_INGOT.get()));
        ItemEntity nearestItem = items.stream().min(Comparator.comparingDouble(livingEntity::distanceTo)).orElse(null);
        if(nearestItem == null) return;
        ItemStack stack = nearestItem.getItem();
        if(!stack.is(Items.NETHERITE_INGOT)) return;

        nearestItem.setItem(new ItemStack(ModItems.EMPTY_INGOT.get(),stack.getCount()));
        ItemStack scrapStack = new ItemStack(Items.NETHERITE_SCRAP,stack.getCount() * 2);
        Vec3 pos = nearestItem.position();
        ItemEntity scrapEntity = new ItemEntity(level,pos.x(),pos.y(),pos.z(),scrapStack);
        level.addFreshEntity(scrapEntity);
        level.playSound(null,nearestItem.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }
}
