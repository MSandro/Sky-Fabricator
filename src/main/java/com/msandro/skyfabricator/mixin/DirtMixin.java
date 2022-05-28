package com.msandro.skyfabricator.mixin;

import com.msandro.skyfabricator.composter.Composter;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class DirtMixin {

    @Shadow @Final protected boolean collidable;

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit,
                      CallbackInfoReturnable<ActionResult> info) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.WHEAT_SEEDS) {
            if (state.getBlock() == Blocks.DIRT) {
                world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState());
                itemStack.decrement(1);
                world.playSound( null, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                info.setReturnValue(ActionResult.SUCCESS);
            } else if (state.getBlock() == Blocks.GRASS_BLOCK) {
                world.setBlockState(pos.up(), Blocks.GRASS.getDefaultState());
                itemStack.decrement(1);
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                info.setReturnValue(ActionResult.SUCCESS);
            } else {
                info.setReturnValue(ActionResult.PASS);
            }
        } else {
            info.setReturnValue(ActionResult.PASS);
        }

        info.cancel();
        return;
    }

}
