# Changelog for Treasure2 1.19.2

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.9.1] - 2024-12-20

### Changed
- Enabled the LivingHurtEvent so critical damage can be inflicted!
- Fix critical weapon damage -> changed from multiple to addition.

## [3.9.0] - 2024-08-01

### Changed
- Fixed Deferred generator block entities using the wrong config-based conditional statement.
- Updated treasure2-chests-1.20.1 toml file to v3.

### Added
- Added Deferred generators for Surface, Subaquatic structures, and Pits.


## [3.8.6] - 2024-07-24

### Changed
-Fixed GeometryUtil.mcRotate() for Countclockwise90/Clockwise270 rotation.

## [3.8.5] - 2024-07-23

### Changed
- Fixed pit shaft and pit room alignments! For real this time.
- Updated crypt3.nbt to include the missing bottom level which had the entrance marker.
- Updated treasure2-structures toml file.
- Updated StructureMarkerGenerator to use mcRotate() instead of rotate()
- Updated StructurePitGenerator to use mcRotate() instead of rotate()

### Added
- Added Oh Biomes You'll Go support.
- Added crypt5.nbt that is like crypt3 but with a full skeleton.
- Added mcRotate() to GeometryUtil.


## [3.8.4] - 2024-07-18

### Changed

- Fixed add patchouli guide book on first use.
- Added condition to KeyItem.isDamageable() to prevent exception due to mixin use.

## [3.8.3] - 2024-07-17

### Changed

- Added the Falling blocks names to the language file.
- Fixed all_overworld biome modifier tag so that chests spawn correctly and at the correct frequency.

## [3.8.2] - 2024-07-11

### Changed

- Fixed crafted Keys from only having 0 durability/usage on server.
- Fixed reading datapacks in flat/exploded directory format.
- Fixed high-level loot tables to include high-level combat items.
- KeyItem now takes a default durability value in the constructor.

## [3.8.1] - 2024-02-15

### Changed

- Fixed the inventory size of chests display in inventory.
- Increased the success probability of Pilferers and Thiefs Locks Picks to 48/60 respectively.
- Fixed the IChestGenerator.buildLootTableList not using the correct key when searching for injectable loot tables.
- Fixed Legendary and Mythical loot tables to include respective weapons.
- Fixed Cauldron Chest not displaying a rarity.
- Update mimics to have a smoother chomp animation.

### Added

- Config options for Pilferers and Thiefs Lock Picks success probabilities for different rarities.
- Patchouli book support with entries for Chests, Keys, Locks and Key Ring. Added to your inventory on first join.

## [3.7.1] - 2024-01-25

### Changed

- Fixed key usage/damage not updating client when playing on dedicated server.
- Key usage/damage isn't increment in Creative mode.

## [3.7.0] - 2024-01-17

### Added

- Cardboard Box mimic.

## [3.6.2] - 2023-10-23

### Changed

- Properly referenced the treasure2-structures-1.93.3-v2.toml resource which caused crashes if not found.

## [3.6.1] - 2023-10-16

### Changed

- Fixed crash when removing item from crafting table (anything requiring the Treasure Tool.)
- Data generated all recipes.

## [3.6.0] - 2023-10-16

### Changed

- Fixed generation crashes.
- Fixed structure alignments.
- Fixed treasure tab name display.
- Updated SurfaceStructureFeatureGenerator to return an Optional.
- Updated treasure2-structures-1.20.1 to version 2.
- Updated sunken-ship structures.

### Added

- Blackrock and Deepslate variations of towers.
- Deferred Random Vanilla Spawner generation classes.
- Deferred Wither Tree generation classes. 
- GeometryUtil class.

## [3.5.0] - 2023-09-05

### Changed

- Fixed built-in structure registration.
- Update structure datapack registering to handle multiple datapacks (see loot tables)
- Update when selecting loot table/structure list based on category/type to merge built-in and datapacks, replacing built-ins with datapack elements when overlapped.


## [3.4.0] - 2023-09-03

### Changed
- Fixed ChestRegistry registering chests by Rarity.
- Added Cauldron Chest to chest rarity tag.
- Fixed Emerald Key registration in CommonSetup.
- Changed Lock Item rarity tooltip color to BLUE to be the same as Key Item.
- Changed Treasure Chest Block Item rarity tooltip color to BLUE to be the same as Key & Lock Item.

## [3.3.0] - 2023-07-26

### Changed

- Updated IChestGeneator.buildLootTableList() to use the ILootTableType param instead of defaulting to CHESTS.
- Updated IChestGenerator.fillChest() to only use the rarity that was selected for the chest for the injects.

## [3.2.0] - 2023-07-16

### Added 

-Added internationalization language entries for Rarities and Key/Lock Categories
- Added name for Wither Chest top block.
-Added Callandor sword.

### Changed


## [3.1.2] - 2023-07-13

### Changed

- Updated chest block entities to prevent piping items out/in of locked chests.
- Updated ITEM_HANDLER capability - replaced old deprecated versions with new ones.
- Updated AbstractTreasureChestBlockEntity to use the sided call to CapabilityProvider#getCapability(). This fixes the Jade crash issue.

## [3.1.1] - 2023-07-11

### Changed

- Added names for all mobs.
- Fixed polished granite gravestone recipes.

## [3.1.0] - 2023-07-06

### Added

- Added Crate and Moldy Crate mimics.
- Added 1.19.2 blocks and items to loot tables.

### Changed

- Made 1.18.2 -> 1.19.2 porting code changes (ex . Random -> RandomSource).
- Updated the topaz key texture to be more like the onyx key.
- Key durability is no longer setup during item registration but in common setup using Reflection.
- Updated chest blocks to be waterloggable.


## [3.0.0] - 2023-06-24

- Initial release for mc1.18.2 port.

### Added

- Add topaz and onyx keys and locks.
- Added a series of weapons (short swords, swords, axes, maces, staff), general and collectable, to populate treasure chests in lieu of charms.
- Re-added the eye-patch (removed in mc1.16.5).
- Re-added the Wood Chest and Pirate Chest Mimics (removed in mc1.16.5)
- Added Cauldron Chest and Viking Chest Mimics.
- Added chest effects (particles and light source) for 'undiscovered' chests. The effects are removed when the chest is 'discovered' - by attempting to unlock, open or breaking the chest.
- Added custom container screen for some of the chests.
- Added new marker structure.

### Changed

- Totally refactored the mod with a set of registries to enable easy mod extensibility and integration.
- Refactored configs. Externalized chest config to a separate, non-Forge managed, toml file.
- Removed all manifest files for data files (meta, structure, loot tables).
- All data files (meta, structure, loot tables) are not longer exposed to users' system by default.
- Added datapack support. ie. loot tables and structure nbts in a datapack will be registered by Treasure2.

### Removed

- Removed all Charms and charm-related content. They are being moved to a new stand-alone mod called 'Magic Treasures', which will have built-in integration with Treasure2.