package com.someguyssoftware.treasure2.item.weapon;

import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

/**
 * This wrapper class allows float values for damage.
 * @author Mark Gottschling May 8, 2023
 *
 */
public class Sword extends ItemSword implements IWeapon {
	private String repairUnlocalizedName;
	private float criticalChance;
	private float criticalDamage;
	private float speedModifier;
	
	/*
		MC 1.12.2: net/minecraft/world/item/ItemSword.attackDamage
		SRG Name: field_150934_a
	 */
	private static final String ATTACK_DAMAGE_SRG_NAME = "field_150934_a";

	public Sword(String id, String name, Item.ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, CreativeTabs tab) {
		this(id, name, material, repairItem, damageModifier, speedModifier, 0f, 0f, tab);
	}

	public Sword(String id, String name, Item.ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, float criticalChance, float criticalDamage, CreativeTabs tab) {
		super(material);
		this.setItemName(id, name);
		this.criticalChance = criticalChance;
		this.criticalDamage = criticalDamage;
		this.setRepairUnlocalizedName(repairItem.getUnlocalizedName());
		this.setCreativeTab(tab);

		float attackDamage = damageModifier + material.getAttackDamage();
		ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, attackDamage, ATTACK_DAMAGE_SRG_NAME);
		this.speedModifier = speedModifier;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.getAttackDamage(), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", speedModifier, 0));
		}
		return multimap;
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

	public float getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(float criticalChance) {
		this.criticalChance = criticalChance;
	}

	public float getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(float criticalDamage) {
		this.criticalDamage = criticalDamage;
	}
}
