package mod.gottsch.forge.treasure2.core.block;

import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.block.entity.AbstractTreasureChestBlockEntity;
import mod.gottsch.forge.treasure2.core.block.entity.DeferredGeneratorBlockEntity;
import mod.gottsch.forge.treasure2.core.block.entity.DeferredSurfaceGeneratorBlockEntity;
import mod.gottsch.forge.treasure2.core.lock.LockLayout;
import mod.gottsch.forge.treasure2.core.lock.LockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mark Gottschling on 7/28/2024
 */
public class DeferredGeneratorBlock extends Block implements EntityBlock {

    /*
     *  the class of the tileEntityClass this DeferredGeneratorBlock should use.
	 */
    private final Class<?> blockEntityClass;

    /**
     *
     * @param be
     * @param properties
     */
    public DeferredGeneratorBlock(Class<? extends DeferredGeneratorBlockEntity> be, Properties properties) {
        super(properties);
        this.blockEntityClass = be;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        BlockEntity blockEntity = null;
        try {
            blockEntity = newInstanceBlockEntity(pos, state);
    }
        catch(Exception e) {
            Treasure.LOGGER.error(e);
        }
        return blockEntity;
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, blockState, t) -> {
                if (blockEntityClass.isInstance(t)) { // test and cast
                    ((DeferredGeneratorBlockEntity)t).tickServer();
                }
            };
        }
        return null;
    }

    /**
     *
     * @param pos
     * @param state
     * @return
     */
    protected DeferredGeneratorBlockEntity newInstanceBlockEntity(BlockPos pos, BlockState state) {
        /*
         *  construct a new instance of the block entity.
         *  ensure to use BlockPos.class and not pos.getClass() for the Class<?> type variable
         *  because when this method is called from load, a MutableBlockPos is passed in,
         *  and reflection will not be able to locate the constructor because it has a different signature,
         *  and this will save from having every concrete Chest class needing to implement a separate
         *  constructor for the MutableBlockPos.
         */
        try {
            Class<?>[] type = { BlockPos.class, BlockState.class };
            Constructor<?> cons = getBlockEntityClass().getConstructor(type);
            return (DeferredGeneratorBlockEntity) cons.newInstance(pos, state);
        }
        catch(Exception e) {
            Treasure.LOGGER.error(e);
            return null;
        }
    }

    public Class<?> getBlockEntityClass() {
        return blockEntityClass;
    }
}
