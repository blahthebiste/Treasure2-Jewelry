/**
 * 
 */
package com.someguyssoftware.treasure2.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.AbstractChestBlock;
import com.someguyssoftware.treasure2.block.ITreasureChestProxy;
import com.someguyssoftware.treasure2.enums.Category;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.lock.LockState;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

/**
 * @author Mark Gottschling onJan 10, 2018
 *
 */
public class LockItem extends ModItem {

	/*
	 * The category that the lock belongs to
	 */
	private Category category;

	/*
	 * The rarity of the lock
	 */
	private Rarity rarity;

	/*
	 * Flag if the lock is craftable
	 */
	private boolean craftable;

	/*
	 * a list of keys that unlock the lock
	 */
	private List<KeyItem> keys = new ArrayList<>(3);

	/**
	 * 
	 * @param item
	 * @param keys
	 */
	public LockItem(String modID, String name, Item.Properties properties, KeyItem[] keys) {
		this(modID, name, properties);
		getKeys().addAll(Arrays.asList(keys));
	}

	/**
	 * 
	 * @param item
	 */
	public LockItem(String modID, String name, Item.Properties properties) {
		super(modID, name, properties.tab(TreasureItemGroups.MOD_ITEM_GROUP));
		setCategory(Category.ELEMENTAL);
		setRarity(Rarity.COMMON);
		setCraftable(false);
	}

	/**
	 * Format: Item Name (vanilla minecraft) Rarity: [COMMON | UNCOMMON | SCARCE |
	 * RARE| EPIC] [color = Dark Blue] Category: [...] [color = Gold] Craftable: [Yes |
	 * No] [color = Green | Dark Red] Accepts Keys: [list] [color = Gold]
	 */
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TranslatableComponent("tooltip.label.rarity",
				ChatFormatting.DARK_BLUE + getRarity().toString()));
		tooltip.add(new TranslatableComponent("tooltip.label.category", ChatFormatting.GOLD + getCategory().toString()));

		TextComponent craftable = null;
		if (isCraftable()) {
			craftable = new TranslatableComponent("tooltip.yes").withStyle(ChatFormatting.GREEN);
		} else {
			craftable = new TranslatableComponent("tooltip.no").withStyle(ChatFormatting.DARK_RED);
		}
		tooltip.add(new TranslatableComponent("tooltip.label.craftable", craftable));

		/**
		 * Attempting to make a safe call for some performance enchancing mixin mods
		 */
		String keyList = getKeys().stream().map(e -> {
			TextComponent txt = e.getName(new ItemStack(e));
			return txt == null ? "" : txt.getString();
		}).collect(Collectors.joining(","));

		tooltip.add(new TranslatableComponent("tooltip.label.accepts_keys", ChatFormatting.GOLD + keyList));
	}

	/**
	 * 
	 */
	@Override
	public InteractionResult useOn(ItemUseContext context) {

		BlockPos chestPos = context.getClickedPos();		
		Block block = context.getLevel().getBlockState(chestPos).getBlock();
//		Treasure.LOGGER.info("LockItem | onItemUse | chestPos start -> {}", chestPos);
		// if the block is a proxy of a chest (ie wither chest top block or other special block)
		if (block instanceof ITreasureChestProxy) {
			Treasure.LOGGER.info("LockItem | onItemUse | block is an ITreasureChestProxy");
			chestPos = ((ITreasureChestProxy) block).getChestPos(chestPos);
			block = context.getLevel().getBlockState(chestPos).getBlock();
		}
//		Treasure.LOGGER.info("LockItem | onItemUse | chestPos after proxy check -> {}", chestPos);
		// determine if block at pos is a treasure chest
		if (block instanceof AbstractChestBlock) {
			// get the tile entity
			AbstractTreasureChestTileEntity te = (AbstractTreasureChestTileEntity) context.getLevel().getBlockEntity(chestPos);
//			Treasure.LOGGER.info("LockItem | onItemUse | tileEntity -> {}", te);
			// exit if on the client
			if (WorldInfo.isClientSide(context.getLevel())) {
				return InteractionResult.FAIL;
			}

			try {
				ItemStack heldItem = context.getPlayer().getItemInHand(context.getHand());
				// handle the lock
				// NOTE don't use the return boolean as the locked flag here, as the chest is
				// already locked and if the method was
				// unsuccessful it could state the chest is unlocked.
				handleHeldLock(te, context.getPlayer(), heldItem);
			} catch (Exception e) {
				Treasure.LOGGER.error("error: ", e);
			}
		}
		return super.useOn(context);
	}

	/**
	 * 
	 * @param tileEntity
	 * @param player
	 * @param heldItem
	 * @return flag indicating if a lock was added
	 */
	private boolean handleHeldLock(AbstractTreasureChestTileEntity tileEntity, Player player, ItemStack heldItem) {
		boolean lockedAdded = false;
		LockItem lock = (LockItem) heldItem.getItem();
		Treasure.LOGGER.info("LockItem | handleHeldLock | lock -> {}", lock);
		Treasure.LOGGER.info("LockItem | handleHeldLock | lockState -> {}", tileEntity.getLockStates());
		// add the lock to the first lockstate that has an available slot
		for (LockState lockState : tileEntity.getLockStates()) {
			Treasure.LOGGER.info("handleHeldLock | lockState -> {}", lockState);
			if (lockState != null && lockState.getLock() == null) {
				lockState.setLock(lock);
				tileEntity.sendUpdates(); // TODO won't send until implement read/write nbt
				// decrement item in hand
				heldItem.shrink(1);
//				if (heldItem.getCount() <=0 && !player.capabilities.isCreativeMode) {
//					IInventory inventory = player.inventory;
//					inventory.setInventorySlotContents(player.inventory.currentItem, null);
//				}
				lockedAdded = true;
				break;
			}
		}
		return lockedAdded;
	}

	/**
	 * 
	 * @param keyItem
	 * @return
	 */
	public boolean acceptsKey(KeyItem keyItem) {
		for (KeyItem k : getKeys()) {
			if (k == keyItem)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param keyItem
	 * @return
	 */
    public boolean breaksKey(KeyItem keyItem) {
        return false;
    }
    
	/**
	 * @return the rarity
	 */
	public Rarity getRarity() {
		return rarity;
	}

	/**
	 * @param rarity the rarity to set
	 */
	public LockItem setRarity(Rarity rarity) {
		this.rarity = rarity;
		return this;
	}

	/**
	 * @return the craftable
	 */
	public boolean isCraftable() {
		return craftable;
	}

	/**
	 * @param craftable the craftable to set
	 */
	public LockItem setCraftable(boolean craftable) {
		this.craftable = craftable;
		return this;
	}

	/**
	 * @return the keys
	 */
	public List<KeyItem> getKeys() {
		return keys;
	}

	/**
	 * @param keys the keys to set
	 */
	public LockItem setKeys(List<KeyItem> keys) {
		this.keys = keys;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LockItem [name=" + getRegistryName() + ", rarity=" + rarity + ", craftable=" + craftable + ", keys="
				+ keys + "]";
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public LockItem setCategory(Category category) {
		this.category = category;
		return this;
	}
}
