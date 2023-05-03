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
package com.someguyssoftware.treasure2.network;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.capability.ICharmableCapability;
import com.someguyssoftware.treasure2.capability.InventoryType;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.charm.ICharmEntity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

/**
 * 
 * @author Mark Gottschling on Aug 7, 2021
 *
 */
public class CharmMessageHandlerOnClient {
	private static final String CURIOS_ID = "curios";
	
	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return TreasureNetworking.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}

	public static void onMessageReceived(final CharmMessageToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);

		if (sideReceived != LogicalSide.CLIENT) {
			Treasure.LOGGER.warn("CharmMessageToClient received on wrong side -> {}", ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.isValid()) {
			Treasure.LOGGER.warn("CharmMessageToClient was invalid -> {}", message.toString());
			return;
		}

		// we know for sure that this handler is only used on the client side, so it is ok to assume
		//  that the ctx handler is a client, and that Minecraft exists.
		// Packets received on the server side must be handled differently!  See MessageHandlerOnServer

		Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (!clientWorld.isPresent()) {
			Treasure.LOGGER.warn("CharmMessageToClient context could not provide a ClientWorld.");
			return;
		}

		// This code creates a new task which will be executed by the client during the next tick
		//  In this case, the task is to call messageHandlerOnClient.processMessage(worldclient, message)
		ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
	}

	/**
	 * 
	 * @param worldClient
	 * @param message
	 */
	private static void processMessage(ClientWorld worldClient, CharmMessageToClient message) {
		Treasure.LOGGER.debug("received charm message -> {}", message);
		try {
			PlayerEntity player = worldClient.getPlayerByUUID(UUID.fromString(message.getPlayerName()));
	        if (player != null) {
	        	Treasure.LOGGER.debug("valid player -> {}", message.getPlayerName());
	        	// check hands first
	        	if (message.getHand() != null) {
		        	Treasure.LOGGER.debug("hand handler - valid hand -> {}", message.getHand());
		        	// get the item for the hand
	        		ItemStack heldItemStack = player.getItemInHand(message.getHand());
	        		// determine what is being held in hand
	        		if (heldItemStack != null) {
	        			Treasure.LOGGER.debug("holding item -> {}", heldItemStack.getItem().getRegistryName());
//	        			ICharmableCapability cap = heldItemStack.getCapability(TreasureCapabilities.CHARMABLE).orElse(null);
//	        			if (cap != null) {
//	        				updateCharms(heldItemStack, message, cap);
//	        			}
	        			heldItemStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
	        				updateCharms(heldItemStack, message, cap);
	        			});
	        		}
	        	}
	        	else if (CURIOS_ID.equals(message.getSlotProviderId())) {
	        		Treasure.LOGGER.debug("curios handler - updating slot charm...");
	    			LazyOptional<ICuriosItemHandler> handler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
	    			handler.ifPresent(itemHandler -> {
	    				Optional<ICurioStacksHandler> stacksOptional = itemHandler.getStacksHandler(message.getSlot());
	    				stacksOptional.ifPresent(stacksHandler -> {
	    					Treasure.LOGGER.debug("# of slots in curios handler -> {}", stacksHandler.getStacks().getSlots());
							ItemStack curiosStack = stacksHandler.getStacks().getStackInSlot(0);							
							curiosStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
								updateCharms(curiosStack, message, cap);
							});
	    				});
	    			});
	        	}
	        	else {
	        		Treasure.LOGGER.debug("hotbar handler");
	        		// get the item from the hotbar
	        		ItemStack hotbarStack = player.inventory.getItem(Integer.valueOf(message.getSlot()));
	        		if (hotbarStack != null) {
	        			Treasure.LOGGER.debug("hotbar item -> {}", hotbarStack.getItem().getRegistryName());
	        			hotbarStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
	        				updateCharms(hotbarStack, message, cap);
	        			});
	        		}
	        	}	        	
	        }
		}
		catch(Exception e) {
			Treasure.LOGGER.error("Unexpected error ->", e);
		}
	}
	
	private static void updateCharms(ItemStack itemStack, CharmMessageToClient message, ICharmableCapability capability) {
		// get the charm that is being sent
		ResourceLocation charmName = new ResourceLocation(message.getCharmName());
		// cycle through the charm states to find the named charm
		List<ICharmEntity> entityList = (List<ICharmEntity>) capability.getCharmEntities().get(message.getInventoryType());
		if (entityList != null && !entityList.isEmpty() && entityList.size() > message.getIndex()) {
			ICharmEntity entity = entityList.get(message.getIndex());
    		Treasure.LOGGER.debug("looking for charm -> {} at index -> {}", entity, message.getIndex());
			if (entity != null && entity.getCharm().getName().equals(charmName)) {
	        	Treasure.LOGGER.debug("found charm, updating...");
				// update entity properties
				entity.update(message.getEntity());
				
				// NOTE yes, remove innate charms from Adornments - they can't be recharged
				if (message.getInventoryType() == InventoryType.INNATE && entity.getMana() <= 0.0) {
					capability.remove(message.getInventoryType(), message.getIndex());
				}
				// TODO probably need to remove imbue as well
				

				// update Durability 
				itemStack.getCapability(TreasureCapabilities.DURABILITY).ifPresent(cap -> {
					Treasure.LOGGER.debug("updating durability damage from {} -> to -> {}", itemStack.getDamageValue(), message.getItemDamage());
					itemStack.setDamageValue(message.getItemDamage());
				});
//				break;
			}
		}
	}
}
