/*
 * This file is part of  Treasure2.
 * Copyright (c) 2018 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.core.generator;

import java.util.List;
import java.util.Optional;

import mod.gottsch.forge.gottschcore.block.BlockContext;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.size.DoubleRange;
import mod.gottsch.forge.gottschcore.spatial.Coords;
import mod.gottsch.forge.gottschcore.spatial.Heading;
import mod.gottsch.forge.gottschcore.spatial.ICoords;
import mod.gottsch.forge.gottschcore.world.IWorldGenContext;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import mod.gottsch.forge.gottschcore.world.gen.structure.BlockInfoContext;
import mod.gottsch.forge.gottschcore.world.gen.structure.PlacementSettings;
import mod.gottsch.forge.gottschcore.world.gen.structure.StructureMarkers;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.block.AbstractTreasureChestBlock;
import mod.gottsch.forge.treasure2.core.block.SkeletonBlock;
import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.forge.treasure2.core.block.entity.*;
import mod.gottsch.forge.treasure2.core.config.Config;
import mod.gottsch.forge.treasure2.core.config.MobSetConfiguration;
import mod.gottsch.forge.treasure2.core.config.StructureConfiguration;
import mod.gottsch.forge.treasure2.core.generator.template.TemplatePoiInspector;
import mod.gottsch.forge.treasure2.core.registry.MimicRegistry;
import mod.gottsch.forge.treasure2.core.registry.TreasureTemplateRegistry;
import mod.gottsch.forge.treasure2.core.size.IntegerRange;
import mod.gottsch.forge.treasure2.core.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author Mark Gottschling on Jan 24, 2018
 *
 */
