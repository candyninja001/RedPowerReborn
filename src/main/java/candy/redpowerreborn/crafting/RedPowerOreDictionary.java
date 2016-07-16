package candy.redpowerreborn.crafting;

import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.item.RedPowerItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class RedPowerOreDictionary {
	public static void init(FMLInitializationEvent event) {
		OreDictionary.registerOre("oreRuby", RedPowerBlocks.RUBY_ORE);
		OreDictionary.registerOre("oreEmerald", RedPowerBlocks.EMERALD_ORE);
		OreDictionary.registerOre("oreSapphire", RedPowerBlocks.SAPPHIRE_ORE);
		OreDictionary.registerOre("blockMarble", RedPowerBlocks.MARBLE);
		OreDictionary.registerOre("stoneMarble", RedPowerBlocks.MARBLE);
		OreDictionary.registerOre("blockBasalt", RedPowerBlocks.BASALT);
		OreDictionary.registerOre("stoneBasalt", RedPowerBlocks.BASALT);
		OreDictionary.registerOre("blockRuby", RedPowerBlocks.RUBY_BLOCK);
		OreDictionary.registerOre("blockEmerald", RedPowerBlocks.EMERALD_BLOCK);
		OreDictionary.registerOre("blockSapphire", RedPowerBlocks.SAPPHIRE_BLOCK);
		OreDictionary.registerOre("blockSilver", RedPowerBlocks.SILVER_BLOCK);
		OreDictionary.registerOre("blockTin", RedPowerBlocks.TIN_BLOCK);
		OreDictionary.registerOre("blockCopper", RedPowerBlocks.COPPER_BLOCK);
		OreDictionary.registerOre("ingotRedAlloy", RedPowerItems.RED_ALLOY_INGOT);
		OreDictionary.registerOre("ingotBlueAlloy", RedPowerItems.BLUE_ALLOY_INGOT);
		OreDictionary.registerOre("ingotBrass", RedPowerItems.BRASS_INGOT);
		OreDictionary.registerOre("wireCopper", RedPowerItems.COPPER_WIRE);
		OreDictionary.registerOre("wireIron", RedPowerItems.IRON_WIRE);
		OreDictionary.registerOre("dye", RedPowerItems.INDIGO_DYE);
		OreDictionary.registerOre("dyeBlue", RedPowerItems.INDIGO_DYE);
		OreDictionary.registerOre("nuggetIron", RedPowerItems.IRON_NUGGET);
		OreDictionary.registerOre("nuggetSilver", RedPowerItems.SILVER_NUGGET);
		OreDictionary.registerOre("nuggetTin", RedPowerItems.TIN_NUGGET);
		OreDictionary.registerOre("nuggetCopper", RedPowerItems.COPPER_NUGGET);
		OreDictionary.registerOre("gemRuby", RedPowerItems.RUBY);
		OreDictionary.registerOre("gemEmerald", RedPowerItems.EMERALD);
		OreDictionary.registerOre("gemGreenSapphire", RedPowerItems.EMERALD);
		OreDictionary.registerOre("ingotSilver", RedPowerItems.SILVER_INGOT);
		OreDictionary.registerOre("ingotTin", RedPowerItems.TIN_INGOT);
		OreDictionary.registerOre("ingotCopper", RedPowerItems.COPPER_INGOT);
		OreDictionary.registerOre("dustNikolite", RedPowerItems.NIKOLITE);
		OreDictionary.registerOre("gearBrass", RedPowerItems.BRASS_GEAR);
	}
}
