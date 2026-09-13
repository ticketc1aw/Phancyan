package io.github.ticketc1aw.phancyan.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class IgnoranceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<IIgnorance> PLAYER_DATA =
            CapabilityManager.get(new CapabilityToken<>() {
            });

    private final IIgnorance data = new Ignorance();

    private final LazyOptional<IIgnorance> optional =
            LazyOptional.of(() -> data);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(
            @NotNull Capability<T> capability,
            Direction side
    ) {
        if (capability == PLAYER_DATA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("ignorance", data.getIgnorance());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        data.setIgnorance(tag.getInt("ignorance"));
    }
}