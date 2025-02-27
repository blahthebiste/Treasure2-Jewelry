/*
 * This file is part of  Treasure2.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.datagen;

import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.forge.treasure2.core.tags.TreasureTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 *
 * @author Mark Gottschling on May 20, 2023
 *
 */
public class TreasureBiomeTagsProvider extends BiomeTagsProvider {
    private String BOP = "biomesoplenty";
    private String BYG = "byg";

    /**
     *
     * @param generatorIn
     * @param existingFileHelper
     */
    public TreasureBiomeTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Treasure.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {


        // blocks rarity
//    	tag(TreasureTags.Biomes.DESERT_WELL1).add(Biomes.DESERT);
//    	tag(TreasureTags.Biomes.DESERT_WELL2).add(Biomes.DESERT);
//    	tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.BADLANDS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.BAMBOO_JUNGLE);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.BASALT_DELTAS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.BIRCH_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.BEACH);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.CRIMSON_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.DARK_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.DESERT);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.DRIPSTONE_CAVES);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.FLOWER_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.FROZEN_PEAKS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.FROZEN_RIVER);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.GROVE);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.ICE_SPIKES);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.JUNGLE);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.JAGGED_PEAKS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.LUSH_CAVES);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.MEADOW);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.MUSHROOM_FIELDS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.OLD_GROWTH_BIRCH_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.OLD_GROWTH_PINE_TAIGA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.PLAINS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.RIVER);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SAVANNA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SAVANNA_PLATEAU);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SNOWY_PLAINS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SNOWY_SLOPES);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SNOWY_TAIGA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SPARSE_JUNGLE);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.STONY_PEAKS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SUNFLOWER_PLAINS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.SWAMP);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.TAIGA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.WINDSWEPT_FOREST);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.WINDSWEPT_SAVANNA);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.WINDSWEPT_HILLS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.WINDSWEPT_GRAVELLY_HILLS);
//        tag(TreasureTags.Biomes.ALL_OVERWORLD).add(Biomes.WOODED_BADLANDS);
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addTag(BiomeTags.IS_OVERWORLD);

        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "aspen_glade"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "auroral_garden"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "bayou"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "bog"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "clover_patch"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "cold_desert"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "coniferous_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "crag"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "crystalline_chasm"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "dead_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "dryland"));

        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "dune_beach"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "end_wilds"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "end_reef"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "end_corruption"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "erupting_inferno"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "field"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "fir_clearing"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "floodplain"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "forested_field"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP,  "fungal_jungle"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP,  "glowing_grotto"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "grassland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "gravel_beach"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "highland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "hot_springs"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "jacaranda_glade"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "jade_cliffs"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "lavender_field"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "lush_desert"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "lush_savanna"));

        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "maple_woods"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "marsh"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP,  "mediterranean_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "moor"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "muskeg"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "mystic_grove"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "old_growth_dead_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "old_growth_woodland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "ominous_woods"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "orchard"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "origin_valley"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "pasture"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "prairie"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "pumpkin_patch"));

        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "rainforest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "redwood_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "rocky_rainforest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "rocky_shrubland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "scrubland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "seasonal_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "shrubland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP,  "snowblossom_grove"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "snowy_coniferous_forest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "snowy_fir_clearing"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "snowy_maple_woods"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "spider_nest"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "tropics"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "tundra"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "undergrowth"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "visceral_heap"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "volcano"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "volcanic_plains"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "wasteland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "wasteland_steppe"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "wetland"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "wintry_origin_valley"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "withered_abyss"));
        tag(TreasureTags.Biomes.ALL_OVERWORLD).addOptional(new ResourceLocation(BOP, "woodland"));

        // Oh Biomes You'll Go support
        allOptional(BYG, "allium_fields");
        allOptional(BYG, "amaranth_fields");
        allOptional(BYG, "araucaria_savanna");
        allOptional(BYG, "arisian_undergrowth");
        allOptional(BYG, "aspen_forest");
        allOptional(BYG, "atacama_desert");
        allOptional(BYG, "autumnal_forest");
        allOptional(BYG, "autumnal_taiga");
        allOptional(BYG, "autumnal_valley");
        allOptional(BYG, "baobab_savanna");
        allOptional(BYG, "basalt_barrera");
        allOptional(BYG, "bayou");
        allOptional(BYG, "black_forest");
        allOptional(BYG, "borealis_grove");
        allOptional(BYG, "brimstone_caverns");
        allOptional(BYG, "bulbis_gardens");
        allOptional(BYG, "canadian_shield");
        allOptional(BYG, "cardinal_tundra");
        allOptional(BYG, "cherry_blossom_forest");
        allOptional(BYG, "cika_woods");
        allOptional(BYG, "coconino_meadow");
        allOptional(BYG, "coniferous_forest");
        allOptional(BYG, "crag_gardens");
        allOptional(BYG, "crimson_gardens");
        allOptional(BYG, "cryptic_wastes");
        allOptional(BYG, "cypress_swamplands");
        allOptional(BYG, "dacite_ridges");
        allOptional(BYG, "dacite_shore");
        allOptional(BYG, "dead_sea");
        allOptional(BYG, "ebony_woods");
        allOptional(BYG, "embur_bog");
        allOptional(BYG, "ethereal_islands");
        allOptional(BYG, "firecracker_shrubland");
        allOptional(BYG, "forgotten_forest");
        allOptional(BYG, "fragment_forest");
        allOptional(BYG, "frosted_coniferous_forest");
        allOptional(BYG, "frosted_taiga");
        allOptional(BYG, "glowstone_gardens");
        allOptional(BYG, "guiana_shield");
        allOptional(BYG, "howling_peaks");
        allOptional(BYG, "imparius_grove");
        allOptional(BYG, "ivis_fields");
        allOptional(BYG, "jacaranda_forest");
        allOptional(BYG, "lush_stacks");
        allOptional(BYG, "magma_wastes");
        allOptional(BYG, "maple_taiga");
        allOptional(BYG, "mojave_desert");
        allOptional(BYG, "nightshade_forest");
        allOptional(BYG, "orchard");
        allOptional(BYG, "prairie");
        allOptional(BYG, "quartz_desert");
        allOptional(BYG, "rainbow_beach");
        allOptional(BYG, "red_oak_forest");
        allOptional(BYG, "red_rock_valley");
        allOptional(BYG, "redwood_thicket");
        allOptional(BYG, "rose_fields");
        allOptional(BYG, "shattered_glacier");
        allOptional(BYG, "shulkren_forest");
        allOptional(BYG, "sierra_badlands");
        allOptional(BYG, "skyris_vale");
        allOptional(BYG, "subzero_hypogeal");
        allOptional(BYG, "sythian_torrids");
        allOptional(BYG, "temperate_grove");
        allOptional(BYG, "temperate_rainforest");
        allOptional(BYG, "tropical_rainforest");
        allOptional(BYG, "twilight_meadow");
        allOptional(BYG, "viscal_isles");
        allOptional(BYG, "wailing_garth");
        allOptional(BYG, "warped_desert");
        allOptional(BYG, "weeping_mire");
        allOptional(BYG, "weeping_witch_forest");
        allOptional(BYG, "white_mangrove_marshes");
        allOptional(BYG, "windswept_desert");
        allOptional(BYG, "zelkova_forest");
    }

    private void allOptional(String domain, String biome) {
        tag(TreasureTags.Biomes.ALL_OVERWORLD)
                .addOptional(new ResourceLocation(domain, biome));
    }
}
