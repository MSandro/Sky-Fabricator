package com.msandro.skyfabricator.item;

import com.msandro.skyfabricator.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class Crook extends MiningToolItem {

    private static final TagKey<Block> EFFECTIVE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("minecraft:mineable/hoe"));

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
        ItemStack stack = null;
        float chance = 1.0f;
        if (!player.getInventory().main.isEmpty()) {
            ItemStack tool = player.getInventory().getMainHandStack();
            int i = EnchantmentHelper.getLevel(Enchantments.FORTUNE, tool);
            chance += i;
        }
        if (path.endsWith("_leaves")) {
            stack = new ItemStack(Registry.ITEM.get(new Identifier(identifier.getNamespace(), path.replace("_leaves", "_sapling"))), (int)chance);
        }

        if (stack != null) {
            player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
            player.addExhaustion(0.005F);
            Block.dropStack(world, pos, stack);
        }
        return true;
    }
}