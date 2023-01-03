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
package com.someguyssoftware.treasure2.block;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.someguyssoftware.gottschcore.block.ModContainerBlock;
import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.spatial.Heading;
import com.someguyssoftware.gottschcore.spatial.Rotate;
import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.chest.ILockSlot;
import com.someguyssoftware.treasure2.chest.TreasureChestType;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.lock.LockState;
import com.someguyssoftware.treasure2.tileentity.AbstractTreasureChestTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author Mark Gottschling on Sep 16, 2018
 *
 */
public abstract class AbstractChestBlock extends ModContainerBlock implements ITreasureBlock {
	public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);

	/*
	 *  the class of the tileEntityClass this BlockChest should use.
	 */
	private Class<?> tileEntityClass;

	/*
	 * the concrete object of the tile entity
	 */
	private AbstractTreasureChestTileEntity tileEntity;

	/*
	 * the type of chest
	 */
	private TreasureChestType chestType;

	/*
	 * the rarity of the chest
	 */
	private Rarity rarity;

	/*
	 * An array of VoxelShape shapes for the bounding box
	 */
	private VoxelShape[] bounds = new VoxelShape[4];

	/**
	 * 
	 */
	public AbstractChestBlock(String modID, String name, Class<? extends AbstractTreasureChestTileEntity> te, 
			TreasureChestType type, Rarity rarity, Block.Properties properties) {
		
		super(modID, name, properties);
		setTileEntityClass(te);
		setChestType(type);
		setRarity(rarity);

		// set the default shapes/shape
		VoxelShape shape = Block.box(1, 0, 1, 15, 14, 15);
		setBounds(
				new VoxelShape[] {
						shape, 	// N
						shape,  	// E
						shape,  	// S
						shape	// W
				});

		// set the tile entity reference
		try {
			setTileEntity((AbstractTreasureChestTileEntity)getTileEntityClass().newInstance());
		}
		catch(Exception e) {
			Treasure.LOGGER.warn("Unable to create reference AbstractTreasureChestTileEntity object.");
		}

	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.block.ITileEntityProvider#createNewTileEntity(net.minecraft.world.World, int)
	 */
	@Override
	 public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		AbstractTreasureChestTileEntity chestTileEntity = null;
		try {
			chestTileEntity = (AbstractTreasureChestTileEntity) getTileEntityClass().newInstance();

			// setup lock states
			List<LockState> lockStates = new LinkedList<>();

			for (int i = 0; i < chestType.getSlots().length; i++) {
				LockState lockState = new LockState();
				lockState.setSlot(chestType.getSlots()[i]);
				// add in order of slot indexes
				lockStates.add(lockState.getSlot().getIndex(), lockState);
			}
			chestTileEntity.setLockStates(lockStates);
			Treasure.LOGGER.info("AbstractChestBlock | createNewTileEntity | lockStates -> {}", chestTileEntity.getLockStates());
		}
		catch(Exception e) {
			Treasure.LOGGER.error(e);
		}
		Treasure.LOGGER.info("AbstractChestBlock | createNewTileEntity | tileEntity -> {} @ {}", chestTileEntity, chestTileEntity.getBlockPos());
		return chestTileEntity;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	// TODO make interface and add to interface
	/**
	 * Convenience method.
	 * @param state
	 * @return
	 */
	public static Direction getFacing(BlockState state) {
		return state.getValue(FACING);
	}
	
	/**
	 * 
	 */
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.getValue(FACING)) {
		default:
		case NORTH:
			return bounds[0];
		case EAST:
			return bounds[1];
		case SOUTH:
			return bounds[2];
		case WEST:
			return bounds[3];
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockState = this.defaultBlockState().setValue(FACING,
				context.getHorizontalDirection().getOpposite());
		return blockState;
	}

	/**
	 * 
	 */
