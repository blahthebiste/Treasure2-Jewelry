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
package mod.gottsch.forge.treasure2.core.eventhandler;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.someguyssoftware.gottschcore.random.RandomHelper;
import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.world.WorldInfo;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.capability.ICharmableCapability;
import mod.gottsch.forge.treasure2.core.capability.InventoryType;
import mod.gottsch.forge.treasure2.core.capability.TreasureCapabilities;
import mod.gottsch.forge.treasure2.core.charm.CharmContext;
import mod.gottsch.forge.treasure2.core.charm.ICharm;
import mod.gottsch.forge.treasure2.core.charm.ICharmEntity;
import mod.gottsch.forge.treasure2.core.item.weapon.IWeapon;
import mod.gottsch.forge.treasure2.core.network.CharmMessageToClient;
import mod.gottsch.forge.treasure2.core.network.TreasureNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * 
 * @author Mark Gottschling on Sep 4, 2021
 *
 */
public class CharmEventHandler {

	private IEquipmentCharmHandler equipmentCharmHandler;
	
	/**
	 * 
	 * @param handler
	 */
	public CharmEventHandler(IEquipmentCharmHandler handler) {
		equipmentCharmHandler = handler;
	}
	
	/*
	 * Subscribing to multiple types of Living events for Charm Interactions so that instanceof doesn't have to be called everytime.
	 */

	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void checkCharmsInteraction(LivingUpdateEvent event) {
		if (WorldInfo.isClientSide(event.getEntity().level)) {
			return;
		}

		// do something to player every update tick:
		if (event.getEntity() instanceof PlayerEntity) {

			// get the player
			ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
			processCharms(event, player);
		}
	}

	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void checkCharmsInteractionWithDamage(LivingDamageEvent event) {
		if (WorldInfo.isClientSide(event.getEntity().level)) {
			return;
		}

		// NOTE mimic checkCharms...(LivingHurEvent) for checking the player entity, IFF a charm causes a mob damage.
		
		// do something to player every update tick:
		if (event.getEntity() instanceof PlayerEntity) {
			// get the player
			ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
			processCharms(event, player);
		}		
	}

	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void checkCharmsInteractionWithAttack(LivingHurtEvent event) {
		if (WorldInfo.isClientSide(event.getEntity().level)) {
			return;
		}

		// get the player
		ServerPlayerEntity player = null;
		if (event.getEntity() instanceof PlayerEntity) {
			player = (ServerPlayerEntity) event.getEntity();
		}
		else if (event.getSource().getEntity() instanceof PlayerEntity) {
			player = (ServerPlayerEntity) event.getSource().getEntity();

			// weapon buffs processing
			ItemStack heldStack = player.getItemInHand(Hand.MAIN_HAND);
			if (heldStack != null & heldStack.getItem() instanceof IWeapon) {
				IWeapon weapon = (IWeapon) heldStack.getItem();
				Treasure.LOGGER.debug("original damage -> {}", event.getAmount());
				if (RandomHelper.checkProbability(new Random(), weapon.getCriticalChance())) {
					event.setAmount(event.getAmount() + weapon.getCriticalDamage());
					Treasure.LOGGER.debug("new + critical damage -> {}", event.getAmount());
				}
			}
		}
		
		if (player != null) {
			processCharms(event, player);
		}
	}

	// Maybe use BlockEvent.BreakBlock and then use Global Loot Modifiers??
	//	@SubscribeEvent
	//	public void checkCharmsInteractionWithBlock(BlockEvent.HarvestDropsEvent event) {
	//		if (WorldInfo.isClientSide(event.getWorld())) {
	//			return;
	//		}
	//
	//		if (event.getHarvester() == null) {
	//			return;
	//		}
	//
	//		// if the harvested blcok has a tile entity then don't process
	//		// NOTE this may exclude non-inventory blocks
	//		IBlockState harvestedState = event.getState();
	//		Block harvestedBlock = harvestedState.getBlock();
	//		if (harvestedBlock.hasTileEntity(harvestedState)) {
	//			return;
	//		}
	//
	//		// get the player
	//		PlayerEntityMP player = (PlayerEntityMP) event.getHarvester();
	//		processCharms(event, player);
	//	}

	/**
	 * 
	 * @param event
	 * @param player
	 */
	private void processCharms(Event event, ServerPlayerEntity player) {
		/*
		 * a list of charm contexts to execute
		 */
		List<CharmContext> charmsToExecute;

		// gather all charms
		charmsToExecute = gatherCharms(event, player);

		// sort charms
		Collections.sort(charmsToExecute, CharmContext.priorityComparator);

		// execute charms
		executeCharms(event, player, charmsToExecute);
	}

