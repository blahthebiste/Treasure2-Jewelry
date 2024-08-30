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

import mod.gottsch.forge.gottschcore.block.entity.AbstractProximityBlockEntity;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import mod.gottsch.forge.gottschcore.spatial.Coords;
import mod.gottsch.forge.gottschcore.spatial.ICoords;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.registry.MobSetRegistry;
import mod.gottsch.forge.treasure2.core.size.IntegerRange;
import mod.gottsch.forge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class ProximityMobSetSpawnerBlockEntity extends AbstractProximityBlockEntity {
    private static final String MOBSET_NAME = "mobSetName";
    private static final String MIN_MOBS = "minMobs";
    private static final String MAX_MOBS = "maxMobs";

    private static final ResourceLocation DEFAULT_MOB = ModUtil.asLocation("minecraft:zombie");

    private ResourceLocation mobSetName;
    private IntegerRange mobSizeRange;

    /**
     *
     * @param pos
     * @param state
     */
    public ProximityMobSetSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(TreasureBlockEntities.PROXIMITY_MOBSET_SPAWNER_ENTITY_TYPE.get(), pos, state);
    }

    public ProximityMobSetSpawnerBlockEntity(BlockPos pos, BlockState state, double proximity) {
        super(TreasureBlockEntities.PROXIMITY_MOBSET_SPAWNER_ENTITY_TYPE.get(), pos, state, proximity);
    }

    public void load(CompoundTag tag) {
        super.load(tag);

        try {
            if (tag.contains(MOBSET_NAME)) {
                setMobSetName(ModUtil.asLocation(tag.getString(MOBSET_NAME)));
            }

            int min = 1;
            int max = 1;
            if (tag.contains(MIN_MOBS)) {
                min = tag.getInt(MIN_MOBS);
            }
            if (tag.contains(MAX_MOBS)) {
                max = tag.getInt(MAX_MOBS);
            }
            this.mobSizeRange = new IntegerRange(min, max);

        } catch (Exception e) {
            Treasure.LOGGER.error("error reading TreasureProximityMultiSpawnerBlockEntity properties from tag:", e);
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString(MOBSET_NAME, getMobSetName().toString());
        tag.putInt(MIN_MOBS, this.getMobSizeRange().getMin());
        tag.putInt(MAX_MOBS, this.getMobSizeRange().getMax());
    }

    private void defaultMobSpawnerSettings() {
        setMobSetName(new ResourceLocation("minecraft", "small_zombie_group"));
        this.setMobSizeRange(new IntegerRange(1, 1));
        this.setProximity(5.0);
    }

    /**
     *
     */
    public void tickServer() {
        if (!this.level.isClientSide()) {
            boolean isTriggered = false;
            double proximitySq = this.getProximity() * this.getProximity();
            if (proximitySq < 1.0) {
                proximitySq = 1.0;
            }

            Iterator players = this.getLevel().players().iterator();

            while(players.hasNext()) {
                Player player = (Player)players.next();
                double distanceSq = player.distanceToSqr((double)this.getBlockPos().getX(), (double)this.getBlockPos().getY(), (double)this.getBlockPos().getZ());
                if (!isTriggered && !this.isDead() && distanceSq < proximitySq) {
                    Treasure.LOGGER.debug("proximity @ -> {} was met.", (new Coords(this.getBlockPos())).toShortString());
                    isTriggered = true;
                    Treasure.LOGGER.debug("proximity pos -> {}", this.getBlockPos());
                    this.execute(this.level, this.level.getRandom(), new Coords(this.getBlockPos()), new Coords(player.blockPosition()));
                }

                if (this.isDead()) {
                    break;
                }
            }

        }
    }

    public void execute(Level world, RandomSource random, ICoords blockCoords, ICoords playerCoords) {
        if (!world.isClientSide()) {
            ServerLevel level = (ServerLevel)world;
            int numberOfMobs = RandomHelper.randomInt(random, this.getMobSizeRange().getMin(), this.getMobSizeRange().getMax());

            Optional<WeightedCollection<Integer, ResourceLocation>> weightedMobs = MobSetRegistry.get(getMobSetName());
            if (weightedMobs.isPresent()) {
                // for the number of mobs
                for (int x = 0; x < numberOfMobs; ++x) {
                    ResourceLocation mobName = DEFAULT_MOB;
                    if (!weightedMobs.get().isEmpty()) {
                        mobName = weightedMobs.get().next();
                    }

                    Optional<EntityType<?>> entityType = EntityType.byString(mobName.toString());
                    if (entityType.isEmpty()) {
                        Treasure.LOGGER.debug("unable to get entityType -> {}", mobName);
                        weightedMobs.get().remove(mobName);
                        continue;
                    }
                    Entity mob = ((EntityType<?>) entityType.get()).create(level);
                    if (mob instanceof Mob) {
                        ForgeEventFactory.onFinalizeSpawn((Mob)mob, level, level.getCurrentDifficultyAt(getBlockPos()), MobSpawnType.EVENT, null, null);
                    }
                    ModUtil.SpawnEntityHelper.spawn(level, random, entityType.get(), mob, blockCoords);
                }
            }
            this.selfDestruct();
        }
    }

    private void selfDestruct() {
        Treasure.LOGGER.debug("self-destructing @ {}", this.getBlockPos());
        this.setDead(true);
        this.getLevel().setBlock(this.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
        this.getLevel().removeBlockEntity(this.getBlockPos());
    }

    public ResourceLocation getMobSetName() {
        return mobSetName;
    }

    public void setMobSetName(ResourceLocation mobSetName) {
        this.mobSetName = mobSetName;
    }

    public IntegerRange getMobSizeRange() {
        return mobSizeRange;
    }

    public void setMobSizeRange(IntegerRange mobSizeRange) {
        this.mobSizeRange = mobSizeRange;
    }
}
