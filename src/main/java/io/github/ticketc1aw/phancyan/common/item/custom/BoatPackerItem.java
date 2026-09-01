package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BoatPackerItem extends Item implements IPhancyanItem {
    public BoatPackerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        if (pLevel.isClientSide) {
            return;
        }
        if (!(pLivingEntity instanceof Player)) {
            return;
        }
        Vec3 vacuumOrigin = pLivingEntity.getEyePosition();
        Vec3 vacuumLook = pLivingEntity.getLookAngle();
        Vec3 vacuumEnd = vacuumOrigin.add(vacuumLook.scale(12.0));
        AABB vacuumAABB = new AABB(vacuumOrigin, vacuumEnd).inflate(1.0);
        List<LivingEntity> vacuumEntities = pLivingEntity.level().getEntitiesOfClass(
                LivingEntity.class,
                vacuumAABB,
                entity -> entity != pLivingEntity && entity.getBoundingBox().clip(vacuumOrigin, vacuumEnd).isPresent()
        );

        for (LivingEntity livingEntity : vacuumEntities) {
            Vec3 toPlayerVec = new Vec3(pLivingEntity.getX() - livingEntity.getX(), pLivingEntity.getY() - livingEntity.getY(), pLivingEntity.getZ() - livingEntity.getZ());
            Vec3 toPlayerVecScale = livingEntity.getDeltaMovement().add(toPlayerVec.normalize().scale(0.1F));
            if (toPlayerVecScale.length() > 1.0F) {
                toPlayerVecScale = toPlayerVecScale.normalize().scale(1.0F);
            }
            livingEntity.setDeltaMovement(toPlayerVecScale);
        }

        AABB packAABB = pLivingEntity.getBoundingBox().inflate(1.0);
        List<LivingEntity> packEntities = pLivingEntity.level().getEntitiesOfClass(
                LivingEntity.class,
                packAABB,
                entity -> entity != pLivingEntity && !entity.isPassenger()
        );
        LivingEntity packTarget = packEntities
                .stream()
                .min(Comparator.comparingDouble(entity -> entity.distanceToSqr(pLivingEntity)))
                .orElse(null);

        if (packTarget == null) {
            return;
        }

        packEntity(pLivingEntity.level(), (Player) pLivingEntity,packTarget);
    }

    public static List<ItemStack> getInventory(Player player) {
        ArrayList<ItemStack> inventory = new ArrayList<>(player.getInventory().offhand);
        inventory.addAll(player.getInventory().items);
        return inventory;
    }

    private void packEntity(Level level, Player player, LivingEntity target) {
        getInventory(player).stream()
                .filter(stack -> stack.is(ItemTags.BOATS))
                .findFirst()
                .ifPresent(stack -> {
                    Boat boat = new Boat(
                            level,
                            target.getX(),
                            target.getY(),
                            target.getZ()
                    );

                    level.addFreshEntity(boat);

                    if (!target.startRiding(boat, false)) {
                        boat.discard();
                        return;
                    }

                    level.playSound(null, player.blockPosition(), SoundEvents.BAMBOO_PLACE, SoundSource.NEUTRAL, 1.0F, 1.0F);

                    stack.shrink(1);
                });
    }

    @Override
    public int getPhase() {
        return 1;
    }
}
