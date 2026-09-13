package io.github.ticketc1aw.phancyan.common.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.ticketc1aw.phancyan.common.item.IPhancyanItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class BalancingToyItem extends Item implements IPhancyanItem {
    public BalancingToyItem(Item.Properties properties) {
        super(properties);
    }

    private static final UUID KB_RESIST_UUID = UUID.fromString("26ff7587-add7-46a5-be53-f721c0850459");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.OFFHAND) {
            return super.getAttributeModifiers(slot, stack);
        }
        return ImmutableMultimap.of(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(KB_RESIST_UUID, "clay", 0.5, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public int getPhase() {
        return 1;
    }
}
