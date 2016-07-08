package candy.redpowerreborn.block;

import candy.redpowerreborn.RedPower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class RedPowerBlocks {
	public static Block accelerator;
	public static Block alloy_furnace;
	public static Block assembler;
	public static Block basalt;
	public static Block basalt_brick;
	public static Block basalt_brick_chiseled;
	public static Block basalt_cobblestone;
	public static Block basalt_paver;
	public static Block battery_box;
	public static Block block_breaker;
	public static Block blulectric_alloy_furnace;
	public static Block blulectric_furnace;
	public static Block buffer;
	public static Block charging_bench;
	public static Block copper_block;
	public static Block copper_ore;
	public static Block deployer;
	public static Block ejector;
	public static Block emerald_block;
	public static Block emerald_ore;
	public static Block filter;
	public static Block flax;
	public static Block fluid_pipe;
	public static Block frame_motor;
	public static Block grate;
	public static Block igniter;
	public static Block indigo_flower;
	public static Block item_detector;
	public static Block kinetic_generator;
	public static Block magtube;
	public static Block manager;
	public static Block marble;
	public static Block marble_brick;
	public static Block nickolite_ore;
	public static Block pnuematic_tube;
	public static Block project_table;
	public static Block pump;
	public static Block redstone_tube;
	public static Block regulator;
	public static Block relay;
	public static Block restriction_tube;
	public static Block retriever;
	public static Block rubber_leaves;
	public static Block rubber_sapling;
	public static Block rubber_log;
	public static Block ruby_block;
	public static Block ruby_ore;
	public static Block sapphire_block;
	public static Block sapphire_ore;
	public static Block silver_block;
	public static Block silver_ore;
	public static Block solar_panel;
	public static Block sorting_machine;
	public static Block sortron;
	public static Block support_frame;
	public static Block thermopile;
	public static Block tin_block;
	public static Block tin_ore;
	public static Block transposer;
	public static Block tungsten_ore;

	public static void preInit(FMLPreInitializationEvent event) {
		//TODO give all blocks proper hardness values and effectives tools, and generation if applicable
		accelerator = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_accelerator").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(accelerator, "accelerator");
		alloy_furnace = new BlockAlloyFurnace().setUnlocalizedName(RedPower.MODID + "_alloyFurnace").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(alloy_furnace, "alloy_furnace");
		assembler = new BlockAssembler().setUnlocalizedName(RedPower.MODID + "_assembler").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(assembler, "assembler");
		basalt = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_basalt").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(basalt, "basalt");
		basalt_brick = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_basalt.brick").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(basalt_brick, "basalt_brick");
		basalt_brick_chiseled = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_basalt.chiseled").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(basalt_brick_chiseled, "basalt_brick_chiseled");
		basalt_cobblestone = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_basalt.cobblestone").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(basalt_cobblestone, "basalt_cobblestone");
		basalt_paver = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_basalt.paver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(basalt_paver, "basalt_paver");
		battery_box = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_batteryBox").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(battery_box, "battery_box");
		block_breaker = new BlockBlockBreaker().setUnlocalizedName(RedPower.MODID + "_blockBreaker").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(block_breaker, "block_breaker");
		blulectric_alloy_furnace = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blulectricAlloyFurnace").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(blulectric_alloy_furnace, "blulectric_alloy_furnace");
		blulectric_furnace = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blulectricFurnace").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(blulectric_furnace, "blulectric_furnace");
		buffer = new BlockBuffer().setUnlocalizedName(RedPower.MODID + "_buffer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(buffer, "buffer");
		charging_bench = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_chargingBench").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(charging_bench, "charging_bench");
		copper_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(copper_block, "copper_block");
		copper_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(copper_ore, "copper_ore");
		deployer = new BlockDeployer().setUnlocalizedName(RedPower.MODID + "_deployer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(deployer, "deployer");
		ejector = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_ejector").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(ejector, "ejector");
		emerald_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(emerald_block, "emerald_block");
		emerald_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(emerald_ore, "emerald_ore");
		filter = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_filter").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(filter, "filter");
		flax = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_flax").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(flax, "flax");
		fluid_pipe = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_fluidPipe").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(fluid_pipe, "fluid_pipe");
		frame_motor = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_frameMotor").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(frame_motor, "frame_motor");
		grate = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_grate").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(grate, "grate");
		igniter = new BlockIgniter().setUnlocalizedName(RedPower.MODID + "_igniter").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(igniter, "igniter");
		indigo_flower = new BlockIndigoFlower().setUnlocalizedName(RedPower.MODID + "_flower.indigo").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(indigo_flower, "indigo_flower");
		item_detector = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockDetector").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(item_detector, "block_detector");
		kinetic_generator = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_kineticGenerator").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(kinetic_generator, "kinetic_generator");
		magtube = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_magtube").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(magtube, "magtube");
		manager = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_mannager").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(manager, "mannager");
		marble = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_marble").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(marble, "marble");
		marble_brick = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_marble.brick").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(marble_brick, "marble_brick");
		nickolite_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreNickolite").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(nickolite_ore, "nickolite_ore");
		pnuematic_tube = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_pnuematicTube").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(pnuematic_tube, "pnuematic_tube");
		project_table = new BlockProjectTable().setUnlocalizedName(RedPower.MODID + "_projectTable").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(project_table, "project_table");
		pump = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_pump").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(pump, "pump");
		redstone_tube = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_redstoneTube").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(redstone_tube, "redstone_tube");
		regulator = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_regulator").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(regulator, "regulator");
		relay = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_relay").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(relay, "relay");
		restriction_tube = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_restrictionTube").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(restriction_tube, "restriction_tube");
		retriever = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_retriever").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(retriever, "retriever");
		rubber_leaves = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_leaves.rubber").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(rubber_leaves, "rubber_leaves");
		rubber_sapling = new Block(Material.AIR).setUnlocalizedName(RedPower.MODID + "_sapling.rubber").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(rubber_sapling, "rubber_sapling");
		rubber_log = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_log.rubber").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(rubber_log, "rubber_log");
		ruby_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(ruby_block, "ruby_block");
		ruby_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(ruby_ore, "ruby_ore");
		sapphire_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(sapphire_block, "sapphire_block");
		sapphire_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(sapphire_ore, "sapphire_ore");
		silver_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockSilver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(silver_block, "silver_block");
		silver_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreSilver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(silver_ore, "silver_ore");
		solar_panel = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_solarPanel").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(solar_panel, "solar_panel");
		sorting_machine = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_sortingMachine").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(sorting_machine, "sorting_machine");
		sortron = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_sortron").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(sortron, "sortron");
		support_frame = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_supportFrame").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(support_frame, "support_frame");
		thermopile = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_thermopile").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(thermopile, "thermopile");
		tin_block = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_blockTin").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(tin_block, "tin_block");
		tin_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreTin").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(tin_ore, "tin_ore");
		transposer = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_transposer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(transposer, "transposer");
		tungsten_ore = new Block(Material.ROCK).setUnlocalizedName(RedPower.MODID + "_oreTungsten").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerBlock(tungsten_ore, "tungsten_ore");
	}
	
	public static void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(accelerator), 0, new ModelResourceLocation(RedPower.MODID + ":accelerator", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(alloy_furnace), 0, new ModelResourceLocation(RedPower.MODID + ":alloy_furnace", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(assembler), 0, new ModelResourceLocation(RedPower.MODID + ":assembler", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(basalt), 0, new ModelResourceLocation(RedPower.MODID + ":basalt", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(basalt_brick), 0, new ModelResourceLocation(RedPower.MODID + ":basalt_brick", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(basalt_brick_chiseled), 0, new ModelResourceLocation(RedPower.MODID + ":basalt_brick_chiseled", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(basalt_cobblestone), 0, new ModelResourceLocation(RedPower.MODID + ":basalt_cobblestone", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(basalt_paver), 0, new ModelResourceLocation(RedPower.MODID + ":basalt_paver", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(battery_box), 0, new ModelResourceLocation(RedPower.MODID + ":battery_box", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(block_breaker), 0, new ModelResourceLocation(RedPower.MODID + ":block_breaker", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(blulectric_alloy_furnace), 0, new ModelResourceLocation(RedPower.MODID + ":blulectric_alloy_furnace", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(blulectric_furnace), 0, new ModelResourceLocation(RedPower.MODID + ":blulectric_furnace", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(buffer), 0, new ModelResourceLocation(RedPower.MODID + ":buffer", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(charging_bench), 0, new ModelResourceLocation(RedPower.MODID + ":charging_bench", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(copper_block), 0, new ModelResourceLocation(RedPower.MODID + ":copper_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(copper_ore), 0, new ModelResourceLocation(RedPower.MODID + ":copper_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(deployer), 0, new ModelResourceLocation(RedPower.MODID + ":deployer", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(ejector), 0, new ModelResourceLocation(RedPower.MODID + ":ejector", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(emerald_block), 0, new ModelResourceLocation(RedPower.MODID + ":emerald_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(emerald_ore), 0, new ModelResourceLocation(RedPower.MODID + ":emerald_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(filter), 0, new ModelResourceLocation(RedPower.MODID + ":filter", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(flax), 0, new ModelResourceLocation(RedPower.MODID + ":flax", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(fluid_pipe), 0, new ModelResourceLocation(RedPower.MODID + ":fluid_pipe", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(frame_motor), 0, new ModelResourceLocation(RedPower.MODID + ":frame_motor", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(grate), 0, new ModelResourceLocation(RedPower.MODID + ":grate", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(igniter), 0, new ModelResourceLocation(RedPower.MODID + ":igniter", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(indigo_flower), 0, new ModelResourceLocation(RedPower.MODID + ":indigo_flower", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(item_detector), 0, new ModelResourceLocation(RedPower.MODID + ":block_detector", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(kinetic_generator), 0, new ModelResourceLocation(RedPower.MODID + ":kinetic_generator", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(magtube), 0, new ModelResourceLocation(RedPower.MODID + ":magtube", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(manager), 0, new ModelResourceLocation(RedPower.MODID + ":mannager", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(marble), 0, new ModelResourceLocation(RedPower.MODID + ":marble", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(marble_brick), 0, new ModelResourceLocation(RedPower.MODID + ":marble_brick", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(nickolite_ore), 0, new ModelResourceLocation(RedPower.MODID + ":nickolite_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(pnuematic_tube), 0, new ModelResourceLocation(RedPower.MODID + ":pnuematic_tube", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(project_table), 0, new ModelResourceLocation(RedPower.MODID + ":project_table", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(pump), 0, new ModelResourceLocation(RedPower.MODID + ":pump", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(redstone_tube), 0, new ModelResourceLocation(RedPower.MODID + ":redstone_tube", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(regulator), 0, new ModelResourceLocation(RedPower.MODID + ":regulator", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(relay), 0, new ModelResourceLocation(RedPower.MODID + ":relay", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(restriction_tube), 0, new ModelResourceLocation(RedPower.MODID + ":restriction_tube", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(retriever), 0, new ModelResourceLocation(RedPower.MODID + ":retriever", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(rubber_leaves), 0, new ModelResourceLocation(RedPower.MODID + ":rubber_leaves", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(rubber_log), 0, new ModelResourceLocation(RedPower.MODID + ":rubber_wood", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(ruby_block), 0, new ModelResourceLocation(RedPower.MODID + ":ruby_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(ruby_ore), 0, new ModelResourceLocation(RedPower.MODID + ":ruby_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(sapphire_block), 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(sapphire_ore), 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(silver_block), 0, new ModelResourceLocation(RedPower.MODID + ":silver_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(silver_ore), 0, new ModelResourceLocation(RedPower.MODID + ":silver_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(solar_panel), 0, new ModelResourceLocation(RedPower.MODID + ":solar_panel", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(sorting_machine), 0, new ModelResourceLocation(RedPower.MODID + ":sorting_machine", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(sortron), 0, new ModelResourceLocation(RedPower.MODID + ":sortron", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(support_frame), 0, new ModelResourceLocation(RedPower.MODID + ":support_frame", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(thermopile), 0, new ModelResourceLocation(RedPower.MODID + ":thermopile", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(tin_block), 0, new ModelResourceLocation(RedPower.MODID + ":tin_block", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(tin_ore), 0, new ModelResourceLocation(RedPower.MODID + ":tin_ore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(transposer), 0, new ModelResourceLocation(RedPower.MODID + ":transposer", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(tungsten_ore), 0, new ModelResourceLocation(RedPower.MODID + ":tungsten_ore", "inventory"));
		}
	}
}