//	@Override
//	public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos) {
//		return false;
//	}
	
	/**
	 * Called just after the player places a block.
	 */
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		Treasure.LOGGER.debug("Placing chest from item");

		boolean shouldRotate = false;
		boolean shouldUpdate = false;
		boolean forceUpdate = false;
		AbstractTreasureChestTileEntity tcte = null;
		Heading oldPersistedChestDirection = Heading.NORTH;

		// face the block towards the player (there isn't really a front)
		worldIn.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 3);
		TileEntity te = worldIn.getBlockEntity(pos);
		if (te != null && te instanceof AbstractTreasureChestTileEntity) {
			// get the backing tile entity
			tcte = (AbstractTreasureChestTileEntity) te;

			// set the name of the chest
			if (stack.hasCustomHoverName()) {
				tcte.setCustomName(stack.getDisplayName());
			}
			
			// read in nbt
			if (stack.hasTag()) {
				tcte.readFromItemStackNBT(stack.getTag());
				forceUpdate = true;

				// get the old tcte facing direction
				oldPersistedChestDirection = Heading.fromDirection(tcte.getFacing()); // TODO might be byHorizontalIndex

				// dump stack NBT
//				if (Treasure.LOGGER.isDebugEnabled()) {
//					dump(stack.getTag(), new Coords(pos), "STACK ITEM -> CHEST NBT");
//				}
			}

			// get the direction the block is facing.
			Heading direction = Heading.fromDirection(placer.getDirection().getOpposite());

			// rotate the lock states
			shouldUpdate = rotateLockStates(worldIn, pos, oldPersistedChestDirection.getRotation(direction)); // old ->
			// Direction.NORTH
			// //

						Treasure.LOGGER.debug("New lock states ->");
						for (LockState ls : tcte.getLockStates()) {
							Treasure.LOGGER.debug(ls);
						}

			// update the TCTE facing
			tcte.setFacing(placer.getDirection().getOpposite());
		}
		if ((forceUpdate || shouldUpdate) && tcte != null) {
			// update the client
			tcte.sendUpdates();
		}
	}

	/**
	 * 
	 */
	@Override
	   public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {

		AbstractTreasureChestTileEntity tileEntity = (AbstractTreasureChestTileEntity) world.getBlockEntity(pos);

		// exit if on the client
		if (WorldInfo.isClientSide(world)) {
			return ActionResultType.SUCCESS;
		}

		// determine if chest is locked
		if (!tileEntity.isLocked()) {
			// get the container provider
			INamedContainerProvider namedContainerProvider = this.getContainer(state, world, pos);			
			// open the chest
			NetworkHooks.openGui((ServerPlayerEntity)player, namedContainerProvider, (packetBuffer)->{});
			// NOTE: (packetBuffer)->{} is just a do-nothing because we have no extra data to send
		}

		return ActionResultType.SUCCESS;
	}


	@Override
	public void destroy(IWorld worldIn, BlockPos pos, BlockState state) {
		Treasure.LOGGER.info("block destroyed by player. should happen after block is broken/replaced");
		super.destroy(worldIn, pos, state);
	}


	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		Treasure.LOGGER.debug("Removing block....!");

		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		AbstractTreasureChestTileEntity te = null;
		if (tileEntity instanceof AbstractTreasureChestTileEntity) {
			te = (AbstractTreasureChestTileEntity)tileEntity;
		}

		if (te != null) {
			// unlocked!
			if (!te.isLocked()) {
				/*
				 * spawn inventory items
				 */
				InventoryHelper.dropContents(worldIn, pos, (IInventory) te);

				/*
				 * spawn chest item
				 */
				ItemStack chestItem = new ItemStack(Item.byBlock(this), 1);
				Treasure.LOGGER.debug("Item being created from chest -> {}", chestItem.getItem().getRegistryName());
				InventoryHelper.dropItemStack(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
						chestItem);

				/*
				 * write the properties to the nbt
				 */
				if (!chestItem.hasTag()) {
					chestItem.setTag(new CompoundNBT());
				}
				te.writePropertiesToNBT(chestItem.getTag());
			} else {
				Treasure.LOGGER.debug("[BreakingBlock] ChestConfig is locked, save locks and items to NBT");

				/*
				 * spawn chest item
				 */

				if (WorldInfo.isServerSide(worldIn)) {
					ItemStack chestItem = new ItemStack(Item.byBlock(this), 1);

					// give the chest a tag compound
					//					Treasure.LOGGER.debug("[BreakingBlock]Saving chest items:");

					CompoundNBT nbt = new CompoundNBT();
					nbt = te.save(nbt);
					chestItem.setTag(nbt);

					InventoryHelper.dropItemStack(worldIn, (double) pos.getX(), (double) pos.getY(),
							(double) pos.getZ(), chestItem);

					// TEST log all items in item
					//					NonNullList<ItemStack> items = NonNullList.<ItemStack>setValueSize(27, ItemStack.EMPTY);
					//					ItemStackHelper.loadAllItems(chestItem.getTagCompound(), items);
					//					for (ItemStack stack : items) {
					//						Treasure.LOGGER.debug("[BreakingBlock] item in chest item -> {}", stack.getDisplayName());
					//					}
				}
			}

			// remove the tile entity
			worldIn.removeBlockEntity(pos);
		}
		else {
			// default to regular block break;
			//			super.breakBlock(worldIn, pos, state);
			//			worldIn.destroyBlock()
			//			super.onBlockHarvested(worldIn, pos, state, player);
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	// replaces getItemDropped
	public static void spawnDrops(BlockState state, World worldIn, BlockPos pos) {
		return;
	}

//	@Override
//	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
//		return false;
//	}
	
	/**
	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	 * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
	 */
//	@Override
//	public BlockRenderType getRenderType(BlockState state) {
//		return BlockRenderType.ENTITYBLOCK_ANIMATED;
//	}
	
	@Override
   public BlockRenderType getRenderShape(BlockState state) {
      return BlockRenderType.ENTITYBLOCK_ANIMATED;
   }

	/**
	 * Gets whether the provided {@link VoxelShape} is opaque
	 * used by the renderer to control lighting and visibility of other blocks.
	 * set to false because this block doesn't fill the entire 1x1x1 space
	 */
	public static boolean isOpaque(VoxelShape shape) {
		return false;
	}

	/**
	 * 
	 * @param world
	 * @param pos
	 * @param rotate
	 * @return
	 */
	public boolean  rotateLockStates(IWorld world, BlockPos pos, Rotate rotate) {
		// TODO replace pos with ICoords
		boolean hasRotated = false;
		boolean shouldRotate = false;
		if (rotate != Rotate.NO_ROTATE) shouldRotate = true;
		//		Treasure.LOGGER.debug("Rotate to:" + rotate);

		AbstractTreasureChestTileEntity tcte = null;
		TileEntity te = world.getBlockEntity(pos);
		if (te != null && te instanceof AbstractTreasureChestTileEntity) {
			// get the backing tile entity
			tcte = (AbstractTreasureChestTileEntity) te;
		}
		else {
			return false;
		}

		try {
			for (LockState lockState : tcte.getLockStates()) {
				if (lockState != null && lockState.getSlot() != null) {
					//					Treasure.LOGGER.debug("Original lock state:" + lockState);
					// if a rotation is needed
					if (shouldRotate) {
						ILockSlot newSlot = lockState.getSlot().rotate(rotate);
						//						Treasure.LOGGER.debug("New slot position:" + newSlot);
						lockState.setSlot(newSlot);
						// set the flag to indicate the lockStates have rotated
						hasRotated = true;
					}
				}
			}
		}
		catch(Exception e) {
			Treasure.LOGGER.error("Error updating lock states: ", e);
		}
		return hasRotated;
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	/**
	 * Returns the blockstate setValue the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#setValueRotation(Rotation)} whenever possible. Implementing/overriding is
	 * fine.
	 */
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate setValue the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#setValueMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	 */
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	/**
	 * @return the tileEntityClass
	 */
	public Class<?> getTileEntityClass() {
		return tileEntityClass;
	}

	/**
	 * @param tileEntityClass the tileEntityClass to set
	 */
	public void setTileEntityClass(Class<?> tileEntityClass) {
		this.tileEntityClass = tileEntityClass;
	}

	/**
	 * @return the tileEntity
	 */
	public AbstractTreasureChestTileEntity getTileEntity() {
		return tileEntity;
	}

	/**
	 * @param tileEntity the tileEntity to set
	 */
	public AbstractChestBlock setTileEntity(AbstractTreasureChestTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		return this;
	}

	/**
	 * @return the chestType
	 */
	public TreasureChestType getChestType() {
		return chestType;
	}

	/**
	 * @param chestType the chestType to set
	 */
	public AbstractChestBlock setChestType(TreasureChestType chestType) {
		this.chestType = chestType;
		return this;
	}

	/**
	 * @return the rarity
	 */
	public Rarity getRarity() {
		return rarity;
	}

	/**
	 * @param rarity the rarity to set
	 */
	public AbstractChestBlock setRarity(Rarity rarity) {
		this.rarity = rarity;
		return this;
	}

	public VoxelShape[] getBounds() {
		return bounds;
	}

	public AbstractChestBlock setBounds(VoxelShape[] bounds) {
		this.bounds = bounds;
		return this;
	}
}