public class GeneratorUtil {
	public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);

		/**
		 * convenience method
		 * 
		 * @param offset
		 * @return
		 */
		public static Block getMarkerBlock(StructureMarkers marker) {
			return TreasureTemplateRegistry.getMarkerMap().get(marker);
		}

	/**
	 * 
	 * @param world
	 * @param random
	 * @param coords
	 * @param block
	 * @return
	 */
	public static boolean replaceWithBlock(ServerLevelAccessor world, ICoords coords, Block block) {
		// don't change if old block is air
		BlockContext context = new BlockContext(world, coords);
		if (context.isAir()) {
			return false;
		}
		world.setBlock(coords.toPos(), block.defaultBlockState(), 3);
		return true;
	}

	/**
	 * 
	 * @param world
	 * @param coords
	 * @param blockState
	 * @return
	 */
	public static boolean replaceWithBlockState(ServerLevelAccessor world, ICoords coords, BlockState blockState) {
		// don't change if old block is air
		BlockContext context = new BlockContext(world, coords);
		if (context.isAir()) {
			return false;
		}
		world.setBlock(coords.toPos(), blockState, 3);
		return true;
	}

	/**
	 * 
	 * @param chest
	 * @param coords
	 * @return
	 */
	public static boolean replaceBlockWithChest(IWorldGenContext context, Block chest, ICoords coords, boolean discovered) {
		// get the old state
		BlockState oldState = context.level().getBlockState(coords.toPos());

		if (oldState.getProperties().contains(FACING)) {
			// set the new state
			return placeChest(context.level(), chest, coords, (Direction) oldState.getValue(FACING), discovered);

		} else {
			return placeChest(context.level(), chest, coords, Direction.from2DDataValue(context.random().nextInt(4)), discovered);
		}
	}

	/**
	 * 
	 * @param coords
	 * @param chest
	 * @param state
	 * @return
	 */
	public static boolean replaceBlockWithChest(IWorldGenContext context, ICoords coords, 
			Block chest,	BlockState state, boolean discovered) {
		if (state.getProperties().contains(FACING)) {
			return placeChest(context.level(), chest, coords, (Direction) state.getValue(FACING), discovered);
		}

		if (state.getBlock() == Blocks.CHEST) {
			Direction direction = (Direction) state.getValue(ChestBlock.FACING);
			return placeChest(context.level(), chest, coords, direction, discovered);
		}

		// else do generic
		return replaceBlockWithChest(context, chest, coords, discovered);
	}

	/**
	 * 
	 * @param level
	 * @param chest
	 * @param pos
	 * @return
	 */
	public static boolean placeChest(ServerLevelAccessor level, Block chest, ICoords coords, Direction direction, boolean discovered) {
		// check if spawn pos is valid
		if (!WorldInfo.isHeightValid(coords)) {
			Treasure.LOGGER.debug("cannot place chest due to invalid height -> {}", coords.toShortString());
			return false;
		}

		try {
			BlockPos pos = coords.toPos();
			// create and place the chest
			WorldInfo.setBlock(level, coords, chest
					.defaultBlockState()
					.setValue(FACING, direction)
					.setValue(AbstractTreasureChestBlock.DISCOVERED, discovered));
			Treasure.LOGGER.debug("placed chest -> {} into world at coords -> {} with prop -> {}",
					chest.getClass().getSimpleName(), coords.toShortString(), direction);

			// get the direction the block is facing.
			Heading heading = Heading.fromDirection(direction);
			((AbstractTreasureChestBlock) chest).rotateLockStates(level, coords, Heading.NORTH.getRotation(heading));

			// get the tile entity
			BlockEntity blockEntity = (BlockEntity) level.getBlockEntity(pos);

			if (blockEntity == null) {
				// remove the chest block
				WorldInfo.setBlock(level, coords, Blocks.AIR.defaultBlockState());
				Treasure.LOGGER.warn("unable to create treasure chest's BlockEntity, removing chest.");
				return false;
			}
			// update the facing
			((AbstractTreasureChestBlockEntity) blockEntity).setFacing(direction.get3DDataValue());
		} catch (Exception e) {
			Treasure.LOGGER.error("An error occurred placing chest: ", e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param coords
	 */
	public static void placeSkeleton(IWorldGenContext context, ICoords coords) {
		// select a random facing direction
		Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
		ICoords coords2 = new Coords(coords.toPos().relative(facing));

		BlockContext blockContext = new BlockContext(context.level(), coords);
		BlockContext blockContext2 = new BlockContext(context.level(), coords2);

		boolean flag = blockContext.isReplaceable();
		boolean flag1 = blockContext2.isReplaceable();
		boolean flag2 = flag || blockContext.isAir();
		boolean flag3 = flag1 || blockContext2.isAir();

		if (flag2 && flag3
				&& context.level().getBlockState(coords.down(1).toPos()).isSolidRender(context.level(), coords.down(1).toPos())
				&& context.level().getBlockState(coords2.down(1).toPos()).isSolidRender(context.level(), coords2.down(1).toPos())) {
			BlockState skeletonState = TreasureBlocks.SKELETON.get().defaultBlockState()
					.setValue(SkeletonBlock.FACING, facing.getOpposite())
					.setValue(SkeletonBlock.PART, SkeletonBlock.EnumPartType.BOTTOM);

			context.level().setBlock(coords.toPos(), skeletonState, 3);
			context.level().setBlock(coords2.toPos(),
					skeletonState.setValue(SkeletonBlock.PART, SkeletonBlock.EnumPartType.TOP), 3);

			context.level().blockUpdated(coords.toPos(), blockContext.getState().getBlock());
			context.level().blockUpdated(coords2.toPos(), blockContext.getState().getBlock());
		}
	}

	/**
	 * 
	 * @param world
	 * @param coords
	 * @return
	 */
	public static ICoords findSubterraneanCeiling(ServerLevelAccessor world, ICoords coords) {
		// TODO a better solution would take in the ChunkGenerator and find the
		// surface height. if coords exceeds the surface height, then fail.
		final int CEILING_FAIL_SAFE = 50;
		int ceilingHeight = 1;

		// find the ceiling of the cavern
		while (world.isEmptyBlock(coords.toPos())) {
			ceilingHeight++;
			if (ceilingHeight > world.getHeight() || ceilingHeight == CEILING_FAIL_SAFE) {
				return null;
			}
			coords = coords.add(0, 1, 0);
		}
		// add 1 height to the final pos
		coords = coords.add(0, 1, 0);
		return coords;
	}



	/**
	 *
	 * @param coords
	 * @param rotatedSize
	 * @param placement
	 * @return
	 */
	public static ICoords align(ICoords coords, BlockPos rotatedSize, PlacementSettings placement) {
		return switch(placement.getRotation()) {
			default -> coords;
			case CLOCKWISE_90 -> coords.add((rotatedSize.getX() - 1) / 2, 0, -rotatedSize.getZ() / 2);
			case CLOCKWISE_180 -> coords.add((rotatedSize.getX() - 1) / 2, 0, (rotatedSize.getZ() - 1) / 2);
			case COUNTERCLOCKWISE_90 -> coords.add(-rotatedSize.getX() / 2, 0, (rotatedSize.getZ() - 1) / 2);
		};
	}

	/**
	 *
	 * @param coords
	 * @param rotatedSize
	 * @param placement
	 * @return
	 */
	public static ICoords standardizePosition(ICoords coords, BlockPos rotatedSize, PlacementSettings placement) {
		return switch(placement.getRotation()) {
			case CLOCKWISE_90 -> coords.add(-(rotatedSize.getX() - 1), 0, 0);
			case CLOCKWISE_180 -> coords.add(-(rotatedSize.getX() - 1), 0, -(rotatedSize.getZ() - 1));
			case COUNTERCLOCKWISE_90 -> coords.add(0, 0, -(rotatedSize.getZ() - 1));
			default ->coords;
		};
	}


	public static void fillBelow(IWorldGenContext context, ICoords coords, BlockPos size, int depth, BlockState blockState) {
		Treasure.LOGGER.debug("filling starting at -> {}", coords.toShortString());

		for (int y = 1; y <= depth; y++) {
			for (int x = 0; x < size.getX(); x++) {
				for (int z = 0; z < size.getZ(); z++) {
					ICoords c = coords.add(x, -y, z);
//					Treasure.LOGGER.debug("checking fill block -> {} is air -> {}", c.toShortString(), context.level().getBlockState(c.toPos()).isAir());

					if (context.level().getBlockState(c.toPos()).isAir()) {
						Treasure.LOGGER.debug("placing fill block -> {}", c.toShortString());
						context.level().setBlock(c.toPos(), blockState, 3);
					}
				}
			}
		}
	}

	/**
	 * select a single chest from all boss and vanilla chests.
	 * @param inspector
	 * @return
	 */
	public static Optional<BlockInfoContext> selectTreasureChest(IWorldGenContext context, TemplatePoiInspector inspector) {
		// check if there is a boss chest(s)
		BlockInfoContext chestContext = null;
		if (!inspector.getBossChests().isEmpty()) {
			Treasure.LOGGER.debug("selecting a boss chest. size -> {}", inspector.getBossChests().size());
			chestContext = inspector.getBossChests().get(context.random().nextInt(inspector.getBossChests().size()));
			Treasure.LOGGER.debug("boss chest context -> {}", chestContext.getCoords());
		} else {
			Treasure.LOGGER.debug("boss chests are empty");
			if (!inspector.getChests().isEmpty()) {
				chestContext = inspector.getChests().get(context.random().nextInt(inspector.getChests().size()));
				// remove the chest from the list of available chests
				inspector.getChests().remove(chestContext);
			}
		}
		Treasure.LOGGER.debug("chest context -> {}", chestContext);
		return Optional.ofNullable(chestContext);
	}

	/**
	 *
	 * @param context
	 * @param inspector
	 * @param meta
	 * @return
	 */
	public static Optional<MobSetConfiguration.MobSet> selectMobSet(IWorldGenContext context, TemplatePoiInspector inspector, StructureConfiguration.StructMeta meta) {
		MobSetConfiguration.MobSet mobSet = null;
		if(StringUtils.isNotBlank(meta.getMobSet()) || !meta.getMobSets().isEmpty()) {

			// first try and select a mobSet from the mobSets
			ResourceLocation mobSetName = null;
			if (!meta.getMobSets().isEmpty()) {
				mobSetName = ModUtil.asLocation(meta.getMobSets().get(context.random().nextInt(meta.getMobSets().size())));
			} else {
				mobSetName = ModUtil.asLocation(meta.getMobSet());
			}

			Treasure.LOGGER.debug("meta has a mob set -> {}", mobSetName);

			if (Config.mobSetMap.containsKey(mobSetName)) {
				mobSet = Config.mobSetMap.get(mobSetName);
				return Optional.ofNullable(mobSet);
			}
		}
		return Optional.empty();
	}

	/**
	 * TODO need to pass in the loot table
	 * @param context
	 * @param placement
	 * @param inspector
	 */
	public static void buildVanillaChests(IWorldGenContext context, PlacementSettings placement, TemplatePoiInspector inspector, ResourceLocation lootTable) {
		for (BlockInfoContext c : inspector.getChests()) {
			Treasure.LOGGER.debug("placing vanilla chest at -> {}", c.getCoords().toShortString());

			// add mimic if any
			if (Config.SERVER.mobs.enableMimics.get() && RandomHelper.checkProbability(context.random(), Config.SERVER.mobs.mimicProbability.get())) {
				Optional<ResourceLocation> mimicName = MimicRegistry.getMimic(ModUtil.getName(TreasureBlocks.VANILLA_CHEST.get()));
				if (mimicName.isPresent()) {
					// place vanilla treasure chest
					context.level().setBlock(c.getCoords().toPos(),
							TreasureBlocks.VANILLA_CHEST.get().rotate(TreasureBlocks.VANILLA_CHEST.get().defaultBlockState(), context.level(), c.getCoords().toPos(), placement.getRotation()),
							3);
					// set the mimic name in the block entity
					ITreasureChestBlockEntity blockEntity = (ITreasureChestBlockEntity) context.level().getBlockEntity(c.getCoords().toPos());
					blockEntity.setMimic(mimicName.get());
					blockEntity.setLootTable(lootTable);
				}
			} else {
				// TODO use SAFE PLACEMENT - see RandomizedContainerBlockEntity
				context.level().setBlock(c.getCoords().toPos(),
						Blocks.CHEST.rotate(Blocks.CHEST.defaultBlockState(), context.level(), c.getCoords().toPos(), placement.getRotation()),3);
				RandomizableContainerBlockEntity.setLootTable(context.level(), context.random(), c.getCoords().toPos(), lootTable);

			}
		}
	}

	/**
	 *
	 * @param context
	 * @param spawnerContexts
	 */
	public static void buildVanillaSpawners(IWorldGenContext context, List<BlockInfoContext> spawnerContexts) {
		for (BlockInfoContext spawnerContext : spawnerContexts) {
			try {
				Treasure.LOGGER.debug("placing vanilla spawner at -> {}", spawnerContext.getCoords().toShortString());
				// TODO no longer need deferred spawners - use regular ones.
				context.level().setBlock(spawnerContext.getCoords().toPos(), TreasureBlocks.DEFERRED_RANDOM_VANILLA_SPAWNER.get().defaultBlockState(), 3);
			} catch(Exception e) {
				Treasure.LOGGER.error("error placing vanilla spawner", e);
			}
		}
	}

	/**
	 * Adds a proximity spawner of one type of mob.
	 * @param context
	 * @param proximityContexts
	 * @param range
	 * @param proximity
	 */
	public static void buildOneTimeSpawners(IWorldGenContext context, List<BlockInfoContext> proximityContexts, DoubleRange range, double proximity) {
		for (BlockInfoContext c : proximityContexts) {
			Treasure.LOGGER.debug("placing proximity spawner at -> {}", c.getCoords().toShortString());
			context.level().setBlock(c.getCoords().toPos(), TreasureBlocks.PROXIMITY_SPAWNER.get().defaultBlockState(), 3);
			TreasureProximitySpawnerBlockEntity te = (TreasureProximitySpawnerBlockEntity) context.level().getBlockEntity(c.getCoords().toPos());
			if (te != null) {
				EntityType<?> r = DungeonHooks.getRandomDungeonMob(context.random());
				if (RandomHelper.checkProbability(context.random(), 20)) {
					r = EntityType.VINDICATOR;
				}
				Treasure.LOGGER.debug("using mob -> {} for poximity spawner.", EntityType.getKey(r).toString());
				te.setMobName(EntityType.getKey(r));
				te.setMobNum(range);
				te.setProximity(proximity);
			}
			else {
				Treasure.LOGGER.debug("unable to generate proximity spawner at -> {}", c.getCoords().toShortString());
			}
		}
	}

	/**
	 * Adds a proximity spawner of a list mobs to select from.
	 * @param context
	 * @param proximityContexts
	 * @param mobNames
	 * @param range
	 * @param proximity
	 */
	public static void buildOneTimeSpawners(IWorldGenContext context, List<BlockInfoContext> proximityContexts, List<ResourceLocation> mobNames, IntegerRange range, double proximity) {
		for (BlockInfoContext c : proximityContexts) {
			Treasure.LOGGER.debug("placing proximity spawner at -> {}", c.getCoords().toShortString());
			context.level().setBlock(c.getCoords().toPos(), TreasureBlocks.PROXIMITY_MULTI_SPAWNER.get().defaultBlockState(), 3);
			TreasureProximityMultiSpawnerBlockEntity blockEntity = (TreasureProximityMultiSpawnerBlockEntity) context.level().getBlockEntity(c.getCoords().toPos());
			if (blockEntity != null) {
				blockEntity.setMobNames(mobNames);
				blockEntity.setMobSizeRange(new IntegerRange(range.getMin(), range.getMax()));
				blockEntity.setProximity(proximity);
			}
			else {
				Treasure.LOGGER.debug("unable to generate proximity spawner at -> {}", c.getCoords().toShortString());
			}
		}
	}

	public static void buildOneTimeSpawners(IWorldGenContext context, List<BlockInfoContext> proximityContexts, MobSetConfiguration.MobSet mobSet, IntegerRange range, double proximity) {
		for (BlockInfoContext c : proximityContexts) {
			Treasure.LOGGER.debug("placing mobset proximity spawner at -> {}", c.getCoords().toShortString());
			context.level().setBlock(c.getCoords().toPos(), TreasureBlocks.PROXIMITY_MOBSET_SPAWNER.get().defaultBlockState(), 3);
			ProximityMobSetSpawnerBlockEntity blockEntity = (ProximityMobSetSpawnerBlockEntity) context.level().getBlockEntity(c.getCoords().toPos());
			if (blockEntity != null) {
				blockEntity.setMobSetName(ModUtil.asLocation(mobSet.getName()));
				// TODO if range is provided, should it override
				blockEntity.setMobSizeRange(new IntegerRange(mobSet.getCount().getMin(), mobSet.getCount().getMax()));
				blockEntity.setProximity(proximity);
			}
			else {
				Treasure.LOGGER.debug("unable to generate proximity spawner at -> {}", c.getCoords().toShortString());
			}
		}
	}
}