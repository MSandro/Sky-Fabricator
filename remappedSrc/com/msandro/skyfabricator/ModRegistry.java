package com.msandro.skyfabricator;

import com.msandro.skyfabricator.block.WoodenCauldronBlock;
import com.msandro.skyfabricator.block.WoodenCauldronEntity;
import com.msandro.skyfabricator.block.kiln.KilnBlock;
import com.msandro.skyfabricator.block.kiln.KilnBlockEntity;
import com.msandro.skyfabricator.block.kiln.KilnScreenHandler;
import com.msandro.skyfabricator.item.Crucible;
import com.msandro.skyfabricator.item.Hammer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModRegistry {

	public static final Block CHARCOAL_BLOCK = new Block(
			AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).strength(5.0F, 6.0F));
	public static final BlockItem CHARCOAL_BLOCK_ITEM = new BlockItem(CHARCOAL_BLOCK,
			new Item.Settings().group(ItemGroup.MISC));
	public static final Item DIAMOND_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item WOODCHIPS = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item PEBBLE = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item RAW_CRUCIBLE = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Crucible CRUCIBLE = new Crucible(Fluids.EMPTY, new Item.Settings().group(ItemGroup.MISC));
	public static final Crucible WATER_CRUCIBLE = new Crucible(Fluids.WATER,
			new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Crucible LAVA_CRUCIBLE = new Crucible(Fluids.LAVA,
			new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Hammer WOODEN_HAMMER = new Hammer(ToolMaterials.WOOD, 6, -2.8F,
			(new Item.Settings()).group(ItemGroup.TOOLS));
	public static final Hammer STONE_HAMMER = new Hammer(ToolMaterials.STONE, 6, -2.8F,
			(new Item.Settings()).group(ItemGroup.TOOLS));
	public static final Hammer IRON_HAMMER = new Hammer(ToolMaterials.IRON, 6, -2.8F,
			(new Item.Settings()).group(ItemGroup.TOOLS));
	public static final Hammer DIAMOND_HAMMER = new Hammer(ToolMaterials.DIAMOND, 6, -2.8F,
			(new Item.Settings()).group(ItemGroup.TOOLS));
	public static final Hammer NETHERITE_HAMMER = new Hammer(ToolMaterials.NETHERITE, 6, -2.8F,
			(new Item.Settings()).group(ItemGroup.TOOLS));

	// KILN
	public static BlockEntityType<KilnBlockEntity> KILN_ENTITY_TYPE;
	public static final Block KILN_BLOCK = new KilnBlock(AbstractBlock.Settings.of(Material.STONE).strength(3.5F, 3.5F));
	public static final BlockEntityType<KilnBlockEntity> KILN_ENTITY = FabricBlockEntityTypeBuilder
			.create(KilnBlockEntity::new, KILN_BLOCK).build(null);
	public static final BlockItem KILN_BLOCK_ITEM = new BlockItem(KILN_BLOCK,
			new Item.Settings().group(ItemGroup.REDSTONE));
	public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER = ScreenHandlerRegistry
			.registerSimple(Skyfabricator.id("kiln"), KilnScreenHandler::new);

	// CONDENSER
	public static Block CONDENSER_BLOCK = new WoodenCauldronBlock();
	public static BlockEntityType<WoodenCauldronEntity> CONDENSER_ENTITY = FabricBlockEntityTypeBuilder
			.create(WoodenCauldronEntity::new, CONDENSER_BLOCK).build(null);
	public static BlockItem CONDENSER_BLOCK_ITEM = new BlockItem(CONDENSER_BLOCK,
			new Item.Settings().group(ItemGroup.MISC));

	public static void init() {

		// items
		Registry.register(Registry.ITEM, Skyfabricator.id("wooden_hammer"), WOODEN_HAMMER);
		Registry.register(Registry.ITEM, Skyfabricator.id("stone_hammer"), STONE_HAMMER);
		Registry.register(Registry.ITEM, Skyfabricator.id("iron_hammer"), IRON_HAMMER);
		Registry.register(Registry.ITEM, Skyfabricator.id("diamond_hammer"), DIAMOND_HAMMER);
		Registry.register(Registry.ITEM, Skyfabricator.id("netherite_hammer"), NETHERITE_HAMMER);
		Registry.register(Registry.ITEM, Skyfabricator.id("woodchips"), WOODCHIPS);
		Registry.register(Registry.ITEM, Skyfabricator.id("pebble"), PEBBLE);
		Registry.register(Registry.ITEM, Skyfabricator.id("raw_crucible"), RAW_CRUCIBLE);
		Registry.register(Registry.ITEM, Skyfabricator.id("crucible"), CRUCIBLE);
		Registry.register(Registry.ITEM, Skyfabricator.id("water_crucible"), WATER_CRUCIBLE);
		Registry.register(Registry.ITEM, Skyfabricator.id("lava_crucible"), LAVA_CRUCIBLE);
		Registry.register(Registry.ITEM, Skyfabricator.id("diamond_nugget"), DIAMOND_NUGGET);

		// blocks

		// charcoal block
		Registry.register(Registry.BLOCK, Skyfabricator.id("charcoal_block"), CHARCOAL_BLOCK);
		Registry.register(Registry.ITEM, Skyfabricator.id("charcoal_block"), CHARCOAL_BLOCK_ITEM);

		FuelRegistry registry = FuelRegistry.INSTANCE;
		registry.add(CHARCOAL_BLOCK_ITEM, 1600);

		// kiln
		Registry.register(Registry.BLOCK, Skyfabricator.id("kiln"), KILN_BLOCK);
		Registry.register(Registry.ITEM, Skyfabricator.id("kiln"), KILN_BLOCK_ITEM);
		KILN_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, Skyfabricator.id("kiln"), KILN_ENTITY);

		//condenser
		Registry.register(net.minecraft.util.registry.Registry.BLOCK, Skyfabricator.id("wooden_cauldron"), CONDENSER_BLOCK);
		Registry.register(net.minecraft.util.registry.Registry.ITEM, Skyfabricator.id("wooden_cauldron"), CONDENSER_BLOCK_ITEM);
		CONDENSER_ENTITY = net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK_ENTITY_TYPE, Skyfabricator.id("wooden_cauldron"), CONDENSER_ENTITY);
	}
}