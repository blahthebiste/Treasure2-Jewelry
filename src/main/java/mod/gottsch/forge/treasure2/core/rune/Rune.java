/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2.core.rune;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.enums.Rarity;
import mod.gottsch.forge.treasure2.core.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/**
 * NOTE:  As of today, Jan 14, this class doesn't really require a builder. However the details
 * aren't really fleshed out and this way makes it more expandable in the future.
 * NOTE:  Runestones are a one-time application, ie. the effects are applied to the charm entity, adornment, etc
 * at one point in time and not re-calculated (ex for display purposes). Typically their effects are applied
 * on the Anvil event. If you want add attach a Runestone to an adornment in initCapabilities() or something similar
 * you will have to call the Runestone.apply(ItemStack) in order to see the effects.
 * @author Mark Gottschling on Jan 14, 2022
 *
 */
public abstract class Rune implements IRune {
	private ResourceLocation name;
	private String type;
	private String lore;
	// the rarity of the charm
	private Rarity rarity;
	
	// invalid charm list
	private List<String> invalids;
	
	protected Rune(Builder builder) {
		this.name = builder.name;
		this.type = builder.type;
		this.lore = builder.lore;
		this.rarity = builder.rarity;
		this.invalids = builder.invalids;
	}
	
	// TODO necessary? - only if saving state values - like for undo()
	@Override
	public IRuneEntity createEntity() {
		IRuneEntity entity = new RuneEntity();
		entity.setRunestone(this);
		
		return entity;
	}
	
	//?
	public IRuneEntity createEntity(IRuneEntity entity) {
		IRuneEntity newEntity = new RuneEntity(entity);
		return newEntity;
	}
	
	/**
	 * Determines whether this Runestone is valid for the given ItemStack
	 */
	@Override
	abstract public boolean isValid(ItemStack itemStack);
	
	/**
	 * Applies the Runestone's ability/modification to the ItemStack
	 * @param itemStack
	 */
	@Override
	abstract public void apply(ItemStack itemStack, IRuneEntity entity);
		
	/**
	 * Undoes the Runestone's ability/modification from the ItemStack 
	 * @param itemStack
	 */
	abstract public void undo(ItemStack itemStack, IRuneEntity entity);
	
	@SuppressWarnings("deprecation")
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag, IRuneEntity entity) {
        TextFormatting color = TextFormatting.LIGHT_PURPLE;       
		tooltip.add(new TranslationTextComponent("tooltip.indent2", new TranslationTextComponent("runestone." + getName().toString().replace(":", "."))).withStyle(color));		
	}
	
	public static Optional<IRune> load(CompoundNBT tag) {
		Optional<IRune> runestone = Optional.empty();
		// read the name of the runestone and fetch from the registry
		try {
			String name = tag.getString("name");			
			ResourceLocation resource = ModUtils.asLocation(name);
			runestone = TreasureRunes.get(resource);
			if (!runestone.isPresent()) {
				throw new Exception(String.format("Unable to locate charm %s in registry.", resource.toString()));
			}
		}
		catch(Exception e) {
			Treasure.LOGGER.error("Unable to read state to NBT:", e);
		}	

		return runestone;
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		try {
			nbt.putString("name", this.name.toString());
		}
		catch(Exception e) {
			Treasure.LOGGER.error("Unable to write state to NBT:", e);
		}
		return nbt;
	}
	
	/*
	 * 
	 */
	abstract public static class Builder {
		private final ResourceLocation name;
		private String type;
		public String lore;
		public  Rarity rarity;
		private List<String> invalids;
		
		public Builder(ResourceLocation name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @param builder
		 * @return
		 */
		public Builder with(Consumer<Builder> builder)  {
			builder.accept(this);
			return this;
		}
		
		abstract public IRune build();

		public List<String> getInvalids() {
			if (invalids == null) {
				invalids = new ArrayList<>();
			}
			return invalids;
		}	
	}

	@Override
	public ResourceLocation getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public String getLore() {
		return lore;
	}

	@Override
	public void setLore(String lore) {
		this.lore = lore;
	}

	@Override
	public String toString() {
		return "Runestone [name=" + name + ", type=" + type + ", lore=" + lore + ", rarity=" + rarity + "]";
	}

	public List<String> getInvalids() {
		if (invalids == null) {
			invalids = new ArrayList<>();
		}
		return invalids;
	}
}
