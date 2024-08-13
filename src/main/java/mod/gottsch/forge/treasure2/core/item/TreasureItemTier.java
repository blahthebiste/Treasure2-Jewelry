/**
 * 
 */
package mod.gottsch.forge.treasure2.core.item;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.LazyValue;

/**
 * @author Mark Gottschling on Jan 15, 2021
 *
 */
public enum TreasureItemTier implements IItemTier {
	COPPER(1, 200, 5.0F, 1.0F, 10,
			() -> Ingredient.of(Items.IRON_INGOT)),
	STEEL(2, 600, 6.5F, 2.5F, 15,
				  () -> Ingredient.of(Items.IRON_INGOT)),
	BONE(2, 200, 6.25F, 2F, 16,
			() -> Ingredient.of(Items.BONE)),
	SHADOW(3, 1600, 9.0F, 4.0F, 15,
			() -> Ingredient.of(Items.NETHERITE_INGOT)),

	SKULL(2, 1800, 9.0F, 4.0F, 15,
			() -> Ingredient.of(Items.SKELETON_SKULL)),
	RARE(4, 1700, 9.5F, 3.0F, 18,
			() -> Ingredient.of(Items.DIAMOND)),
	EPIC(4, 1800, 9.5F, 4.5F, 18,
				 () -> Ingredient.of(Items.DIAMOND)),
	LEGENDARY(5, 2200, 10.0F, 5.0F, 20,
					  () -> Ingredient.of(Items.DIAMOND)),
	MYTHICAL(6, 2400, 11.0F, 6.0F, 22,
			() -> Ingredient.of(Items.NETHERITE_INGOT));

	private final int level;
	private final int uses;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final LazyValue<Ingredient> repairIngredient;

	private TreasureItemTier(int harvestLevelIn, int maxUsesIn, float speedIn, float attackDamageBonusIn, int enchantmentValueIn, Supplier<Ingredient> repairIngredientIn) {
		this.level = harvestLevelIn;
		this.uses = maxUsesIn;
		this.speed = speedIn;
		this.attackDamageBonus = attackDamageBonusIn;
		this.enchantmentValue = enchantmentValueIn;
		this.repairIngredient = new LazyValue<>(repairIngredientIn);
	}

	@Override
	public int getUses() {
		return uses;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

}
