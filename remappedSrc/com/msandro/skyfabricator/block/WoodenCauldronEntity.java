package com.msandro.skyfabricator.block;

import com.msandro.skyfabricator.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WoodenCauldronEntity extends BlockEntity {

    private int time = 0;
    private int level = 0;

    public WoodenCauldronEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.CONDENSER_ENTITY, pos, state);
    }

    public int getLevel() {
        return level;
    }

    public int getTime() {
        return time;
    }

    public void incLevel() {
        if (level < 7)
            level++;
    }

    public void setTime(int t) {
        time = t;
    }

    public void incTime() {
        time++;
    }

    public void empty() {
        level = 0;
        time = 0;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        // Save the current value of the number to the tag
        tag.putInt("number", time);
        tag.putInt("level", level);
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        time = tag.getInt("number");
        level = tag.getInt("level");
    }

    public static void tick(World world, BlockPos pos, BlockState state, WoodenCauldronEntity blockEntity) {
        if (!world.isClient && state.getBlock() instanceof WoodenCauldronBlock) {
            int time_limit = 2400;
            RegistryEntry<Biome> biome = world.getBiome(pos);
            float temperature = biome.value().getTemperature();
            boolean raining = world.isRaining();

            if (temperature >= 0.95f) {
                time_limit = time_limit * 2;
            }
            if (biome.value().getPrecipitation() == Biome.Precipitation.RAIN && raining) {
                time_limit = (int) (time_limit * 0.2);
            }

            int d = time_limit / 7;
            WoodenCauldronBlock block = (WoodenCauldronBlock) state.getBlock();
            if (blockEntity.getTime() > time_limit) {
                blockEntity.setTime(0);
                if (blockEntity.getTime() % d == 0 && blockEntity.getLevel() < 7 && world.isSkyVisibleAllowingSea(pos)) {
                    blockEntity.incLevel();
                    block.incLevel(world, pos, state);
                }
            }
            blockEntity.incTime();
            blockEntity.markDirty();
        }
    }
}