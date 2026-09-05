package io.github.ticketc1aw.phancyan.common.item.custom;

import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public class StargazerLanternItem extends Item implements IPhancyanItem {
    public StargazerLanternItem(Item.Properties properties) {
        super(properties);
    }

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
        if (!(pLivingEntity instanceof Player pPlayer)) {
            return;
        }

        int elapsed = getUseDuration(pStack) - pRemainingUseDuration;
        if (elapsed > 50) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, 0, false, false));
        }
        if (elapsed == 80) {
            if (!(pPlayer.getOffhandItem().is(Items.COMPASS) || pPlayer.getOffhandItem().is(Items.RECOVERY_COMPASS))) {
                pPlayer.sendSystemMessage(Component.literal("指針になる物を持っていない..."));
                pLevel.playSound(null,pPlayer.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 1.0F, 1.0F);
                return;
            }
            Optional<GlobalPos> CompassPos = getCompassPos(pLevel, pPlayer, pPlayer.getOffhandItem());
            if (CompassPos.isEmpty()) {
                pPlayer.sendSystemMessage(Component.literal("星々は指針の先を照らせない..."));
                pLevel.playSound(null,pPlayer.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 1.0F, 1.0F);
                return;
            }
            long currentTime = pLevel.getDayTime() % 24000;
            long timeCost = (long) calculateDistance(pPlayer.blockPosition(), CompassPos.get().pos());
            if (!canTravelAtNight(timeCost, currentTime)) {
                pPlayer.sendSystemMessage(Component.literal("星々が眠る前に指針の先へ辿り着けない..."));
                pLevel.playSound(null,pPlayer.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 1.0F, 1.0F);
                return;
            }
            pPlayer.sendSystemMessage(Component.literal("星々が指針の先を照らした！"));
            pPlayer.sendSystemMessage(Component.literal(
                    "現在時刻:"
                            + String.format(
                            "%02d:%02d",
                            getHour(currentTime),
                            getMinute(currentTime)
                    )
            ));
            pPlayer.sendSystemMessage(Component.literal(
                    "予想時刻:"
                            + String.format(
                            "%02d:%02d",
                            getHour(currentTime + timeCost),
                            getMinute(currentTime + timeCost)
                    )
            ));
            pLevel.playSound(null,pPlayer.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.AMBIENT, 1.0F, 1.0F);
        }
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLevel.isClientSide) {
            return;
        }
        if (!(pLivingEntity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        int elapsed = getUseDuration(pStack) - pTimeCharged;
        if (elapsed < 80) {
            return;
        }
        if (!(serverPlayer.getOffhandItem().is(Items.COMPASS) || serverPlayer.getOffhandItem().is(Items.RECOVERY_COMPASS))) {
            return;
        }
        Optional<GlobalPos> CompassPos = getCompassPos(pLevel, serverPlayer, serverPlayer.getOffhandItem());
        if (CompassPos.isEmpty()) {
            return;
        }
        long currentTime = pLevel.getDayTime() % 24000;
        BlockPos targetPos = CompassPos.get().pos();
        ServerLevel targetLevel = serverPlayer.getServer().getLevel(CompassPos.get().dimension());
        long timeCost = (long) calculateDistance(
                serverPlayer.blockPosition(),
                targetPos
        );
        if (!canTravelAtNight(timeCost, currentTime)) {
            return;
        }
        if (!(pLevel instanceof ServerLevel serverLevel)) {
            return;
        }
        if (targetLevel == null) {
            return;
        }
        serverLevel.setDayTime(serverLevel.getDayTime() + timeCost);
        serverPlayer.teleportTo(
                targetLevel,
                targetPos.getX() + 0.5,
                targetPos.getY(),
                targetPos.getZ() + 0.5,
                Set.of(),
                serverPlayer.getYRot(),
                serverPlayer.getXRot()
        );
        targetLevel.playSound(null,serverPlayer.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.AMBIENT, 1.0F, 1.5F);
    }

    public Optional<GlobalPos> getCompassPos(Level pLevel, Player pPlayer, ItemStack pStack) {
        if (pStack.is(Items.COMPASS)) {
            CompoundTag tag = pStack.getTag();
            if (tag != null && tag.contains("LodestonePos")) {
                CompoundTag posTag = tag.getCompound("LodestonePos");

                return Optional.of(GlobalPos.of(ResourceKey.create(
                                Registries.DIMENSION, ResourceLocation.parse(tag.getString("LodestoneDimension"))),
                        new BlockPos(posTag.getInt("X"), posTag.getInt("Y"), posTag.getInt("Z"))));
            } else {
                return Optional.of(GlobalPos.of(
                        Level.OVERWORLD,
                        pLevel.getSharedSpawnPos()
                ));
            }
        } else if (pStack.is(Items.RECOVERY_COMPASS)) {
            return pPlayer.getLastDeathLocation();
        }
        return Optional.empty();
    }

    public double calculateDistance(BlockPos from, BlockPos to) {
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        double dz = from.getZ() - to.getZ();
        return Math.sqrt(
                dx * dx + dy * dy + dz * dz
        );
    }

    private boolean canTravelAtNight(long timeCost, long currentTime) {
        long predictedTime = currentTime + timeCost;

        boolean currentIsNight = currentTime >= 13000 && currentTime < 23000;
        boolean predictedIsNight = predictedTime >= 13000 && predictedTime < 23000;
        boolean crossesDay = predictedTime >= 24000;

        return currentIsNight && predictedIsNight && !crossesDay;
    }

    private static int getHour(long tick) {
        long time = (tick + 6000) % 24000;
        return (int) (time / 1000);
    }

    private static int getMinute(long tick) {
        long time = (tick + 6000) % 24000;
        return (int) ((time % 1000) * 60 / 1000);
    }


    @Override
    public int getPhase() {
        return 2;
    }
}
