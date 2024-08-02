/*
 * This file is part of  Treasure2.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.core.block.entity;

import mod.gottsch.forge.treasure2.core.world.feature.FeatureType;
import mod.gottsch.forge.treasure2.core.world.feature.IFeatureType;
import mod.gottsch.forge.treasure2.core.world.feature.gen.IFeatureGenerator;
import mod.gottsch.forge.treasure2.core.world.feature.gen.TreasureFeatureGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 *
 * @author Mark Gottschling on July 28, 2024
 *
 */
public class DeferredPitGeneratorBlockEntity extends DeferredGeneratorBlockEntity {

    public DeferredPitGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.DEFERRED_PIT_GENERATOR_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public IFeatureType getFeatureType() {
        return FeatureType.TERRANEAN;
    }

    @Override
    public IFeatureGenerator getFeatureGenerator() {
        return TreasureFeatureGenerators.PIT_FEATURE_GENERATOR;
    }
}
