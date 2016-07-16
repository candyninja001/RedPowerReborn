package candy.redpowerreborn.crafting;

import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.color.ColorHelper;
import candy.redpowerreborn.item.ItemCanvasBag;
import candy.redpowerreborn.item.RedPowerItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RedPowerCrafting {
	
	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerFuelHandler(new FuelHandler());
		// TODO fix all recipes
		//TODO add alloy furnace recipes 
		
		// canvas bag recipes Ends at line 77
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RedPowerItems.INDIGO_DYE, 2), new Object[] { RedPowerBlocks.INDIGO_FLOWER }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerItems.PLAN, new Object[] { RedPowerItems.INDIGO_DYE, Items.PAPER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerItems.CANVAS, true, new Object[] { "sss", "sSs", "sss", 's', "string", 'S', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerItems.CANVAS_BAG, true, new Object[] { "ccc", "c c", "ccc", 'c', RedPowerItems.CANVAS }));
		NBTTagCompound tag;
		NBTTagCompound tag2;
		ItemStack stack;
		ItemStack stack2;
		for (int i = 0; i < 16; i++) {
			tag = new NBTTagCompound();
			tag.setInteger("Color", i);
			stack = new ItemStack(RedPowerItems.CANVAS_BAG);
			stack.setTagCompound(tag);
			if (i != 0)
				GameRegistry.addRecipe(new ShapedOreRecipe(stack, true, new Object[] { "ccc", "cdc", "ccc", 'c', RedPowerItems.CANVAS, 'd', ColorHelper.getOreDictNameFromEnumDyeColor(EnumDyeColor.byMetadata(i)) }));
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RedPowerItems.CANVAS_BAG), new Object[] { RedPowerItems.CANVAS_BAG, "dye" }){
			@Override
		    public ItemStack getCraftingResult(InventoryCrafting var1){
		    	//return output.copy(); 
		    	int color = 0;
		    	for (int i = 0; i < var1.getSizeInventory(); i++){
		    		if(var1.getStackInSlot(i) != null){
		    			int[] ids = OreDictionary.getOreIDs(var1.getStackInSlot(i));
		    			for (int id : ids) {
		    				if (OreDictionary.getOreName(id).startsWith("dye") && OreDictionary.getOreName(id) != "dye"){
		    					String s = OreDictionary.getOreName(id);
		    					color = ColorHelper.getColorFromOreDictName(s).getMetadata();
		    				}
		    			}
		    		}
		    	}
		    	
		    	ItemStack bag;
		    	for (int i = 0; i < var1.getSizeInventory(); i++){
		    		if(var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemCanvasBag){
		    			bag = var1.getStackInSlot(i).copy();
		    			if(color == ItemCanvasBag.getColorFromStack(bag).getMetadata())
		    				return null;
		    			if (!bag.hasTagCompound()){
		    				NBTTagCompound tag = new NBTTagCompound();
		    				tag.setInteger("Color", color);
		    				bag.setTagCompound(tag);
		    				return bag;
		    			}else{
		    				bag.getTagCompound().setInteger("Color", color);
		    				return bag;
		    			}
		    		}
		    	}
		    	return null;
		    }
		});
		// end of canvas bag recipes
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.STICK, 8), new ItemStack(RedPowerBlocks.RUBBER_LOG));
		GameRegistry.addSmelting(new ItemStack(RedPowerBlocks.RUBBER_LOG), new ItemStack(Items.COAL), 0.15F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.INDIGO_DYE, 2), new ItemStack(RedPowerBlocks.INDIGO_FLOWER));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.MARBLE_BRICK, 4), "mm", "mm", 'm', new ItemStack(RedPowerBlocks.MARBLE));
		
		GameRegistry.addSmelting(new ItemStack(RedPowerBlocks.BASALT_COBBLESTONE), new ItemStack(RedPowerBlocks.BASALT), 0.1F);
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.BASALT_BRICK, 4), "bb", "bb", 'b', new ItemStack(RedPowerBlocks.BASALT));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerBlocks.BASALT_PAVER), new ItemStack(RedPowerBlocks.BASALT));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.BASALT_BRICK_CHISELED, 4), "bb", "bb", 'b', new ItemStack(RedPowerBlocks.BASALT_BRICK));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.RUBY_BLOCK), new Object[] { "rrr", "rrr", "rrr", 'r', new ItemStack(RedPowerItems.RUBY) });
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.RUBY, 9), new ItemStack(RedPowerBlocks.RUBY_BLOCK));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.EMERALD_BLOCK), "eee", "eee", "eee", 'e', new ItemStack(RedPowerItems.EMERALD));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.EMERALD, 9), new ItemStack(RedPowerBlocks.EMERALD_BLOCK));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.SAPPHIRE_BLOCK), "sss", "sss", "sss", 's', new ItemStack(RedPowerItems.SAPPHIRE));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.SAPPHIRE, 9), new ItemStack(RedPowerBlocks.SAPPHIRE_BLOCK));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.SILVER_BLOCK), "sss", "sss", "sss", 's', new ItemStack(RedPowerItems.SILVER_INGOT));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.SILVER_INGOT, 9), new ItemStack(RedPowerBlocks.SILVER_BLOCK));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.TIN_BLOCK), "ttt", "ttt", "ttt", 't', new ItemStack(RedPowerItems.TIN_INGOT));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.TIN_INGOT, 9), new ItemStack(RedPowerBlocks.TIN_BLOCK));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.COPPER_BLOCK), "ccc", "ccc", "ccc", 'c', new ItemStack(RedPowerItems.COPPER_INGOT));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.COPPER_INGOT, 9), new ItemStack(RedPowerBlocks.COPPER_BLOCK));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.ALLOY_FURNACE), "bbb", "b b", "bbb", 'b', new ItemStack(Blocks.BRICK_BLOCK));
		// GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.blockBlulectricAlloyFurnace), "ccc", "c c", "ibi", 'c', new ItemStack(Blocks.clay), 'i', new ItemStack(Items.iron_ingot), 'b', new ItemStack(RedPowerItems.itemBlueAlloyIngot));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.COPPER_BLOCK), "ccc", "ccc", "ccc", 'c', new ItemStack(RedPowerItems.COPPER_INGOT));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.BUFFER, true, new Object[] { "iwi", "w w", "iwi", 'i', Blocks.IRON_BARS, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.PROJECT_TABLE, true, new Object[] { "sss", "wtw", "wcw", 's', Blocks.STONE, 'w', "plankWood", 't', Blocks.CRAFTING_TABLE, 'c', Blocks.CHEST }));
		// GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.blockBlulectricAlloyFurnace), "bbb", "b b", "iBi", 'b', new ItemStack(Blocks.brick_block), 'i', new ItemStack(Items.iron_ingot), 'B', new ItemStack(RedPowerItems.itemBlueAlloyIngot));
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockChargingBench, true, new Object[]{"oco", "bCb", "wBw", 'o', Blocks.obsidian, 'c', RedPowerItems.itemCopperCoil, 'b', RedPowerItems.itemBattery, 'C', Blocks.chest, 'w', "plankWood", 'B', RedPowerItems.itemBlueAlloyIngot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.FLUID_PIPE, 16), true, new Object[] { "b b", "bgb", "b b", 'b', "ingotBrass", 'g', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.PNEUMATIC_TUBE, 8), true, new Object[] { "bgb", 'b', "ingotBrass", 'g', "blockGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerBlocks.REDSTONE_TUBE, Items.REDSTONE, RedPowerBlocks.PNEUMATIC_TUBE));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerBlocks.RESTRICTION_TUBE, new Object[] { Items.IRON_INGOT, RedPowerBlocks.PNEUMATIC_TUBE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.MAGTUBE, 8), true, new Object[] { "ccc", "ogo", "ccc", 'c', "wireCopper", 'o', Blocks.OBSIDIAN, 'g', "blockGlass" }));
		
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockSupportFrame, true, new Object[]{"sss","sbs","sss", 's', "stickWood", 'b', "ingotBrass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.DEPLOYER, true, new Object[] { "cCc", "cpc", "crc", 'c', "cobblestone", 'C', Blocks.CHEST, 'p', Blocks.PISTON, 'r', Items.REDSTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.BLOCK_BREAKER, true, new Object[] { "cpc", "cPc", "crc", 'c', "cobblestone", 'p', Items.IRON_PICKAXE, 'P', Blocks.PISTON, 'r', Items.REDSTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.TRANSPOSER, true, new Object[] { "ccc", "wpw", "crc", 'c', "cobblestone", 'w', "plankWood", 'p', Blocks.PISTON, 'r', Items.REDSTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.FILTER, true, new Object[] { "ccc", "gpg", "crc", 'c', "cobblestone", 'g', Items.GOLD_INGOT, 'p', Blocks.PISTON, 'r', RedPowerItems.RED_DOPED_WAFER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.ITEM_DETECTOR, true, new Object[] { "bpb", "rPr", "wpw", 'b', "ingotBrass", 'p', RedPowerBlocks.PNEUMATIC_TUBE, 'r', RedPowerItems.RED_DOPED_WAFER, 'P', Blocks.WOODEN_PRESSURE_PLATE, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.DEPLOYER, true, new Object[] { "iii", "rfr", "ibi", 'i', Items.IRON_INGOT, 'r', RedPowerItems.RED_DOPED_WAFER, 'f', RedPowerBlocks.FILTER, 'b', "ingotBlueAlloy" }));
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockBatteryBox, true, new Object[]{"bwb","bib","iBi", 'b', RedPowerItems.itemBattery, 'w', "plankWood", 'i', Items.iron_ingot, 'B', "ingotBlueAlloy"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.RETRIEVER, true, new Object[] { "blb", "efe", "iBi", 'b', "ingotBrass", 'l', Items.LEATHER, 'e', Items.ENDER_PEARL, 'f', RedPowerBlocks.FILTER, 'i', Items.IRON_INGOT, 'B', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.REGULATOR, true, new Object[] { "bBb", "rir", "wBw", 'b', "ingotBrass", 'B', RedPowerBlocks.BUFFER, 'r', RedPowerItems.RED_DOPED_WAFER, 'i', RedPowerBlocks.ITEM_DETECTOR, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.IGNITER, true, new Object[] { "nfn", "cdc", "crc", 'n', Blocks.NETHERRACK, 'f', Items.FLINT_AND_STEEL, 'c', "cobblestone", 'd', RedPowerBlocks.DEPLOYER, 'r', Items.REDSTONE }));
		
		// TODO 'c' is supposed to be bundle cable which I don't plan on porting
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.ASSEMBLER, true, new Object[] { "bib", "cdc", "iri", 'b', "ingotBrass", 'i', Items.IRON_INGOT, 'c', Blocks.OBSIDIAN, 'd', RedPowerBlocks.DEPLOYER, 'r', RedPowerItems.RED_DOPED_WAFER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.EJECTOR, true, new Object[] { "wbw", "wtw", "crc", 'w', "plankWood", 'b', RedPowerBlocks.BUFFER, 't', RedPowerBlocks.TRANSPOSER, 'c', "cobblestone", 'r', Items.REDSTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.RELAY, true, new Object[] { "wbw", "wtw", "crc", 'w', "plankWood", 'b', RedPowerBlocks.BUFFER, 't', RedPowerBlocks.TRANSPOSER, 'c', "cobblestone", 'r', RedPowerItems.RED_DOPED_WAFER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.MANAGER, true, new Object[] { "iri", "RsR", "wbw", 'i', Items.IRON_INGOT, 'r', RedPowerBlocks.REGULATOR, 'R', RedPowerItems.RED_DOPED_WAFER, 's', RedPowerBlocks.SORTING_MACHINE, 'w', "plankWood", 'b', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.ASSEMBLER, true, new Object[] { "iii", "pmp", "ibi", 'i', Items.IRON_INGOT, 'p', RedPowerBlocks.FLUID_PIPE, 'm', RedPowerItems.BLULECTRIC_MOTOR, 'b', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.PUMP, true, new Object[] { "bob", "o o", "bob", 'b', "ingotBlueAlloy", 'o', Blocks.OBSIDIAN }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.GRATE, true, new Object[] { "bbb", "b b", "bpb", 'b', Blocks.IRON_BARS, 'p', RedPowerBlocks.FLUID_PIPE }));
		
		// TODO add item recipes
	}
}
