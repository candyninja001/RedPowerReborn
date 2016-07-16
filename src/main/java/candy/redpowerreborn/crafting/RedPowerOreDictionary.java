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
		OreDictionary.registerOre("ingotBlueAlloy", RedPowerItems.BLUE_ALLOY_INGOT);
		OreDictionary.registerOre("ingotBrass", RedPowerItems.BRASS_INGOT);
		OreDictionary.registerOre("wireCopper", RedPowerItems.COPPER_WIRE);
		OreDictionary.registerOre("wireIron", RedPowerItems.IRON_WIRE);
		OreDictionary.registerOre("dye", RedPowerItems.INDIGO_DYE);
		OreDictionary.registerOre("dyeBlue", RedPowerItems.INDIGO_DYE);
		OreDictionary.registerOre("nuggetIron", RedPowerItems.IRON_NUGGET);
		OreDictionary.registerOre("nuggetSilver", RedPowerItems.silver_nugget);
		OreDictionary.registerOre("nuggetTin", RedPowerItems.tin_nugget);
		OreDictionary.registerOre("nuggetCopper", RedPowerItems.COPPER_NUGGET);
		OreDictionary.registerOre("gemRuby", RedPowerItems.ruby);
		OreDictionary.registerOre("gemEmerald", RedPowerItems.EMERALD);
		OreDictionary.registerOre("gemGreenSapphire", RedPowerItems.EMERALD);
		OreDictionary.registerOre("ingotSilver", RedPowerItems.silver_ingot);
		OreDictionary.registerOre("ingotTin", RedPowerItems.tin_ingot);
		OreDictionary.registerOre("ingotCopper", RedPowerItems.COPPER_INGOT);
		OreDictionary.registerOre("dustNikolite", RedPowerItems.NIKOLITE);
		OreDictionary.registerOre("gearBrass", RedPowerItems.BRASS_GEAR);
	}
}
