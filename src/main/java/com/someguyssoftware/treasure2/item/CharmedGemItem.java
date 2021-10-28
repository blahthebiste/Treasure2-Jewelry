/**
 * 
 */
package com.someguyssoftware.treasure2.item;

import java.util.List;

import javax.annotation.Nullable;

import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.capability.CharmInventoryCapabilityProvider;
import com.someguyssoftware.treasure2.capability.CharmInventoryCapabilityStorage;
import com.someguyssoftware.treasure2.capability.TreasureCapabilities;
import com.someguyssoftware.treasure2.item.charm.ICharmed;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * 
 * @author Mark Gottschling on May 19, 2020
 *
 */
public class CharmedGemItem extends GemItem implements ICharmed {
	
	private static final CharmInventoryCapabilityStorage CAPABILITY_STORAGE = new CharmInventoryCapabilityStorage();
	
	/**
	 * 
	 */
	public CharmedGemItem (String modID, String name)	 {
		super(modID, name);
		this.setMaxStackSize(1);
		// prevent from showing in any creative tab
		this.setCreativeTab(null);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		CharmInventoryCapabilityProvider provider =  new CharmInventoryCapabilityProvider();
		return provider;
	}

	/**
	 * NOTE getNBTShareTag() and readNBTShareTag() are required to sync item capabilities server -> client. I needed this when holding charms in hands and then swapping hands
	 * or having the client update when the Anvil GUI is open.
	 */
	@Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
		NBTTagCompound nbt = null;
		// read cap -> write nbt
		nbt = (NBTTagCompound) CAPABILITY_STORAGE.writeNBT(
				TreasureCapabilities.CHARM_INVENTORY,
				stack.getCapability(TreasureCapabilities.CHARM_INVENTORY, null), null);
		return nbt;
	}
	
    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        // read nbt -> write key item
       CAPABILITY_STORAGE.readNBT(
    		   TreasureCapabilities.CHARM_INVENTORY, 
				stack.getCapability(TreasureCapabilities.CHARM_INVENTORY, null), 
				null,
				nbt);
    }
    
	/**
	 * 
	 */
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		addCharmedInfo2(stack, worldIn, tooltip, flagIn);
	}
	
	/**
	 * 
	 */
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
