package com.msandro.skyfabricator.block;

import java.util.Optional;

import com.msandro.skyfabricator.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WoodenCauldronBlock extends Block implements BlockEntityProvider, FluidDrainable {
    public static final IntProperty LEVEL;

    public WoodenCauldronBlock() {
        super(Settings.of(Material.WOOD).nonOpaque());
        setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, ModRegistry.CONDENSER_ENTITY, WoodenCauldronEntity::tick) : null;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Fluids.WATER.getBucketFillSound();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LEVEL);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState();
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenCauldronEntity(pos, state);
    }

    public void setLevel(World world, BlockPos pos, BlockState state, int level) {

        world.setBlockState(pos, (BlockState) state.with(LEVEL, MathHelper.clamp(level, 0, 7)), 2);
        world.updateNeighbors(pos, this);
    }

    public void incLevel(World world, BlockPos pos, BlockState state) {
        int level = ((Integer) state.get(LEVEL)) + 1;
        world.setBlockState(pos, (BlockState) state.with(LEVEL, MathHelper.clamp(level, 0, 7)), 2);
        world.updateNeighbors(pos, this);
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if (!world.isClient()) {
            int i = state.get(LEVEL);
            if (i == 7) {
                this.setLevel((World) world, pos, state, 0);
                WoodenCauldronEntity e = (WoodenCauldronEntity) world.getBlockEntity(pos);
                if (e != null) {
                    e.empty();
                }
                return new ItemStack(Items.WATER_BUCKET);
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType,
            BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (@Nullable BlockEntityTicker<A>) ticker : null;
    }

    static {
        LEVEL = IntProperty.of("level", 0, 7);
    }
}
