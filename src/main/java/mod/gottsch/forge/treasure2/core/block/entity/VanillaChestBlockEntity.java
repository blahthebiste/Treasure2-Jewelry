package mod.gottsch.forge.treasure2.core.block.entity;

import mod.gottsch.forge.treasure2.core.chest.ChestInventorySize;
import mod.gottsch.forge.treasure2.core.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 
 * @author Mark Gottschling on Aug 13, 2024
 *
 */
public class VanillaChestBlockEntity extends AbstractTreasureChestBlockEntity {

	/**
	 *
	 * @param pos
	 * @param state
	 */
	public VanillaChestBlockEntity(BlockPos pos, BlockState state) {
		super(TreasureBlockEntities.VANILLA_CHEST_BLOCK_ENTITY_TYPE.get(), pos, state);
	}
	
    @Override
	public Component getDefaultName() {
		return Component.translatable(LangUtil.screen("vanilla_chest.name"));
	}
    
	@Override
	public int getInventorySize() {
		return ChestInventorySize.STANDARD.getSize();
	}
}
