package com.msandro.skyfabricator.item;

import com.msandro.skyfabricator.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Hammer extends MiningToolItem {

    private static final TagKey<Block> EFFECTIVE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("skyfabricator:mineable/hammer"));
    private static final Item[] DIRT_DROPS = { Items.OAK_SAPLING, Items.ACACIA_SAPLING, Items.SPRUCE_SAPLING,
            Items.JUNGLE_SAPLING, Items.DARK_OAK_SAPLING, Items.BIRCH_SAPLING, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS,
            Items.BEETROOT_SEEDS, Items.COCOA_BEANS, Items.SWEET_BERRIES, Items.BAMBOO, Items.SUGAR_CANE };

    public Hammer(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super((float) attackDamage, attackSpeed, material, EFFECTIVE_BLOCKS, settings);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        if (state.isIn(EFFECTIVE_BLOCKS)) {
            return true;
        }
        return false;
    }

    public static boolean remap_drop(World world, PlayerEntity player, BlockPos pos, BlockState state) {

        Identifier identifier = net.minecraft.util.registry.Registry.BLOCK.getId(state.getBlock());
        String path = identifier.getPath();
        //System.out.println("path " + path);
        ItemStack stack = null;
        ItemStack stack2 = null;
        float chance = 1.0f;
        if (!player.getInventory().main.isEmpty()) {
            ItemStack tool = player.getInventory().getMainHandStack();
            int i = EnchantmentHelper.getLevel(Enchantments.FORTUNE, tool);
            chance += i;
        }
        // System.out.println("chance " + chance);
        if (path.equals("cobblestone") || path.equals("stone")) {
            stack = new ItemStack(Items.GRAVEL, 1);
        } else if (path.equals("gravel")) {
            stack = new ItemStack(Items.SAND, 1);
            if (world.random.nextFloat() < 0.25 * chance) {
                stack2 = new ItemStack(Items.IRON_NUGGET, (int) chance);
            }
        } else if (path.equals("sand")) {
            stack = new ItemStack(Items.CLAY_BALL, (int) chance + 3);
            if (world.random.nextFloat() < 0.1 * chance) {
                if (world.random.nextFloat() < 0.5) {
                    stack2 = new ItemStack(Items.CACTUS, 1);
                } else {
                    stack2 = new ItemStack(Items.KELP, 1);
                }
            }
        } else if (path.contains("_log")) {
            stack = new ItemStack(ModRegistry.WOODCHIPS, (int) chance);
        } else if (path.equals("dirt")) {
            stack = new ItemStack(ModRegistry.PEBBLE, (int) chance + 3);
        } else if (path.equals("podzol")) {
            stack = new ItemStack(ModRegistry.PEBBLE, (int) chance + 3);
            if (world.random.nextFloat() < 0.2) {
                if (world.random.nextFloat() < 0.5) {
                    stack2 = new ItemStack(Items.BROWN_MUSHROOM, (int) chance);
                } else {
                    stack2 = new ItemStack(Items.RED_MUSHROOM, (int) chance);
                }
            }
        } else if (path.equals("grass_block")) {
            stack = new ItemStack(ModRegistry.PEBBLE, (int) chance + 3);
            if (world.random.nextFloat() < 0.2 * chance) {
                int r = (int) Math.ceil(world.random.nextFloat() * (DIRT_DROPS.length - 1));
                if (r >= 0 && r < DIRT_DROPS.length) {
                    stack2 = new ItemStack(DIRT_DROPS[r], 1);
                }
            }
        } else if (path.equals("charcoal_block")) {
            if (world.random.nextFloat() < 0.05f * chance) {
                stack = new ItemStack(ModRegistry.DIAMOND_NUGGET);
            } else {
                stack = new ItemStack(Items.CHARCOAL, 8);
            }
        } else if (path.equals("coal_block")) {
            if (world.random.nextFloat() < 0.05f * chance) {
                stack = new ItemStack(ModRegistry.DIAMOND_NUGGET);
            } else {
                stack = new ItemStack(Items.COAL, 8);
            }
        } else if (path.equals("quartz_block")) {
            stack = new ItemStack(Items.QUARTZ, 4);
        } else if (path.equals("netherrack")) {
            if (world.random.nextFloat() < 0.1f * chance) {
                stack = new ItemStack(Items.NETHER_WART, 1);
            }
            if (world.random.nextFloat() < 0.01 * chance) {
                stack2 = new ItemStack(Items.NETHERITE_SCRAP, 1);
            } else {
                if (world.random.nextFloat() < 0.2f * chance) {
                    if (world.random.nextFloat() < 0.5f * chance) {
                        stack2 = new ItemStack(Items.CRIMSON_ROOTS, 1);
                    } else {
                        stack2 = new ItemStack(Items.WARPED_ROOTS, 1);
                    }
                }
            }
        }

        if (stack != null) {

            player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
            player.addExhaustion(0.005F);
            Block.dropStack(world, pos, stack);
            if (stack2 != null) {
                Block.dropStack(world, pos, stack2);
            }
        }
        return true;
    }
}