package io.github.ticketc1aw.phancyan.mixin;

import io.github.ticketc1aw.phancyan.common.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class CollisionMixin {

    @Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true)
    public void getCollisionShape(BlockGetter p_60743_, BlockPos p_60744_, CollisionContext p_60745_,
                                  CallbackInfoReturnable<VoxelShape> cir) {

        if (!(p_60745_ instanceof EntityCollisionContext)) {
            return;
        }
        if (!(((EntityCollisionContext) p_60745_).getEntity() instanceof LivingEntity)) {
            return;
        }
        if (!(((LivingEntity) ((EntityCollisionContext) p_60745_).getEntity()).hasEffect(ModEffects.PHANTOM_PHASE_EFFECT.get()))) {
            return;
        }

        cir.setReturnValue(Shapes.empty());
        cir.cancel();
    }

    @Inject(at = @At("HEAD"), method = "getVisualShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true)
    public void getVisualShape(BlockGetter p_60743_, BlockPos p_60744_, CollisionContext p_60745_,
                               CallbackInfoReturnable<VoxelShape> cir) {

        if (!(((EntityCollisionContext) p_60745_).getEntity() instanceof LivingEntity)) {
            return;
        }
        if (!(((LivingEntity) ((EntityCollisionContext) p_60745_).getEntity()).hasEffect(ModEffects.PHANTOM_PHASE_EFFECT.get()))) {
            return;
        }

        cir.setReturnValue(Shapes.empty());
        cir.cancel();
    }
}