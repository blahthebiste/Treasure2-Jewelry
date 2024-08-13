package mod.gottsch.forge.treasure2.core.item.weapon;

import mod.gottsch.forge.treasure2.core.util.LangUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Mark Gottschling on 8/12/2024
 */
public class UniqueSword extends Sword {

    public UniqueSword(IItemTier tier, float damageModifier, float speedModifier, Properties properties) {
        super(tier, damageModifier, speedModifier, properties);
    }

    public UniqueSword(IItemTier tier, float damageModifier, float speedModifier, double criticalChance, float criticalDamage, Properties properties) {
        super(tier, damageModifier, speedModifier, criticalChance, criticalDamage, properties);
    }

    @Override
    public  void appendHoverExtras(ItemStack stack, World level, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(LangUtil.NEWLINE));
        tooltip.add(new StringTextComponent(LangUtil.INDENT4)
                .append(new TranslationTextComponent(LangUtil.tooltip("weapons." + getRegistryName().getPath() + ".lore"))
                        .withStyle(TextFormatting.LIGHT_PURPLE).withStyle(TextFormatting.ITALIC)));
        tooltip.add(new StringTextComponent(LangUtil.NEWLINE));
    }

    /**
     * convenience method for multiline lore
     */
    public void appendHoverExtrasMultiline(ItemStack stack, World level, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(LangUtil.NEWLINE));
        // lore may be multiple lines, so separate on ~ and add to tooltip
        TranslationTextComponent lore = new TranslationTextComponent(LangUtil.tooltip("weapons." + getRegistryName().getPath() + ".lore"));
        for (String s : lore.getString().split("~")) {
            tooltip.add(new StringTextComponent(LangUtil.INDENT4)
                    .append(new TranslationTextComponent(s)).withStyle(TextFormatting.LIGHT_PURPLE).withStyle(TextFormatting.ITALIC));
        }
        tooltip.add(new StringTextComponent(LangUtil.NEWLINE));
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairStack) {
        return false;
    }
}
