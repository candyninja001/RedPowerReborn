package candy.redpowerreborn.crafting;

import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.item.RedPowerItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class RedPowerOreDictionary {
	public static void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("oreRuby", RedPowerBlocks.ruby_ore);
		OreDictionary.registerOre("oreEmerald", RedPowerBlocks.emerald_ore);
		OreDictionary.registerOre("oreSapphire", RedPowerBlocks.sapphire_ore);
		OreDictionary.registerOre("blockMarble", RedPowerBlocks.marble);
		OreDictionary.registerOre("stoneMarble", RedPowerBlocks.marble);
		OreDictionary.registerOre("blockBasalt", RedPowerBlocks.basalt);
		OreDictionary.registerOre("stoneBasalt", RedPowerBlocks.basalt);
		OreDictionary.registerOre("blockRuby", RedPowerBlocks.ruby_block);
		OreDictionary.registerOre("blockEmerald", RedPowerBlocks.emerald_block);
		OreDictionary.registerOre("blockSapphire", RedPowerBlocks.sapphire_block);
		OreDictionary.registerOre("blockSilver", RedPowerBlocks.silver_block);
		OreDictionary.registerOre("blockTin", RedPowerBlocks.tin_block);
		OreDictionary.registerOre("blockCopper", RedPowerBlocks.copper_block);
		OreDictionary.registerOre("ingotRedAlloy", RedPowerItems.red_alloy_ingot);
		OreDictionary.registerOre("ingotBlueAlloy", RedPowerItems.blue_alloy_ingot);
		OreDictionary.registerOre("ingotBrass", RedPowerItems.brass_ingot);
		OreDictionary.registerOre("wireCopper", RedPowerItems.copper_wire);
		OreDictionary.registerOre("wireIron", RedPowerItems.iron_wire);
		OreDictionary.registerOre("dye", RedPowerItems.indigo_dye);
		OreDictionary.registerOre("dyeBlue", RedPowerItems.indigo_dye);
		OreDictionary.registerOre("nuggetIron", RedPowerItems.iron_nugget);
		OreDictionary.registerOre("nuggetSilver", RedPowerItems.silver_nugget);
		OreDictionary.registerOre("nuggetTin", RedPowerItems.tin_nugget);
		OreDictionary.registerOre("nuggetCopper", RedPowerItems.copper_nugget);
		OreDictionary.registerOre("gemRuby", RedPowerItems.ruby);
		OreDictionary.registerOre("gemEmerald", RedPowerItems.emerald);
		OreDictionary.registerOre("gemGreenSapphire", RedPowerItems.emerald);
		OreDictionary.registerOre("ingotSilver", RedPowerItems.silver_ingot);
		OreDictionary.registerOre("ingotTin", RedPowerItems.tin_ingot);
		OreDictionary.registerOre("ingotCopper", RedPowerItems.copper_ingot);
		OreDictionary.registerOre("dustNikolite", RedPowerItems.nikolite);
		OreDictionary.registerOre("gearBrass", RedPowerItems.brass_gear);
	}
}
