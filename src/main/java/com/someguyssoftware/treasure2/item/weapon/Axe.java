package com.someguyssoftware.treasure2.item.weapon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

/**
 * Extends AxeItem so it behaves like a vanilla axe elsewhere in the codebase. ex. breaks shields etc.
 * @author Mark Gottschling May 24, 2023
 *
 */
public class Axe extends ItemAxe implements IWeapon {
	private String repairUnlocalizedName;
	private double criticalChance;
	private float criticalDamage;

	public Axe(String id, String name, ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, CreativeTabs tab) {
		this(id, name, material, repairItem, damageModifier, speedModifier, 0, 0f, tab);
	}

	public Axe(String id, String name, ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, double criticalChance, float criticalDamage, CreativeTabs tab) {
		super(ToolMaterial.IRON); // use iron to construct super()
		this.setItemName(id, name);
		this.criticalChance = criticalChance;
		this.criticalDamage = criticalDamage;
		this.setRepairUnlocalizedName(repairItem.getUnlocalizedName());
		this.setCreativeTab(tab);

		// override properties with custom/passed-in material
		this.toolMaterial = material;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.efficiency = material.getEfficiency();
        this.attackDamage = damageModifier + material.getAttackDamage();
		this.attackSpeed = speedModifier;
	}

	public void setItemName(String modID, String name) {
		this.setRegistryName(modID, name);
		this.setUnlocalizedName(this.getRegistryName().toString());
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack resourceItem) {
		if (itemToRepair.isItemDamaged()) {
			if (resourceItem.getItem().getUnlocalizedName().equals(this.repairUnlocalizedName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (isUnique()) {
			return TextFormatting.YELLOW + "" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
		} else {
			return I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flag) {
		appendStats(stack, worldIn, tooltip, flag);
		appendHoverExtras(stack, worldIn, tooltip, flag);
	}

	public String getRepairUnlocalizedName() {
		return repairUnlocalizedName;
	}

	public void setRepairUnlocalizedName(String repairUnlocalizedName) {
		this.repairUnlocalizedName = repairUnlocalizedName;
	}

	public double getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(double criticalChance) {
		this.criticalChance = criticalChance;
	}

	public float getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(float criticalDamage) {
		this.criticalDamage = criticalDamage;
	}
}
