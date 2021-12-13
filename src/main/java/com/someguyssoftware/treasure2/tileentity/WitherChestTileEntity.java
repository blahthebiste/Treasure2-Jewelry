package com.someguyssoftware.treasure2.tileentity;

import com.someguyssoftware.treasure2.chest.ChestSlotCount;
import com.someguyssoftware.treasure2.inventory.TreasureContainers;
import com.someguyssoftware.treasure2.inventory.WitherChestContainer;

import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * NOTE Wither ChestConfig does not have a "lid", but has doors, however, the use of the lidAngle from the super class
 * will still be used.  The rendered will use that value to determine how to render the doors.
 * 
 * @author Mark Gottschling on Jun 17, 2018
 *
 */
public class WitherChestTileEntity extends AbstractTreasureChestTileEntity {
    
	/**
	 * 
	 * @param texture
	 */
	public WitherChestTileEntity() {
		super(TreasureTileEntities.WITHER_CHEST_TILE_ENTITY_TYPE);
		setCustomName(new TranslatableComponent("display.wither_chest.name"));
	}
	
	/**
	 * 
	 * @param windowID
	 * @param inventory
	 * @param player
	 * @return
	 */
	public Container createServerContainer(int windowID, PlayerInventory inventory, Player player) {
		return new WitherChestContainer(windowID, TreasureContainers.WITHER_CHEST_CONTAINER_TYPE, inventory, this);
	}
	
	/**
	 * @return the numberOfSlots
	 */
	@Override
	public int getNumberOfSlots() {
		return ChestSlotCount.WITHER.getSize();
	}
}
