package com.someguyssoftware.treasure2.model;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.TreasureBlocks;
import com.someguyssoftware.treasure2.item.TreasureItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Treasure.MODID, value = Side.CLIENT)
public class TreasureModels {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		// TAB
		registerItemModel(TreasureItems.TREASURE_TAB);
		registerItemModel(TreasureItems.ADORNMENTS_TAB);
		// There isn't a block model json for chests so you won't be able to get the
		// item from block.
		// CHESTS
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WOOD_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.CRATE_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.MOLDY_CRATE_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.IRONBOUND_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.PIRATE_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.IRON_STRONGBOX));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GOLD_STRONGBOX));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.SAFE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.DREAD_PIRATE_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.COMPRESSOR_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GOLD_SKULL_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.SKULL_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.CRYSTAL_SKULL_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.CAULDRON_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.SPIDER_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.VIKING_CHEST));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.CARDBOARD_BOX));
        registerItemModel(Item.getItemFromBlock(TreasureBlocks.MILK_CRATE));
        
		// MIMICS
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WOOD_MIMIC));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.PIRATE_MIMIC));

		// GRAVESONES
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_STONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_MOSSY_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_POLISHED_GRANITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_POLISHED_ANDESITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_POLISHED_DIORITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_OBSIDIAN));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_STONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_MOSSY_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_POLISHED_GRANITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_POLISHED_ANDESITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_POLISHED_DIORITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_OBSIDIAN));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_STONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_MOSSY_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_POLISHED_GRANITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_POLISHED_ANDESITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_POLISHED_DIORITE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_OBSIDIAN));

		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE1_SPAWNER_STONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE2_SPAWNER_COBBLESTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.GRAVESTONE3_SPAWNER_OBSIDIAN));
		
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.SKULL_CROSSBONES));
		registerItemModel(TreasureItems.SKELETON);

		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WISHING_WELL_BLOCK));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.DESERT_WISHING_WELL_BLOCK));

		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_LOG));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_BROKEN_LOG));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_LOG_SOUL));
//		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_BRANCH));
//		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_ROOT));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.WITHER_PLANKS));

        // ORES
        registerItemModel(Item.getItemFromBlock(TreasureBlocks.AMETHYST_ORE));
        registerItemModel(Item.getItemFromBlock(TreasureBlocks.ONYX_ORE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.SAPPHIRE_ORE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.RUBY_ORE));
				
		// OTHER
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.FALLING_GRASS));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.FALLING_SAND));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.FALLING_RED_SAND));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.BLACKSTONE));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.JEWELER_BENCH));
		registerItemModel(Item.getItemFromBlock(TreasureBlocks.CHARMING_TABLE));
		
		// COINS
		registerItemModel(TreasureItems.GOLD_COIN);
		registerItemModel(TreasureItems.SILVER_COIN);
		registerItemModel(TreasureItems.COPPER_COIN);
		
		// GEMS
        registerItemModel(TreasureItems.SPANISH_MOSS);
        registerItemModel(TreasureItems.AMETHYST);
        registerItemModel(TreasureItems.ONYX);
		registerItemModel(TreasureItems.SAPPHIRE);
		registerItemModel(TreasureItems.RUBY);
		registerItemModel(TreasureItems.WHITE_PEARL);
		registerItemModel(TreasureItems.BLACK_PEARL);
		
//		registerItemModel(TreasureItems.MINERS_FRIEND);		
//		registerItemModel(TreasureItems.DWARVEN_TALISMAN);
				
		// POUCHES
		registerItemModel(TreasureItems.POUCH);
		
		// ADORNMENTS
        registerItemModel(TreasureItems.ANGELS_RING);
        registerItemModel(TreasureItems.RING_OF_FORTITUDE);
        registerItemModel(TreasureItems.PEASANTS_FORTUNE);        
        registerItemModel(TreasureItems.CASTLE_RING);
        registerItemModel(TreasureItems.BRACELET_OF_WONDER); 
        registerItemModel(TreasureItems.SHADOWS_GIFT);
        registerItemModel(TreasureItems.RING_OF_LIFE_DEATH); 
        registerItemModel(TreasureItems.MEDICS_TOKEN);
        registerItemModel(TreasureItems.SALANDAARS_WARD);
        registerItemModel(TreasureItems.ADEPHAGIAS_BOUNTY);
		registerItemModel(TreasureItems.MIRTHAS_TORCH);
        registerItemModel(TreasureItems.POCKET_WATCH);

        registerItemModel(TreasureItems.GOTTSCHS_RING_OF_MOON);
        registerItemModel(TreasureItems.GOTTSCHS_AMULET_OF_HEAVENS);
