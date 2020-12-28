/**
 * 
 */
package com.someguyssoftware.treasure2.item.charm;

import java.util.List;
import java.util.Random;

import com.someguyssoftware.gottschcore.positional.ICoords;
import com.someguyssoftware.treasure2.Treasure;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 
 * @author Mark Gottschling on Dec 27, 2020
 *
 */
public class FireResistenceCharm extends Charm {

	/**
	 * 
	 * @param builder
	 */
	FireResistenceCharm(Builder builder) {
		super(builder);
	}

	@Override
	public boolean update(World world, Random random, ICoords coords, EntityPlayer player, Event event, final ICharmData data) {
		boolean result = false;
		if (event instanceof LivingDamageEvent) {
			// exit if not fire damage
			if (!((LivingDamageEvent)event).getSource().isFireDamage()) {
				return result;
			}
			if (data.getValue() > 0 && !player.isDead) {
				// get the source and amount
				double amount = ((LivingDamageEvent)event).getAmount();
				// calculate the new amount
				double newAmount = 0;
				double amountToCharm = amount * data.getPercent();
				double amountToPlayer = amount - amountToCharm;
				// Treasure.logger.debug("amount to charm -> {}); amount to player -> {}", amountToCharm, amountToPlayer);
				if (data.getValue() >= amountToCharm) {
					data.setValue(data.getValue() - amountToCharm);
					newAmount = amountToPlayer;
				}
				else {
					newAmount = amount - data.getValue();
					data.setValue(0);
				}
				((LivingDamageEvent)event).setAmount((float) newAmount);
				result = true;
			}    		
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag, ICharmData data) {
		TextFormatting color = TextFormatting.RED;
		tooltip.add("  " + color + getLabel(data));
		tooltip.add(" " + TextFormatting.GRAY +  "" + TextFormatting.ITALIC + I18n.translateToLocalFormatted("tooltip.charm.fire_resistence_rate", Math.toIntExact(Math.round(data.getPercent()))));
	}
}
