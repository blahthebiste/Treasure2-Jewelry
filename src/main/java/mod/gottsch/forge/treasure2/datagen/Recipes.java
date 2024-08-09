/*
 * This file is part of  Treasure2.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2.datagen;

import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.forge.treasure2.core.item.TreasureItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

/**
 *
 * @author Mark Gottschling on Nov 26, 2022
 *
 */
public class Recipes extends RecipeProvider {

	public Recipes(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipe) {
		/*
		 * treasure tool
		 */
		ShapedRecipeBuilder.shaped(TreasureItems.TREASURE_TOOL.get())
				.pattern("  i")
				.pattern(" s ")
				.pattern("x  ")
				.define('i', Items.IRON_INGOT)
				.define('s', Items.STICK)
				.define('x', Items.STONE)
				.unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
				.save(recipe);

		// pilferer's lock pick
		ShapedRecipeBuilder.shaped(TreasureItems.PILFERERS_LOCK_PICK.get())
				.pattern("xt")
				.pattern("x ")
				.define('x', Items.IRON_NUGGET)
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// thief's lock pick
		ShapedRecipeBuilder.shaped(TreasureItems.THIEFS_LOCK_PICK.get())
				.pattern("xt")
				.pattern("x ")
				.define('x', Items.IRON_INGOT)
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// keyring
		ShapedRecipeBuilder.shaped(TreasureItems.KEY_RING.get())
				.pattern("kx ")
				.pattern("x x")
				.pattern(" x ")
				.define('x', Items.IRON_INGOT)
				.define('k', Ingredient.of(TreasureItems.WOOD_KEY.get(),
						TreasureItems.STONE_KEY.get(),
						TreasureItems.IRON_KEY.get()))
				.unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
				.save(recipe);

		// pouch
		ShapedRecipeBuilder.shaped(TreasureItems.POUCH.get())
				.pattern(" ct")
				.pattern(" x ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('c', Ingredient.of(
						TreasureItems.COPPER_COIN.get(),
						TreasureItems.SILVER_COIN.get(),
						TreasureItems.GOLD_COIN.get()))
				.define('x', Items.LEATHER)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// spider key
		ShapedRecipeBuilder.shaped(TreasureItems.SPIDER_KEY.get())
				.pattern("kt ")
				.pattern(" d ")
				.pattern(" e ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('k', TreasureItems.IRON_KEY.get())
				.define('d', Items.GLOWSTONE_DUST)
				.define('e', Items.SPIDER_EYE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// topaz key
		ShapedRecipeBuilder.shaped(TreasureItems.TOPAZ_KEY.get())
				.pattern("kt ")
				.pattern(" d ")
				.pattern(" g ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('k', TreasureItems.GOLD_KEY.get())
				.define('d', Items.GLOWSTONE_DUST)
				.define('g', TreasureItems.TOPAZ.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// onyx key
		ShapedRecipeBuilder.shaped(TreasureItems.ONYX_KEY.get())
				.pattern("kt ")
				.pattern(" d ")
				.pattern(" g ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('k', TreasureItems.GOLD_KEY.get())
				.define('d', Items.GLOWSTONE_DUST)
				.define('g', TreasureItems.ONYX.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// ruby key
		ShapedRecipeBuilder.shaped(TreasureItems.RUBY_KEY.get())
				.pattern("kt ")
				.pattern(" d ")
				.pattern(" r ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('k', TreasureItems.GOLD_KEY.get())
				.define('d', Items.GLOWSTONE_DUST)
				.define('r', TreasureItems.RUBY.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// sapphire
		ShapedRecipeBuilder.shaped(TreasureItems.SAPPHIRE_KEY.get())
				.pattern("kt ")
				.pattern(" d ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('k', TreasureItems.GOLD_KEY.get())
				.define('d', Items.GLOWSTONE_DUST)
				.define('s', TreasureItems.SAPPHIRE.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// wither key
		ShapedRecipeBuilder.shaped(TreasureItems.WITHER_KEY.get())
				.pattern(" bt")
				.pattern(" b ")
				.pattern(" br")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('b', TreasureItems.WITHER_STICK_ITEM.get())
				.define('r', TreasureItems.WITHER_ROOT_ITEM.get())
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// skull chest
		ShapelessRecipeBuilder.shapeless(TreasureBlocks.SKULL_CHEST.get())
				.requires(TreasureItems.TREASURE_TOOL.get())
				.requires(Items.SKELETON_SKULL)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// gold skull chest
		ShapedRecipeBuilder.shaped(TreasureBlocks.GOLD_SKULL_CHEST.get())
				.pattern("ggt")
				.pattern("gxg")
				.pattern("ggg")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('x', Items.SKELETON_SKULL)
				.define('g', Items.GOLD_INGOT)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// crystal skull chest
		ShapedRecipeBuilder.shaped(TreasureBlocks.CRYSTAL_SKULL_CHEST.get())
				.pattern("ggt")
				.pattern("gxg")
				.pattern("ggg")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('x', Items.SKELETON_SKULL)
				.define('g', Items.DIAMOND)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// wither planks
		ShapelessRecipeBuilder.shapeless(TreasureBlocks.WITHER_PLANKS.get())
				.requires(TreasureBlocks.WITHER_LOG.get())
				.unlockedBy("has_log", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureBlocks.WITHER_LOG.get()))
				.save(recipe);


		// gravestones
		ShapedRecipeBuilder.shaped(TreasureBlocks.SKULL_CROSSBONES.get())
				.pattern("  t")
				.pattern(" b ")
				.pattern("b b")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('b', Items.BONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_COBBLESTONE.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_MOSSY_COBBLESTONE.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.MOSSY_COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_OBSIDIAN.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.OBSIDIAN)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_POLISHED_GRANITE.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.POLISHED_GRANITE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_SMOOTH_QUARTZ.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.SMOOTH_QUARTZ)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE1_STONE.get())
				.pattern("  t")
				.pattern("   ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.STONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// gravestone 2
		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_COBBLESTONE.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_MOSSY_COBBLESTONE.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.MOSSY_COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_OBSIDIAN.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.OBSIDIAN)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_POLISHED_GRANITE.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.POLISHED_GRANITE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_SMOOTH_QUARTZ.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.SMOOTH_QUARTZ)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE2_STONE.get())
				.pattern("  t")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.STONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// gravestone 3
		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_COBBLESTONE.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_MOSSY_COBBLESTONE.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.MOSSY_COBBLESTONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_OBSIDIAN.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.OBSIDIAN)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_POLISHED_GRANITE.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.POLISHED_GRANITE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_SMOOTH_QUARTZ.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.SMOOTH_QUARTZ)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		ShapedRecipeBuilder.shaped(TreasureBlocks.GRAVESTONE3_STONE.get())
				.pattern(" st")
				.pattern(" s ")
				.pattern(" s ")
				.define('t', TreasureItems.TREASURE_TOOL.get())
				.define('s', Items.STONE)
				.unlockedBy("has_tool", InventoryChangeTrigger.TriggerInstance.hasItems(TreasureItems.TREASURE_TOOL.get()))
				.save(recipe);

		// copper weapons smelting
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.CHIPPED_COPPER_SHORT_SWORD.get()),
						Items.COPPER_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.CHIPPED_COPPER_SHORT_SWORD.get()).build()))
				.save(recipe, "copper_ingot_from_chipped_short_sword");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.COPPER_SHORT_SWORD.get()),
						Items.COPPER_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.COPPER_SHORT_SWORD.get()).build()))
				.save(recipe, "copper_ingot_from_short_sword");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.COPPER_BROAD_AXE.get()),
						Items.COPPER_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.COPPER_BROAD_AXE.get()).build()))
				.save(recipe, "copper_ingot_from_broad_axe");

		// iron weapons smelting
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.CHIPPED_IRON_SHORT_SWORD.get()),
						Items.IRON_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.CHIPPED_IRON_SHORT_SWORD.get()).build()))
				.save(recipe, "iron_ingot_from_chipped_short_sword");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_SHORT_SWORD.get()),
						Items.IRON_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_SHORT_SWORD.get()).build()))
				.save(recipe, "iron_ingot_from_short_sword");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_BROAD_AXE.get()),
						Items.IRON_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_BROAD_AXE.get()).build()))
				.save(recipe, "iron_ingot_from_broad_axe");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(TreasureItems.IRON_DWARVEN_AXE.get()),
						Items.IRON_INGOT, 1.0f, 200)
				.unlockedBy("has_weapon", inventoryTrigger(ItemPredicate.Builder.item().of(TreasureItems.IRON_DWARVEN_AXE.get()).build()))
				.save(recipe, "iron_ingot_from_dwarven_axe");
	}
}
