package mod.gottsch.forge.treasure2.core.registry;

import java.util.Objects;

import com.someguyssoftware.gottschcore.mod.IMod;
import com.someguyssoftware.gottschcore.world.gen.structure.IDecayRuleSet;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.world.gen.structure.DecayResources;
import mod.gottsch.forge.treasure2.core.world.gen.structure.TreasureDecayManager;

public class TreasureDecayRegistry implements ITreasureResourceRegistry {
	private static final String DEFAULT_RESOURCES_LIST_PATH = "decay/default_ruleset_list.json";	
	
	private static TreasureDecayManager decayManager;
	private static DecayResources decayResources;
	
	static {
		// load master loot resources lists
		try {
			decayResources = ITreasureResourceRegistry.<DecayResources>readResourcesFromFromStream(
					Objects.requireNonNull(Treasure.instance.getClass().getClassLoader().getResourceAsStream(DEFAULT_RESOURCES_LIST_PATH)), DecayResources.class);
		}
		catch(Exception e) {
			Treasure.LOGGER.warn("Unable to expose loot tables");
		}
	}
	
	/**
	 * 
	 */
	private TreasureDecayRegistry() {}

	/**
	 * 
	 * @param mod
	 */
	public synchronized static void create(IMod mod) {
		if (decayManager  == null) {
			decayManager = new TreasureDecayManager(mod, "mc1_16/decay");
		}
	}
	
	/**
	 * 
	 */
	public static void clear() {
		decayManager.clear();
	}
	
	/**
	 * Wrapper method
	 * @param modID
	 */
	public static void register(final String modID) {
		buildAndExpose(modID);
		decayManager.register(modID, decayResources.getResources());
	}
	
	/**
	 * 
	 * @param modID
	 */
	private static void buildAndExpose(String modID) {
		decayManager.buildAndExpose("data", modID, "mc1_16", "decay", decayResources.getResources());		
	}
	
	/**
	 * Convenience method.
	 * @param key
	 * @return
	 */
	public static IDecayRuleSet get(String key) {
		return decayManager.get(key);
	}

	public static TreasureDecayManager getDecayManager() {
		return decayManager;
	}
}
