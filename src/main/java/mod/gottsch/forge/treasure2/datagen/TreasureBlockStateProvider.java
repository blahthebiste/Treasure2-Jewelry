package mod.gottsch.forge.treasure2.datagen;

import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Mark Gottschling on Aug 26, 2024
 *
 */
public class TreasureBlockStateProvider extends BlockStateProvider {

    public TreasureBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Treasure.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        signBlock(((StandingSignBlock) TreasureBlocks.WITHERWOOD_SIGN.get()), ((WallSignBlock) TreasureBlocks.WITHERWOOD_WALL_SIGN.get()),
                blockTexture(TreasureBlocks.WITHERWOOD_PLANKS.get()));

        hangingSignBlock(TreasureBlocks.WITHERWOOD_HANGING_SIGN.get(), TreasureBlocks.WITHERWOOD_WALL_HANGING_SIGN.get(), blockTexture(TreasureBlocks.WITHERWOOD_PLANKS.get()));

    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
