package mod.gottsch.forge.treasure2.core.block.state.properties;

import mod.gottsch.forge.treasure2.Treasure;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TreasureWoodTypes {
    public static final WoodType WITHERWOOD_TYPE = WoodType.register(new WoodType(Treasure.MODID + ":witherwood", BlockSetType.OAK));

}