//      registerItemModel(TreasureItems.SILVER_SIGNET_RING);
        
        // RUNESTONES
        registerItemModel(TreasureItems.MANA_RUNESTONE);
        registerItemModel(TreasureItems.GREATER_MANA_RUNESTONE);
        registerItemModel(TreasureItems.DURABILITY_RUNESTONE);
        registerItemModel(TreasureItems.QUALITY_RUNESTONE);
        registerItemModel(TreasureItems.EQUIP_MANA_RUNESTONE);
        registerItemModel(TreasureItems.ANVIL_RUNESTONE);
        registerItemModel(TreasureItems.ANGELS_RUNESTONE);
        registerItemModel(TreasureItems.PERSISTENCE_RUNESTONE);
        registerItemModel(TreasureItems.SOCKETS_RUNESTONE);
        registerItemModel(TreasureItems.DOUBLE_CHARGE_RUNESTONE);
        
        // GENERATED ITEMS
		TreasureItems.ITEMS.forEach((key, value) -> {
			registerItemModel(value);
		});
				
		// LOCKS
		registerItemModel(TreasureItems.WOOD_LOCK);
		registerItemModel(TreasureItems.STONE_LOCK);
		registerItemModel(TreasureItems.EMBER_LOCK);
		registerItemModel(TreasureItems.LEAF_LOCK);
		registerItemModel(TreasureItems.IRON_LOCK);
		registerItemModel(TreasureItems.GOLD_LOCK);
		registerItemModel(TreasureItems.DIAMOND_LOCK);
		registerItemModel(TreasureItems.EMERALD_LOCK);
		registerItemModel(TreasureItems.RUBY_LOCK);
		registerItemModel(TreasureItems.SAPPHIRE_LOCK);
		registerItemModel(TreasureItems.SPIDER_LOCK);
		registerItemModel(TreasureItems.WITHER_LOCK);

		// KEYS
		registerItemModel(TreasureItems.WOOD_KEY);
		registerItemModel(TreasureItems.STONE_KEY);
		registerItemModel(TreasureItems.EMBER_KEY);
		registerItemModel(TreasureItems.LEAF_KEY);
		registerItemModel(TreasureItems.LIGHTNING_KEY);
		registerItemModel(TreasureItems.IRON_KEY);
		registerItemModel(TreasureItems.GOLD_KEY);
		registerItemModel(TreasureItems.DIAMOND_KEY);
		registerItemModel(TreasureItems.EMERALD_KEY);
		registerItemModel(TreasureItems.RUBY_KEY);
		registerItemModel(TreasureItems.SAPPHIRE_KEY);
		registerItemModel(TreasureItems.JEWELLED_KEY);
		registerItemModel(TreasureItems.METALLURGISTS_KEY);
		registerItemModel(TreasureItems.SKELETON_KEY);
		registerItemModel(TreasureItems.SPIDER_KEY);
		registerItemModel(TreasureItems.WITHER_KEY);
		registerItemModel(TreasureItems.PILFERERS_LOCK_PICK);
		registerItemModel(TreasureItems.THIEFS_LOCK_PICK);
		registerItemModel(TreasureItems.KEY_RING);
		

		// WITHER ITEMS
		registerItemModel(TreasureItems.WITHER_STICK_ITEM);
		registerItemModel(TreasureItems.WITHER_ROOT_ITEM);

		// WEAPONS
		registerItemModel(TreasureItems.SKULL_SWORD);
		registerItemModel(TreasureItems.COPPER_SHORT_SWORD);
		registerItemModel(TreasureItems.CHIPPED_COPPER_SHORT_SWORD);
		registerItemModel(TreasureItems.IRON_SHORT_SWORD);
		registerItemModel(TreasureItems.CHIPPED_IRON_SHORT_SWORD);
		registerItemModel(TreasureItems.STEEL_SHORT_SWORD);
		registerItemModel(TreasureItems.CHIPPED_STEEL_SHORT_SWORD);
		registerItemModel(TreasureItems.COPPER_RAPIER);
		registerItemModel(TreasureItems.STEEL_SWORD);
		registerItemModel(TreasureItems.SWORD_OF_POWER);
		registerItemModel(TreasureItems.BLACK_SWORD);
		registerItemModel(TreasureItems.OATHBRINGER);
		registerItemModel(TreasureItems.SWORD_OF_OMENS);
		registerItemModel(TreasureItems.CALLANDOR);
		registerItemModel(TreasureItems.IRON_BROADSWORD);
		registerItemModel(TreasureItems.STEEL_BROADSWORD);
		registerItemModel(TreasureItems.ORCUS);
		registerItemModel(TreasureItems.SNAKES_EYES_KATANA);
		registerItemModel(TreasureItems.STORM_SHADOWS_KATANA);
		registerItemModel(TreasureItems.STEEL_MACHETE);
		registerItemModel(TreasureItems.SHADOW_MACHETE);
		registerItemModel(TreasureItems.IRON_FALCHION);
		registerItemModel(TreasureItems.STEEL_FALCHION);
		registerItemModel(TreasureItems.SHADOW_FALCHION);
		registerItemModel(TreasureItems.IRON_MACE);
		registerItemModel(TreasureItems.STEEL_MACE);
		registerItemModel(TreasureItems.MJOLNIR);
		registerItemModel(TreasureItems.COPPER_BROAD_AXE);
		registerItemModel(TreasureItems.IRON_BROAD_AXE);
		registerItemModel(TreasureItems.STEEL_BROAD_AXE);
		registerItemModel(TreasureItems.IRON_DWARVEN_AXE);
		registerItemModel(TreasureItems.AXE_OF_DURIN);
		registerItemModel(TreasureItems.HEADSMANS_AXE);
		registerItemModel(TreasureItems.EYE_PATCH);

		// PAINTINGS
		registerItemModel(TreasureItems.PAINTING_BLOCKS_BRICKS);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_COBBLESTONE);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_DIRT);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_LAVA);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_SAND);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_WATER);
		registerItemModel(TreasureItems.PAINTING_BLOCKS_WOOD);

		// OTHER
		registerItemModel(TreasureItems.CHARM_BOOK);
		registerItemModel(TreasureItems.TREASURE_TOOL);
	}

	/**
	 * Register the default model for an {@link Item}.
	 *
	 * @param item The item
	 */
	private static void registerItemModel(Item item) {
		final ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");
		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> location));
	}

	/**
	 * Register the {@link IBlockColor} handlers.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) {
		final BlockColors blockColors = event.getBlockColors();

		// Use the grass colour of the biome or the default grass colour
		final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			if (blockAccess != null && pos != null) {
				return BiomeColorHelper.getGrassColorAtPos(blockAccess, pos);
			}

			return ColorizerGrass.getGrassColor(0.5D, 1.0D);
		};

		blockColors.registerBlockColorHandler(grassColourHandler, TreasureBlocks.FALLING_GRASS);
	}

	/**
	 * Register the {@link IItemColor} handlers
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
		final BlockColors blockColors = event.getBlockColors();
		final ItemColors itemColors = event.getItemColors();

		// Use the Block's colour handler for an ItemBlock
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			@SuppressWarnings("deprecation")
			final IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return blockColors.colorMultiplier(state, null, null, tintIndex);
		};

		itemColors.registerItemColorHandler(itemBlockColourHandler, TreasureBlocks.FALLING_GRASS);
	}
}
