package mod.gottsch.forge.treasure2.core.block;

import mod.gottsch.forge.treasure2.core.block.entity.TreasureSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TreasureWallSignBlock extends WallSignBlock {
    public TreasureWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TreasureSignBlockEntity(blockPos, blockState);
    }
}
