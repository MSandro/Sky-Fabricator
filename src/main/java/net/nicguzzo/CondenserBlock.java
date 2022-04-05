package net.nicguzzo;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CondenserBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final IntProperty LEVEL;

    public CondenserBlock() {
        super(Settings.of(Material.WOOD).nonOpaque());
        setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0).with(FACING, Direction.NORTH));
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, SkyutilsMod.CONDENSER_ENTITY, CondenserEntity::tick) : null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LEVEL, Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CondenserEntity(pos, state);
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            int i = (Integer) state.get(LEVEL);
            System.out.println(" tryDrainFluid condenser level " + i);
            if (i == 7 && (player.isHolding(Items.BUCKET) || player.isHolding(SkyutilsMod.CRUCIBLE))) {
                this.setLevel((World) world, pos, state, 0);
                CondenserEntity e = (CondenserEntity) world.getBlockEntity(pos);
                if (e != null) {
                    e.empty();
                }
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F,1.0F);
                if (player.isHolding(Items.BUCKET)) {
                    player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET, 1));
                } else if (player.isHolding(SkyutilsMod.CRUCIBLE)) {
                    player.setStackInHand(hand, new ItemStack(SkyutilsMod.WATER_CRUCIBLE, 1));
                }
            }
        }
        return ActionResult.SUCCESS;
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
