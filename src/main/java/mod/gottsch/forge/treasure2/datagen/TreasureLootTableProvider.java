package mod.gottsch.forge.treasure2.datagen;

import mod.gottsch.forge.treasure2.datagen.loot.TreasureBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class TreasureLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(TreasureBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}

