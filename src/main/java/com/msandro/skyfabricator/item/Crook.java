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

public class Crook extends MiningToolItem {

    private static final TagKey<Block> EFFECTIVE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("skyfabricator:mineable/crook"));
    private static final Item[] DIRT_DROPS = { Items.OAK_SAPLING, Items.ACACIA_SAPLING, Items.SPRUCE_SAPLING,
            Items.JUNGLE_SAPLING, Items.DARK_OAK_SAPLING, Items.BIRCH_SAPLING, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS,
            Items.BEETROOT_SEEDS, Items.COCOA_BEANS, Items.SWEET_BERRIES, Items.BAMBOO, Items.SUGAR_CANE };

    public Crook(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
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

        Identifier identifier = Registry.BLOCK.getId(state.getBlock());
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
        if (path.equals("oak_leaves")) {
            stack = new ItemStack(Items.OAK_SAPLING, 1);
        }
        if (path.equals("spruce_leaves")) {
            stack = new ItemStack(Items.SPRUCE_SAPLING, 1);
        }
        if (path.equals("birch_leaves")) {
            stack = new ItemStack(Items.BIRCH_SAPLING, 1);
        }
        if (path.equals("dark_oak_leaves")) {
            stack = new ItemStack(Items.DARK_OAK_SAPLING, 1);
        }
        if (path.equals("acacia_leaves")) {
            stack = new ItemStack(Items.ACACIA_SAPLING, 1);
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