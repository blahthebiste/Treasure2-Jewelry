/**
 * 
 */
package com.someguyssoftware.treasure2.block;

import com.someguyssoftware.gottschcore.block.ModBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/**
 * TODO is this really necessary? maybe because it requires isNormalCube ??
 * @author Mark Gottschling on Dec 4, 2018
 *
 */
public class TreasureOreBlock extends ModBlock implements ITreasureBlock {
	
	/**
	 * 
	 * @param material
	 */
	public TreasureOreBlock(String modID, String name, Block.Properties properties) {
		super(modID, name, properties.sound(SoundType.STONE));
	}
//    
//	/**
//	 * 
//	 */
//	@Override
//	public boolean isNormalCube(BlockState state, LevelAccessor world, BlockPos pos) {
//		return false;
//	}
}