	/**
	 * Examine and collect  all Charms (not CharmEntity nor CharmItems) that the player has in valid slots.
	 * @param event
	 * @param player
	 * @return
	 */
	private List<CharmContext> gatherCharms(Event event, ServerPlayerEntity player) {
		final List<CharmContext> contexts = new ArrayList<>(5);
		
		// check each hand
		for (Hand hand : Hand.values()) {
			ItemStack heldStack = player.getItemInHand(hand);
			if (heldStack.getCapability(TreasureCapabilities.CHARMABLE).isPresent()) {
				contexts.addAll(getCharmsFromStack(event, hand, "", heldStack, false));
			}
		}

		// check equipment slots
		List<CharmContext> equipmentContexts = getEquipmentCharmHandler().handleEquipmentCharms(event, player);
		contexts.addAll(equipmentContexts);
	
		return contexts;
	}

	/**
	 * 
	 * @param event
	 * @param hand
	 * @param itemStack
	 * @param isPouch
	 * @return
	 */
	private List<CharmContext> getCharmsFromStack(Event event, Hand hand, String slot, ItemStack itemStack, boolean isPouch) {
		final List<CharmContext> contexts = new ArrayList<>(5);
		ICharmableCapability cap = itemStack.getCapability(TreasureCapabilities.CHARMABLE).map(c -> c).orElseThrow(() -> new IllegalSelectorException());
		if (cap.isExecuting()) {
			for (InventoryType type : InventoryType.values()) {
				AtomicInteger index = new AtomicInteger();
				for (int i = 0; i < cap.getCharmEntities().get(type).size(); i++) {
					ICharmEntity entity = ((List<ICharmEntity>)cap.getCharmEntities().get(type)).get(i);
					if (!entity.getCharm().getRegisteredEvent().equals(event.getClass())) {
						continue;
					}
					index.set(i);
					CharmContext context = new CharmContext.Builder().with($ -> {
						$.hand = hand;
						$.slot = slot;
						$.slotProviderId = isPouch ? "treasure2" : "minecraft";
						$.itemStack = itemStack;
						$.capability = cap;
						$.type = type;
						$.index = index.get();
						$.entity = entity;						
					}).build();
					contexts.add(context);
				}
			}
		}
		return contexts;
	}
	
	/**
	 * 
	 * @param event
	 * @param player
	 * @param contexts
	 */
	private static void executeCharms(Event event, ServerPlayerEntity player, List<CharmContext> contexts) {
		/*
		 * a list of charm types that are non-stackable that should not be executed more than once.
		 */
		final List<String> executeOnceCharmTypes = new ArrayList<>(5);

		contexts.forEach(context -> {
			ICharm charm = (ICharm)context.getEntity().getCharm();
			if (!charm.isEffectStackable()) {
				// check if this charm type is already in the monitored list
				if (executeOnceCharmTypes.contains(charm.getType())) {
					return;
				}
				else {
					// add the charm type to the monitored list
					executeOnceCharmTypes.add(charm.getType());
				}
			}

			// if charm is executable and executes successfully
			if (context.getEntity().getCharm().update(player.level, new Random(), new Coords(player.position()), player, event, context.getEntity())) {
				// TODO handle the durability of the adornment
				processUsage(player.level, player, event, context);
				
				// TODO would be nice if ALL charms processed during event could sent 1 bundled message instead of individual messages
				
				// send state message to client
				CharmMessageToClient message = new CharmMessageToClient(player.getStringUUID(), context);
				TreasureNetworking.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), message);
			}
			
			// remove if mana AND recharges are empty and the capability is bindable ie. charm, not adornment
			if (context.getCapability().isBindable() 
					&& context.getEntity().getRecharges() <= 0
					&& context.getEntity().getMana() <= 0.0 ) {
				Treasure.LOGGER.debug("charm is empty without any recharges -> remove");
				context.getCapability().remove(context.getType(), context.getIndex());
			}
		});
	}

	private static void processUsage(World world, PlayerEntity player, Event event, CharmContext context) {
		// TODO call capability.getDecrementor.apply() or something like that.
		ItemStack stack = context.getItemStack();
		// get capability
		stack.getCapability(TreasureCapabilities.DURABILITY).ifPresent(cap -> {
			if (cap.isInfinite()) {
				return;
			}
			// remove/destroy item stack if damage is greater than durability
			stack.setDamageValue(stack.getDamageValue() + 1);
			if (stack.getDamageValue() >= cap.getDurability()) {
				stack.shrink(1);
			}
		});
	}
	
	/**
	 * 
	 * @return
	 */
	private IEquipmentCharmHandler getEquipmentCharmHandler() {
		return equipmentCharmHandler;
	}
	
	// TODO test - remove
	//	@SubscribeEvent
	//	public void onItemInfo(ItemTooltipEvent event) {
	//		if (event.getItemStack().getItem() == Items.EMERALD) {
	//			event.getToolTip().add(new TranslationTextComponent("tooltip.label.gem").withStyle(TextFormatting.GOLD, TextFormatting.ITALIC));
	//		}
	//	}
}
