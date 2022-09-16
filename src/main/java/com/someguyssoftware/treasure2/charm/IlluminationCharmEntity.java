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
package com.someguyssoftware.treasure2.charm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

/**
 * @author Mark Gottschling on May 5, 2020
 *
 */
public class IlluminationCharmEntity extends CharmEntity {
	private List<ICoords> coordsList;

	/**
	 * 
	 */
//	public IlluminationCharmEntity() {
//		super();
//		coordsList = Collections.synchronizedList(new LinkedList<>());
//	}
	
	public IlluminationCharmEntity() {
		super();
	}
	
	/**
	 * 
	 * @param charm
	 */
	public IlluminationCharmEntity(ICharm charm) {
		super(charm);
		setCoordsList(Collections.synchronizedList(new LinkedList<>()));
	}
	
	public IlluminationCharmEntity(IlluminationCharmEntity entity) {
		super(entity);
		setCoordsList(Collections.synchronizedList(new LinkedList<>()));
		entity.getCoordsList().forEach(coords -> {
			ICoords newCoords = new Coords(coords);
			getCoordsList().add(newCoords);
		});
	}
	
	/**
	 * 
	 * @param value
	 * @param duration
	 * @param percent
	 */
//	public IlluminationCharmEntity(double value, int duration, double percent) {
//		this();
//		setValue(value);
//		setDuration(duration);
//		setPercent(percent);
//	}

	/**
	 * 
	 */
	@Override
	public boolean load(CompoundNBT nbt) {
		super.load(nbt);		
		ListNBT list = nbt.getList("illuminationCoords", 10);
//		Treasure.logger.debug("illumination tag list size -> {}", list.tagCount());
		for (int i = 0; i < list.size(); i++) {
			CompoundNBT tag = list.getCompound(i);
			ICoords coords = ICoords.readFromNBT(tag);
			if (coords != null) {
				getCoordsList().add(coords);
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param tag
	 * @return
	 */
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt = super.save(nbt);
		try {
			ListNBT list = new ListNBT();
			synchronized (coordsList) {
				for (ICoords coords : coordsList) {
					// create a new nbt
					CompoundNBT coordsTag = new CompoundNBT();
					coords.writeToNBT(coordsTag);
					list.add(coordsTag);
				}
			}
			nbt.remove("illuminationCoords");
			nbt.put("illuminationCoords", list);
		}
		catch(Exception e) {
			Treasure.LOGGER.error("Unable to write state to NBT:", e);
		}
		return nbt;
	}

	/**
	 * 
	 * @return
	 */
	public List<ICoords> getCoordsList() {
		if (coordsList == null) {
			coordsList = new LinkedList<>();
		}
		return coordsList;
	}

	/**
	 * 
	 * @param blockList
	 */
	public void setCoordsList(List<ICoords> blockList) {
		this.coordsList = blockList;
	}

	@Override
	public String toString() {
		return "IlluminationCharmData [" + /*coordsList=" + coordsList + ",*/ " toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordsList == null) ? 0 : coordsList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IlluminationCharmEntity other = (IlluminationCharmEntity) obj;
		if (coordsList == null) {
			if (other.coordsList != null)
				return false;
		} else if (!coordsList.equals(other.coordsList))
			return false;
		return true;
	}

}
