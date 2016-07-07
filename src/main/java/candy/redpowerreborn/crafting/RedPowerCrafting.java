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
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RedPowerItems.indigo_dye, 2), new Object[] { RedPowerBlocks.indigo_flower }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerItems.plan, new Object[] { RedPowerItems.indigo_dye, Items.paper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerItems.canvas, true, new Object[] { "sss", "sSs", "sss", 's', "string", 'S', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerItems.canvas_bag, true, new Object[] { "ccc", "c c", "ccc", 'c', RedPowerItems.canvas }));
		NBTTagCompound tag;
		NBTTagCompound tag2;
		ItemStack stack;
		ItemStack stack2;
		for (int i = 0; i < 16; i++) {
			tag = new NBTTagCompound();
			tag.setInteger("Color", i);
			stack = new ItemStack(RedPowerItems.canvas_bag);
			stack.setTagCompound(tag);
			if (i != 0)
				GameRegistry.addRecipe(new ShapedOreRecipe(stack, true, new Object[] { "ccc", "cdc", "ccc", 'c', RedPowerItems.canvas, 'd', ColorHelper.getOreDictNameFromEnumDyeColor(EnumDyeColor.byMetadata(i)) }));
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RedPowerItems.canvas_bag), new Object[] { RedPowerItems.canvas_bag, "dye" }){
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
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8), new ItemStack(RedPowerBlocks.rubber_log));
		GameRegistry.addSmelting(new ItemStack(RedPowerBlocks.rubber_log), new ItemStack(Items.coal), 0.15F);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.indigo_dye, 2), new ItemStack(RedPowerBlocks.indigo_flower));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.marble_brick, 4), "mm", "mm", 'm', new ItemStack(RedPowerBlocks.marble));
		
		GameRegistry.addSmelting(new ItemStack(RedPowerBlocks.basalt_cobblestone), new ItemStack(RedPowerBlocks.basalt), 0.1F);
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.basalt_brick, 4), "bb", "bb", 'b', new ItemStack(RedPowerBlocks.basalt));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerBlocks.basalt_paver), new ItemStack(RedPowerBlocks.basalt));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.basalt_brick_chiseled, 4), "bb", "bb", 'b', new ItemStack(RedPowerBlocks.basalt_brick));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.ruby_block), new Object[] { "rrr", "rrr", "rrr", 'r', new ItemStack(RedPowerItems.ruby) });
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.ruby, 9), new ItemStack(RedPowerBlocks.ruby_block));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.emerald_block), "eee", "eee", "eee", 'e', new ItemStack(RedPowerItems.emerald));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.emerald, 9), new ItemStack(RedPowerBlocks.emerald_block));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.sapphire_block), "sss", "sss", "sss", 's', new ItemStack(RedPowerItems.sapphire));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.sapphire, 9), new ItemStack(RedPowerBlocks.sapphire_block));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.silver_block), "sss", "sss", "sss", 's', new ItemStack(RedPowerItems.silver_ingot));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.silver_ingot, 9), new ItemStack(RedPowerBlocks.silver_block));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.tin_block), "ttt", "ttt", "ttt", 't', new ItemStack(RedPowerItems.tin_ingot));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.tin_ingot, 9), new ItemStack(RedPowerBlocks.tin_block));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.copper_block), "ccc", "ccc", "ccc", 'c', new ItemStack(RedPowerItems.copper_ingot));
		GameRegistry.addShapelessRecipe(new ItemStack(RedPowerItems.copper_ingot, 9), new ItemStack(RedPowerBlocks.copper_block));
		
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.alloy_furnace), "bbb", "b b", "bbb", 'b', new ItemStack(Blocks.brick_block));
		// GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.blockBlulectricAlloyFurnace), "ccc", "c c", "ibi", 'c', new ItemStack(Blocks.clay), 'i', new ItemStack(Items.iron_ingot), 'b', new ItemStack(RedPowerItems.itemBlueAlloyIngot));
		GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.copper_block), "ccc", "ccc", "ccc", 'c', new ItemStack(RedPowerItems.copper_ingot));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.buffer, true, new Object[] { "iwi", "w w", "iwi", 'i', Blocks.iron_bars, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.project_table, true, new Object[] { "sss", "wtw", "wcw", 's', Blocks.stone, 'w', "plankWood", 't', Blocks.crafting_table, 'c', Blocks.chest }));
		// GameRegistry.addRecipe(new ItemStack(RedPowerBlocks.blockBlulectricAlloyFurnace), "bbb", "b b", "iBi", 'b', new ItemStack(Blocks.brick_block), 'i', new ItemStack(Items.iron_ingot), 'B', new ItemStack(RedPowerItems.itemBlueAlloyIngot));
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockChargingBench, true, new Object[]{"oco", "bCb", "wBw", 'o', Blocks.obsidian, 'c', RedPowerItems.itemCopperCoil, 'b', RedPowerItems.itemBattery, 'C', Blocks.chest, 'w', "plankWood", 'B', RedPowerItems.itemBlueAlloyIngot}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.fluid_pipe, 16), true, new Object[] { "b b", "bgb", "b b", 'b', "ingotBrass", 'g', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.pnuematic_tube, 8), true, new Object[] { "bgb", 'b', "ingotBrass", 'g', "blockGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerBlocks.redstone_tube, Items.redstone, RedPowerBlocks.pnuematic_tube));
		GameRegistry.addRecipe(new ShapelessOreRecipe(RedPowerBlocks.restriction_tube, new Object[] { Items.iron_ingot, RedPowerBlocks.pnuematic_tube }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RedPowerBlocks.magtube, 8), true, new Object[] { "ccc", "ogo", "ccc", 'c', "wireCopper", 'o', Blocks.obsidian, 'g', "blockGlass" }));
		
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockSupportFrame, true, new Object[]{"sss","sbs","sss", 's', "stickWood", 'b', "ingotBrass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.deployer, true, new Object[] { "cCc", "cpc", "crc", 'c', "cobblestone", 'C', Blocks.chest, 'p', Blocks.piston, 'r', Items.redstone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.block_breaker, true, new Object[] { "cpc", "cPc", "crc", 'c', "cobblestone", 'p', Items.iron_pickaxe, 'P', Blocks.piston, 'r', Items.redstone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.transposer, true, new Object[] { "ccc", "wpw", "crc", 'c', "cobblestone", 'w', "plankWood", 'p', Blocks.piston, 'r', Items.redstone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.filter, true, new Object[] { "ccc", "gpg", "crc", 'c', "cobblestone", 'g', Items.gold_ingot, 'p', Blocks.piston, 'r', RedPowerItems.red_doped_wafer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.item_detector, true, new Object[] { "bpb", "rPr", "wpw", 'b', "ingotBrass", 'p', RedPowerBlocks.pnuematic_tube, 'r', RedPowerItems.red_doped_wafer, 'P', Blocks.wooden_pressure_plate, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.deployer, true, new Object[] { "iii", "rfr", "ibi", 'i', Items.iron_ingot, 'r', RedPowerItems.red_doped_wafer, 'f', RedPowerBlocks.filter, 'b', "ingotBlueAlloy" }));
		// GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.blockBatteryBox, true, new Object[]{"bwb","bib","iBi", 'b', RedPowerItems.itemBattery, 'w', "plankWood", 'i', Items.iron_ingot, 'B', "ingotBlueAlloy"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.retriever, true, new Object[] { "blb", "efe", "iBi", 'b', "ingotBrass", 'l', Items.leather, 'e', Items.ender_pearl, 'f', RedPowerBlocks.filter, 'i', Items.iron_ingot, 'B', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.regulator, true, new Object[] { "bBb", "rir", "wBw", 'b', "ingotBrass", 'B', RedPowerBlocks.buffer, 'r', RedPowerItems.red_doped_wafer, 'i', RedPowerBlocks.item_detector, 'w', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.igniter, true, new Object[] { "nfn", "cdc", "crc", 'n', Blocks.netherrack, 'f', Items.flint_and_steel, 'c', "cobblestone", 'd', RedPowerBlocks.deployer, 'r', Items.redstone }));
		
		// TODO 'c' is supposed to be bundle cable which I don't plan on porting
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.assembler, true, new Object[] { "bib", "cdc", "iri", 'b', "ingotBrass", 'i', Items.iron_ingot, 'c', Blocks.obsidian, 'd', RedPowerBlocks.deployer, 'r', RedPowerItems.red_doped_wafer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.ejector, true, new Object[] { "wbw", "wtw", "crc", 'w', "plankWood", 'b', RedPowerBlocks.buffer, 't', RedPowerBlocks.transposer, 'c', "cobblestone", 'r', Items.redstone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.relay, true, new Object[] { "wbw", "wtw", "crc", 'w', "plankWood", 'b', RedPowerBlocks.buffer, 't', RedPowerBlocks.transposer, 'c', "cobblestone", 'r', RedPowerItems.red_doped_wafer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.manager, true, new Object[] { "iri", "RsR", "wbw", 'i', Items.iron_ingot, 'r', RedPowerBlocks.regulator, 'R', RedPowerItems.red_doped_wafer, 's', RedPowerBlocks.sorting_machine, 'w', "plankWood", 'b', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.assembler, true, new Object[] { "iii", "pmp", "ibi", 'i', Items.iron_ingot, 'p', RedPowerBlocks.fluid_pipe, 'm', RedPowerItems.blulectric_motor, 'b', "ingotBlueAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.pump, true, new Object[] { "bob", "o o", "bob", 'b', "ingotBlueAlloy", 'o', Blocks.obsidian }));
		GameRegistry.addRecipe(new ShapedOreRecipe(RedPowerBlocks.grate, true, new Object[] { "bbb", "b b", "bpb", 'b', Blocks.iron_bars, 'p', RedPowerBlocks.fluid_pipe }));
		
		// TODO add item recipes
	}
}
