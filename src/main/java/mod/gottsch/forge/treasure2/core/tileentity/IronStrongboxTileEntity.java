package mod.gottsch.forge.treasure2.core.tileentity;

import mod.gottsch.forge.treasure2.core.chest.ChestSlotCount;
import mod.gottsch.forge.treasure2.core.inventory.StrongboxChestContainer;
import mod.gottsch.forge.treasure2.core.inventory.TreasureContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * 
 * @author Mark Gottschling on Jan 19, 2018
 *
 */
public class IronStrongboxTileEntity extends AbstractTreasureChestTileEntity {
	
	/**
	 * 
	 * @param texture
	 */
	public IronStrongboxTileEntity() {
		super(TreasureTileEntities.IRON_STRONGBOX_TILE_ENTITY_TYPE);
		setCustomName(new TranslationTextComponent("display.iron_strongbox.name"));
	}
	
	/**
	 * 
	 * @param windowID
	 * @param inventory
	 * @param player
	 * @return
	 */
	public Container createServerContainer(int windowID, PlayerInventory inventory, PlayerEntity player) {
		return new StrongboxChestContainer(windowID, TreasureContainers.STRONGBOX_CHEST_CONTAINER_TYPE, inventory, this);
	}
	
	/**
	 * @return the numberOfSlots
	 */
	@Override
	public int getNumberOfSlots() {
		return ChestSlotCount.STRONGBOX.getSize();
	}
}
