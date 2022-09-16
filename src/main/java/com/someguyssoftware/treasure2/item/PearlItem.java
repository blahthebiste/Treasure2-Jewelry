/**
 * 
 */
package com.someguyssoftware.treasure2.item;

import static com.someguyssoftware.treasure2.Treasure.LOGGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.someguyssoftware.gottschcore.block.BlockContext;
import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.gottschcore.loot.LootPoolShell;
import com.someguyssoftware.gottschcore.loot.LootTableShell;
import com.someguyssoftware.gottschcore.random.RandomHelper;
import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.block.IWishingWellBlock;
import com.someguyssoftware.treasure2.enums.Pearls;
import com.someguyssoftware.treasure2.loot.TreasureLootTableMaster2.SpecialLootTables;
import com.someguyssoftware.treasure2.loot.TreasureLootTableRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * 
 * @author Mark Gottschling on Aug 18, 2019
 *
 */
public class PearlItem extends ModItem /*implements IWishable, IPouchable*/ {

	public static final int MAX_STACK_SIZE = 8;
	// TODO move to IWishable when added
	public static final String DROPPED_BY_KEY = "droppedBy";
		
	private Pearls pearl;
	
	/**
	 * 
	 */
	@Deprecated
	public PearlItem (String modID, String name, Item.Properties properties)	 {
		super(modID, name, properties.tab(TreasureItemGroups.TREASURE_ITEM_GROUP)
				.stacksTo(MAX_STACK_SIZE));
		this.pearl = Pearls.WHITE;
	}
	
