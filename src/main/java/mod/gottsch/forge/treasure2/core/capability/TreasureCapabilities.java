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
package mod.gottsch.forge.treasure2.core.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.items.IItemHandler;

/**
 * @author Mark Gottschling on Aug 2, 2021
 *
 */
public class TreasureCapabilities {
	@CapabilityInject(ICharmableCapability.class)
	public static Capability<ICharmableCapability> CHARMABLE = null;
	
	@CapabilityInject(IRunestonesCapability.class)
	public static Capability<IRunestonesCapability> RUNESTONES = null;
	
	@CapabilityInject(IDurabilityCapability.class)
	public static Capability<IDurabilityCapability> DURABILITY = null;
	
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> KEY_RING_INVENTORY_CAPABILITY = null;	

	@CapabilityInject(IKeyRingCapability.class)
	public static Capability<IKeyRingCapability> KEY_RING_CAPABILITY = null;
	
	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> POUCH = null;
	
	/**
	 * 
	 */
	public static void register() {
        CapabilityManager.INSTANCE.register(IDurabilityCapability.class, new DurabilityCapabilityStorage(), DurabilityCapability::new);
        CapabilityManager.INSTANCE.register(IRunestonesCapability.class, new RunestonesCapabilityStorage(), RunestonesCapability::create);
        CapabilityManager.INSTANCE.register(IKeyRingCapability.class, new KeyRingCapabilityStorage(), KeyRingCapability::new);
        CapabilityManager.INSTANCE.register(ICharmableCapability.class, new CharmableCapabilityStorage(), CharmableCapability::create);
  	}
}
