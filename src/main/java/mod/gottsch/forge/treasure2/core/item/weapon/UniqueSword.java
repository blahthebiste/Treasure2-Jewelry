package mod.gottsch.forge.treasure2.core.item.weapon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Mark Gottschling on 8/12/2024
 */
public class UniqueSword extends Sword {

    public UniqueSword(IItemTier tier, float damageModifier, float speedModifier, Properties properties) {
        super(tier, damageModifier, speedModifier, properties);
    }

    public UniqueSword(IItemTier tier, float damageModifier, float speedModifier, float criticalChance, float criticalDamage, Properties properties) {
        super(tier, damageModifier, speedModifier, criticalChance, criticalDamage, properties);
    }

    @Override
    public  void appendHoverExtras(ItemStack stack, World level, List<ITextComponent> tooltip, ITooltipFlag flag) {

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack repairStack) {
        return false;
    }
}