	/**
	 * 
	 */
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);     	
		// TODO change label to tooltip.label.wishable
		tooltip.add(new TranslationTextComponent("tooltip.label.coin").withStyle(TextFormatting.GOLD, TextFormatting.ITALIC));
	}
	
	/**
	 * 
	 */
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
		// get the item stack or number of items.
		ItemStack entityItemStack = entityItem.getItem();
		
		World world = entityItem.level;
		if (WorldInfo.isClientSide(world)) {
			return super.onEntityItemUpdate(stack, entityItem);
		}
		
		// get the position
		ICoords coords = new Coords(entityItem.blockPosition());
		BlockContext blockContext = new BlockContext(world, coords);
		int numWishingWellBlocks = 0;
		// check if in water
		if (blockContext.equalsBlock(Blocks.WATER)) {
			// check if the water block is adjacent to 2 wishing well blocks
			ICoords checkCoords = coords.add(-1, 0, -1);
			for (int z = 0; z < 3; z++) {
				for (int x = 0; x < 3; x++) {
					BlockContext checkBlockContext = new BlockContext(world, checkCoords);
					if (checkBlockContext.toBlock() instanceof IWishingWellBlock) {
						numWishingWellBlocks++;
					}					
					if (numWishingWellBlocks >= 2) {
						break;
					}
				}
			}

			if (numWishingWellBlocks >=2) {
				Random random = new Random();
				for (int itemIndex = 0; itemIndex < entityItemStack.getCount(); itemIndex++) {
					// generate an item for each item in the stack
					generateLootItem(world, random, entityItem, coords);
				}
				return true;
			}
		}
		
		return super.onEntityItemUpdate(stack, entityItem);
	}
	
	/**
	 * 
	 * @param world
	 * @param random
	 * @param entityItem
	 * @param coords
	 */
	private void generateLootItem(World world, Random random, ItemEntity entityItem, ICoords coords) {
		List<LootTableShell> lootTables = new ArrayList<>();
		
		// determine pearl type
		if (getPearl() == Pearls.WHITE) {
			lootTables.add(TreasureLootTableRegistry.getLootTableMaster().getSpecialLootTable(SpecialLootTables.WHITE_PEARL_WELL));
		}
		else if (getPearl() == Pearls.BLACK) {
			lootTables.add(TreasureLootTableRegistry.getLootTableMaster().getSpecialLootTable(SpecialLootTables.BLACK_PEARL_WELL));
		}
		
		ItemStack stack = null;
		// handle if loot tables is null or size = 0. return an item (apple) to ensure continuing functionality
		if (lootTables == null || lootTables.size() == 0) {
			stack = new ItemStack(Items.DIAMOND);
		}
		else {
			// attempt to get the player who dropped the coin
			ItemStack coinItem = entityItem.getItem();
			CompoundNBT nbt = coinItem.getTag();
			LOGGER.debug("item as a tag");
			PlayerEntity player = null;
			if (nbt != null && nbt.contains(DROPPED_BY_KEY)) {
				// TODO change to check by UUID
				for (PlayerEntity p : world.players()) {
					if (p.getName().getString().equalsIgnoreCase(nbt.getString(DROPPED_BY_KEY))) {
						player = p;
					}
				}
				if (player != null && LOGGER.isDebugEnabled()) {
					LOGGER.debug("coin dropped by player -> {}", player.getName());
				}
				else {
					LOGGER.debug("can't find player!");
				}
			}
			LOGGER.debug("player -> {}", player.getName().getString());
			// select a table
			LootTableShell tableShell = lootTables.get(RandomHelper.randomInt(random, 0, lootTables.size()-1));
			LOGGER.debug("pearl: tableShell -> {}", tableShell.toString());
			if (tableShell.getResourceLocation() == null) {
				return;
			}
			
			// get the vanilla table from shell
			LootTable table = world.getServer().getLootTables().get(tableShell.getResourceLocation());
			// get a list of loot pools
			List<LootPoolShell> lootPoolShells = tableShell.getPools();
			
			// generate a context
			LootContext lootContext = new LootContext.Builder((ServerWorld) world)
					.withLuck((player != null) ? player.getLuck() : 0)
					.withParameter(LootParameters.THIS_ENTITY, player)
					.withParameter(LootParameters.ORIGIN, coords.toVec3d()).create(LootParameterSets.CHEST);

			List<ItemStack> itemStacks = new ArrayList<>();
			for (LootPoolShell pool : lootPoolShells) {
				LOGGER.debug("pearl: processing pool -> {}", pool.getName());
				// go get the vanilla managed pool
				LootPool lootPool = table.getPool(pool.getName());
				
				// geneate loot from pools
				lootPool.addRandomItems(itemStacks::add, lootContext);
			}
			
			// get effective rarity
//			Rarity effectiveRarity = TreasureLootTableRegistry.getLootTableMaster().getEffectiveRarity(tableShell, (getPearl() == Pearls.WHITE) ? Rarity.UNCOMMON : Rarity.SCARCE);	
//			LOGGER.debug("pearl: using effective rarity -> {}", effectiveRarity);
//			
//			// get all injected loot tables
//			LOGGER.debug("pearl: searching for injectable tables for category ->{}, rarity -> {}", tableShell.getCategory(), effectiveRarity);
//			Optional<List<LootTableShell>> injectLootTableShells = buildInjectedLootTableList(tableShell.getCategory(), effectiveRarity);			
//			if (injectLootTableShells.isPresent()) {
//				LOGGER.debug("pearl: found injectable tables for category ->{}, rarity -> {}", tableShell.getCategory(), effectiveRarity);
//				LOGGER.debug("pearl: size of injectable tables -> {}", injectLootTableShells.get().size());
//
//				// attempt to get the player who dropped the coin
//				ItemStack coinItem = entityItem.getItem();
//				CompoundNBT nbt = coinItem.getTagCompound();
//				PlayerEntity player = null;
//				if (nbt != null && nbt.hasKey(DROPPED_BY_KEY)) {					
//					player = world.getPlayerEntityByName(nbt.getString(DROPPED_BY_KEY));
//					if (player != null && LOGGER.isDebugEnabled()) {
//						LOGGER.debug("pearl dropped by player -> {}", player.getName());
//					}
//				}
//				itemStacks.addAll(getLootItems(world, random, injectLootTableShells.get(), getLootContext(world, player)));
//		}
			
			// select one item randomly
			stack = itemStacks.get(RandomHelper.randomInt(0, itemStacks.size()-1));

		}				
		
		// spawn the item 
		if (stack != null) {
			InventoryHelper.dropItemStack(world, (double)coords.getX(), (double)coords.getY()+1, (double)coords.getZ(), stack);
		}

		// remove the item entity
		entityItem.remove();
	}
	
	/**
	 * @return the pearl
	 */
	public Pearls getPearl() {
		return pearl;
	}
	/**
	 * @param pearl the pearl to set
	 */
	public PearlItem setPearl(Pearls pearl) {
		this.pearl = pearl;
		return this;
	}
}