/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
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
package mod.gottsch.forge.treasure2.core.item;

import static mod.gottsch.forge.treasure2.core.capability.TreasureCapabilities.POUCH;

import java.util.List;

import javax.annotation.Nullable;

import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.gottschcore.world.WorldInfo;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.capability.CharmableCapabilityStorage;
import mod.gottsch.forge.treasure2.core.capability.PouchCapabilityProvider;
import mod.gottsch.forge.treasure2.core.capability.TreasureCapabilities;
import mod.gottsch.forge.treasure2.core.inventory.PouchContainer;
import mod.gottsch.forge.treasure2.core.inventory.PouchInventory;
import mod.gottsch.forge.treasure2.core.inventory.TreasureContainers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author Mark Gottschling on May 13, 2020
 *
 */
public class PouchItem extends ModItem implements INamedContainerProvider {
	/**
	 * 
	 * @param properties
	 */
	public PouchItem(Properties properties) {
		super(properties.tab(TreasureItemGroups.TREASURE_ITEM_GROUP).stacksTo(1));
	}
	
	@Deprecated
	public PouchItem(String modID, String name, Properties properties) {
		super(modID, name, properties.tab(TreasureItemGroups.TREASURE_ITEM_GROUP).stacksTo(1));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		PouchCapabilityProvider provider =  new PouchCapabilityProvider();
		return provider;
	}

	/**
	 * 
	 */
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.label.pouch").withStyle(TextFormatting.GOLD));
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		// exit if on the client
		if (WorldInfo.isClientSide(worldIn)) {			
			return new ActionResult<ItemStack>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
		}

		// get the container provider
		INamedContainerProvider namedContainerProvider = this;

		// open the chest
		NetworkHooks.openGui((ServerPlayerEntity)playerIn, namedContainerProvider, (packetBuffer)->{});
		// NOTE: (packetBuffer)->{} is just a do-nothing because we have no extra data to send

		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
	}

	@Override
	public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player) {
		// get the held item
		ItemStack heldItem = player.getItemInHand(Hand.MAIN_HAND);
		if (heldItem == null || !(heldItem.getItem() instanceof PouchItem)) {
			heldItem = player.getItemInHand(Hand.OFF_HAND);
			if (heldItem == null || !(heldItem.getItem() instanceof PouchItem))
				return null;
		}

		// create inventory from item
		IInventory itemInventory = new PouchInventory(heldItem);
		// open the container
		return new PouchContainer(windowID, TreasureContainers.POUCH_CONTAINER_TYPE, inventory, itemInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("item.treasure2.pouch");
	}

	////////////////////////
	/**
	 * NOTE getShareTag() and readShareTag() are required to sync item capabilities server -> client. I needed this when holding charms in hands and then swapping hands.
	 */
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT nbt = stack.getOrCreateTag();
		ItemStackHandler cap = (ItemStackHandler) stack.getCapability(POUCH).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
		try {
			nbt = cap.serializeNBT();
		} catch (Exception e) {
			Treasure.LOGGER.error("Unable to write state to NBT:", e);
		}
		return nbt;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		super.readShareTag(stack, nbt);

		if (nbt instanceof CompoundNBT) {
			ItemStackHandler cap = (ItemStackHandler) stack.getCapability(POUCH).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
			cap.deserializeNBT((CompoundNBT) nbt);
		}
	}
}


