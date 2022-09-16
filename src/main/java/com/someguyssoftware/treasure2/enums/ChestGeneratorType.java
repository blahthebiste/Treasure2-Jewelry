/**
 * 
 */
package com.someguyssoftware.treasure2.enums;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.someguyssoftware.treasure2.generator.chest.CauldronChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.CommonChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.CrystalSkullChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.EpicChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.GoldSkullChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.IChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.LegendaryChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.MythicalChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.RareChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.ScarceChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.SkullChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.UncommonChestGenerator;
import com.someguyssoftware.treasure2.generator.chest.WitherChestGenerator;

/**
 * @author Mark Gottschling on Sep 4, 2020
 *
 */
public enum ChestGeneratorType {
	COMMON(CommonChestGenerator::new),
	UNCOMMON(UncommonChestGenerator::new),
	SCARCE(ScarceChestGenerator::new),
	RARE(RareChestGenerator::new),
	EPIC(EpicChestGenerator::new),
	LEGENDARY(LegendaryChestGenerator::new),
	MYTHICAL(MythicalChestGenerator::new),
	WITHER(WitherChestGenerator::new),
	SKULL(SkullChestGenerator::new),
	GOLD_SKULL(GoldSkullChestGenerator::new),
	CRYSTAL_SKULL(CrystalSkullChestGenerator::new),
	CAULDRON(CauldronChestGenerator::new);

	private Supplier<IChestGenerator> factory;

	/**
	 * 
	 */
	ChestGeneratorType(Supplier<IChestGenerator> factory) {
		this.factory = factory;
	}

	/**
	 * 
	 */
	public IChestGenerator getChestGenerator() {
		return factory.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> getNames() {
		List<String> names = EnumSet.allOf(ChestGeneratorType.class).stream().map(x -> x.name()).collect(Collectors.toList());
		return names;
	}
}