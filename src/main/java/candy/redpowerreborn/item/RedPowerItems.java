package candy.redpowerreborn.item;

import candy.redpowerreborn.RedPower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class RedPowerItems {
	public static Item athame;
	public static Item battery;
	public static Item blue_alloy_ingot;
	public static Item blue_doped_wafer;
	public static Item blulectric_motor;
	public static Item brass_ingot;
	public static Item brass_gear;
	public static Item canvas;
	public static Item canvas_bag;
	public static Item copper_coil;
	public static Item copper_ingot;
	public static Item copper_nugget;
	public static Item copper_wire;
	public static Item diamond_drawplate;
	public static Item diamond_handsaw;
	public static Item diamond_sickle;
	public static Item emerald;
	public static Item emerald_axe;
	public static Item emerald_handsaw;
	public static Item emerald_hoe;
	public static Item emerald_pickaxe;
	public static Item emerald_shovel;
	public static Item emerald_sickle;
	public static Item emerald_sword;
	public static Item flax_seed;
	public static Item gold_sickle;
	public static Item indigo_dye;
	public static Item iron_handsaw;
	public static Item iron_nugget;
	public static Item iron_sickle;
	public static Item iron_wire;
	public static Item lumar;
	public static Item nikolite;
	public static Item paint_brush;
	public static Item paint_can;
	public static Item plan;
	public static Item red_alloy_ingot;
	public static Item red_doped_wafer;
	public static Item ruby;
	public static Item ruby_axe;
	public static Item ruby_handsaw;
	public static Item ruby_hoe;
	public static Item ruby_pickaxe;
	public static Item ruby_shovel;
	public static Item ruby_sickle;
	public static Item ruby_sword;
	public static Item sapphire;
	public static Item sapphire_axe;
	public static Item sapphire_handsaw;
	public static Item sapphire_hoe;
	public static Item sapphire_pickaxe;
	public static Item sapphire_shovel;
	public static Item sapphire_sickle;
	public static Item sapphire_sword;
	public static Item screwdriver;
	public static Item seed_bag;
	public static Item silicon_boule;
	public static Item silicon_chip;
	public static Item silicon_wafer;
	public static Item silver_ingot;
	public static Item silver_nugget;
	public static Item sonic_screwdriver;
	public static Item stone_anode;
	public static Item stone_assembly;
	public static Item stone_bundle;
	public static Item stone_cathode;
	public static Item stone_pointer;
	public static Item stone_redwire;
	public static Item stone_sickle;
	public static Item stone_wafer;
	public static Item stone_wire;
	public static Item tainted_silicon_chip;
	public static Item tin_ingot;
	public static Item tin_nugget;
	public static Item tin_plate;
	public static Item voltmeter;
	public static Item wooden_sickle;
	public static Item wooden_sail;
	public static Item wooden_wind_turbine;
	public static Item wooden_windmill;
	public static Item wool_card;
	
	public static ToolMaterial EMERALD;
	public static ToolMaterial RUBY;
	public static ToolMaterial SAPPHIRE;

	public static void preInit(FMLPreInitializationEvent event) {
		
		//might have to register these after the emeralds but still before the tools
		EMERALD = EnumHelper.addToolMaterial("EMERALD", 2, 500, 8.0F, 3.0F, 8).setRepairItem(new ItemStack(emerald));//TODO check values later maybe
		RUBY = EnumHelper.addToolMaterial("RUBY", 2, 500, 8.0F, 3.0F, 8).setRepairItem(new ItemStack(ruby));
		SAPPHIRE = EnumHelper.addToolMaterial("SAPPHIRE", 2, 500, 8.0F, 3.0F, 8).setRepairItem(new ItemStack(sapphire));
		
		athame = new ItemAthame().setUnlocalizedName(RedPower.MODID + "_athame").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(athame, "athame");
		battery = new Item().setUnlocalizedName(RedPower.MODID + "_battery").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(battery, "battery");
		blue_alloy_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotBlueAlloy").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(blue_alloy_ingot, "blue_alloy_ingot");
		blue_doped_wafer = new Item().setUnlocalizedName(RedPower.MODID + "_blueDopedWafer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(blue_doped_wafer, "blue_doped_wafer");
		blulectric_motor = new Item().setUnlocalizedName(RedPower.MODID + "_blulectricMotor").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(blulectric_motor, "blulectric_motor");
		brass_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotBrass").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(brass_ingot, "brass_ingot");
		brass_gear = new Item().setUnlocalizedName(RedPower.MODID + "_gearBrass").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(brass_gear, "brass_gear");
		canvas = new Item().setUnlocalizedName(RedPower.MODID + "_canvas").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(canvas, "canvas");
		canvas_bag = new ItemCanvasBag().setUnlocalizedName(RedPower.MODID + "_canvasBag").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(canvas_bag, "canvas_bag");
		copper_coil = new Item().setUnlocalizedName(RedPower.MODID + "_coilCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(copper_coil, "copper_coil");
		copper_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(copper_ingot, "copper_ingot");
		copper_nugget = new Item().setUnlocalizedName(RedPower.MODID + "_nuggetCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(copper_nugget, "copper_nugget");
		copper_wire = new Item().setUnlocalizedName(RedPower.MODID + "_wireCopper").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(copper_wire, "copper_wire");
		diamond_drawplate = new Item().setUnlocalizedName(RedPower.MODID + "_drawplateDiamond").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(diamond_drawplate, "diamond_drawplate");
		diamond_handsaw = new Item().setUnlocalizedName(RedPower.MODID + "_handsawDiamond").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(diamond_handsaw, "diamond_handsaw");
		diamond_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleDiamond").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(diamond_sickle, "diamond_sickle");
		emerald = new Item().setUnlocalizedName(RedPower.MODID + "_emerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald, "emerald");
		emerald_axe = new Item().setUnlocalizedName(RedPower.MODID + "_axeEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_axe, "emerald_axe");
		emerald_handsaw = new Item().setUnlocalizedName(RedPower.MODID + "_handsawEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_handsaw, "emerald_handsaw");
		emerald_hoe = new Item().setUnlocalizedName(RedPower.MODID + "_hoeEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_hoe, "emerald_hoe");
		emerald_pickaxe = new Item().setUnlocalizedName(RedPower.MODID + "_pickaxeEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_pickaxe, "emerald_pickaxe");
		emerald_shovel = new Item().setUnlocalizedName(RedPower.MODID + "_shovelEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_shovel, "emerald_shovel");
		emerald_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_sickle, "emerald_sickle");
		emerald_sword = new Item().setUnlocalizedName(RedPower.MODID + "_swordEmerald").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(emerald_sword, "emerald_sword");
		flax_seed = new Item().setUnlocalizedName(RedPower.MODID + "_seedFlax").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(flax_seed, "flax_seed");
		gold_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleGold").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(gold_sickle, "gold_sickle");
		indigo_dye = new Item().setUnlocalizedName(RedPower.MODID + "_dye.indigo").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(indigo_dye, "indigo_dye");
		iron_handsaw = new Item().setUnlocalizedName(RedPower.MODID + "_handsawIron").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(iron_handsaw, "iron_handsaw");
		iron_nugget = new Item().setUnlocalizedName(RedPower.MODID + "_nuggetIron").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(iron_nugget, "iron_nugget");
		iron_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleIron").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(iron_sickle, "iron_sickle");
		iron_wire = new Item().setUnlocalizedName(RedPower.MODID + "_wireIron").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(iron_wire, "iron_wire");
		lumar = new Item().setUnlocalizedName(RedPower.MODID + "_lumar").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(lumar, "lumar");
		nikolite = new Item().setUnlocalizedName(RedPower.MODID + "_nikolite").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(nikolite, "nikolite");
		paint_brush = new Item().setUnlocalizedName(RedPower.MODID + "_paintBrush").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(paint_brush, "paint_brush");
		paint_can = new Item().setUnlocalizedName(RedPower.MODID + "_paintCan").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(paint_can, "paint_can");
		plan = new ItemPlan().setUnlocalizedName(RedPower.MODID + "_plan").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(plan, "plan");
		red_alloy_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotRedAlloy").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(red_alloy_ingot, "red_alloy_ingot");
		red_doped_wafer = new Item().setUnlocalizedName(RedPower.MODID + "_redDopedWafer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(red_doped_wafer, "red_doped_wafer");
		ruby = new Item().setUnlocalizedName(RedPower.MODID + "_ruby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby, "ruby");
		ruby_axe = new Item().setUnlocalizedName(RedPower.MODID + "_axeRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_axe, "ruby_axe");
		ruby_handsaw = new Item().setUnlocalizedName(RedPower.MODID + "_handsawRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_handsaw, "ruby_handsaw");
		ruby_hoe = new Item().setUnlocalizedName(RedPower.MODID + "_hoeRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_hoe, "ruby_hoe");
		ruby_pickaxe = new Item().setUnlocalizedName(RedPower.MODID + "_pickaxeRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_pickaxe, "ruby_pickaxe");
		ruby_shovel = new Item().setUnlocalizedName(RedPower.MODID + "_shovelRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_shovel, "ruby_shovel");
		ruby_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_sickle, "ruby_sickle");
		ruby_sword = new Item().setUnlocalizedName(RedPower.MODID + "_swordRuby").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(ruby_sword, "ruby_sword");
		sapphire = new Item().setUnlocalizedName(RedPower.MODID + "_sapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire, "sapphire");
		sapphire_axe = new Item().setUnlocalizedName(RedPower.MODID + "_axeSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_axe, "sapphire_axe");
		sapphire_handsaw = new Item().setUnlocalizedName(RedPower.MODID + "_handsawSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_handsaw, "sapphire_handsaw");
		sapphire_hoe = new Item().setUnlocalizedName(RedPower.MODID + "_hoeSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_hoe, "sapphire_hoe");
		sapphire_pickaxe = new Item().setUnlocalizedName(RedPower.MODID + "_pickaxeSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_pickaxe, "sapphire_pickaxe");
		sapphire_shovel = new Item().setUnlocalizedName(RedPower.MODID + "_shovelSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_shovel, "sapphire_shovel");
		sapphire_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_sickle, "sapphire_sickle");
		sapphire_sword = new Item().setUnlocalizedName(RedPower.MODID + "_swordSapphire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sapphire_sword, "sapphire_sword");
		screwdriver = new Item().setUnlocalizedName(RedPower.MODID + "_screwdriver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(screwdriver, "screwdriver");
		seed_bag = new Item().setUnlocalizedName(RedPower.MODID + "_seedBag").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(seed_bag, "seed_bag");
		silicon_boule = new Item().setUnlocalizedName(RedPower.MODID + "_siliconBoule").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(silicon_boule, "silicon_boule");
		silicon_chip = new Item().setUnlocalizedName(RedPower.MODID + "_siliconChip").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(silicon_chip, "silicon_chip");
		silicon_wafer = new Item().setUnlocalizedName(RedPower.MODID + "_siliconWafer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(silicon_wafer, "silicon_wafer");
		silver_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotSilver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(silver_ingot, "silver_ingot");
		silver_nugget = new Item().setUnlocalizedName(RedPower.MODID + "_nuggetSilver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(silver_nugget, "silver_nugget");
		sonic_screwdriver = new Item().setUnlocalizedName(RedPower.MODID + "_sonicScrewdriver").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(sonic_screwdriver, "sonic_screwdriver");
		stone_anode = new Item().setUnlocalizedName(RedPower.MODID + "_stoneAnode").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_anode, "stone_anode");
		stone_assembly = new Item().setUnlocalizedName(RedPower.MODID + "_stoneAssembly").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_assembly, "stone_assembly");
		stone_bundle = new Item().setUnlocalizedName(RedPower.MODID + "_stoneBundle").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_bundle, "stone_bundle");
		stone_cathode = new Item().setUnlocalizedName(RedPower.MODID + "_stoneCathode").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_cathode, "stone_cathode");
		stone_pointer = new Item().setUnlocalizedName(RedPower.MODID + "_stonePointer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_pointer, "stone_pointer");
		stone_redwire = new Item().setUnlocalizedName(RedPower.MODID + "_stoneRedwire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_redwire, "stone_redwire");
		stone_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleStone").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_sickle, "stone_sickle");
		stone_wafer = new Item().setUnlocalizedName(RedPower.MODID + "_stoneWafer").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_wafer, "stone_wafer");
		stone_wire = new Item().setUnlocalizedName(RedPower.MODID + "_stoneWire").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(stone_wire, "stone_wire");
		tainted_silicon_chip = new Item().setUnlocalizedName(RedPower.MODID + "_taintedSiliconChip").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(tainted_silicon_chip, "tainted_silicon_chip");
		tin_ingot = new Item().setUnlocalizedName(RedPower.MODID + "_ingotTin").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(tin_ingot, "tin_ingot");
		tin_nugget = new Item().setUnlocalizedName(RedPower.MODID + "_nuggetTin").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(tin_nugget, "tin_nugget");
		tin_plate = new Item().setUnlocalizedName(RedPower.MODID + "_tinPlate").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(tin_plate, "tin_plate");
		voltmeter = new Item().setUnlocalizedName(RedPower.MODID + "_voltmeter").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(voltmeter, "voltmeter");
		wooden_sickle = new Item().setUnlocalizedName(RedPower.MODID + "_sickleWooden").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(wooden_sickle, "wood_sickle");
		wooden_sail = new Item().setUnlocalizedName(RedPower.MODID + "_sail").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(wooden_sail, "wooden_sail");
		wooden_wind_turbine = new Item().setUnlocalizedName(RedPower.MODID + "_woodenWindTurbine").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(wooden_wind_turbine, "wooden_wind_turbine");
		wooden_windmill = new Item().setUnlocalizedName(RedPower.MODID + "_woodenWindmill").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(wooden_windmill, "wooden_windmill");
		wool_card = new Item().setUnlocalizedName(RedPower.MODID + "_woolCard").setCreativeTab(RedPower.tabRedPower);
		GameRegistry.registerItem(wool_card, "wool_card");
	}
	
	public static void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			
			renderItem.getItemModelMesher().register(athame, 0, new ModelResourceLocation(RedPower.MODID + ":athame", "inventory"));
			renderItem.getItemModelMesher().register(battery, 0, new ModelResourceLocation(RedPower.MODID + ":battery", "inventory"));
			renderItem.getItemModelMesher().register(blue_alloy_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":blue_alloy_ingot", "inventory"));
			renderItem.getItemModelMesher().register(blue_doped_wafer, 0, new ModelResourceLocation(RedPower.MODID + ":blue_doped_wafer", "inventory"));
			renderItem.getItemModelMesher().register(blulectric_motor, 0, new ModelResourceLocation(RedPower.MODID + ":blulectric_motor", "inventory"));
			renderItem.getItemModelMesher().register(brass_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":brass_ingot", "inventory"));
			renderItem.getItemModelMesher().register(brass_gear, 0, new ModelResourceLocation(RedPower.MODID + ":brass_gear", "inventory"));
			renderItem.getItemModelMesher().register(canvas, 0, new ModelResourceLocation(RedPower.MODID + ":canvas", "inventory"));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(canvas_bag, new ItemMeshDefinition() {
				public ModelResourceLocation getModelLocation(ItemStack stack) {
					return new ModelResourceLocation(RedPower.MODID + ":canvas_bag", "inventory");
				}
			});
			renderItem.getItemModelMesher().register(copper_coil, 0, new ModelResourceLocation(RedPower.MODID + ":copper_coil", "inventory"));
			renderItem.getItemModelMesher().register(copper_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":copper_ingot", "inventory"));
			renderItem.getItemModelMesher().register(copper_nugget, 0, new ModelResourceLocation(RedPower.MODID + ":copper_nugget", "inventory"));
			renderItem.getItemModelMesher().register(copper_wire, 0, new ModelResourceLocation(RedPower.MODID + ":copper_wire", "inventory"));
			renderItem.getItemModelMesher().register(diamond_drawplate, 0, new ModelResourceLocation(RedPower.MODID + ":diamond_drawplate", "inventory"));
			renderItem.getItemModelMesher().register(diamond_handsaw, 0, new ModelResourceLocation(RedPower.MODID + ":diamond_handsaw", "inventory"));
			renderItem.getItemModelMesher().register(diamond_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":diamond_sickle", "inventory"));
			renderItem.getItemModelMesher().register(emerald, 0, new ModelResourceLocation(RedPower.MODID + ":emerald", "inventory"));
			renderItem.getItemModelMesher().register(emerald_axe, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_axe", "inventory"));
			renderItem.getItemModelMesher().register(emerald_handsaw, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_handsaw", "inventory"));
			renderItem.getItemModelMesher().register(emerald_hoe, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_hoe", "inventory"));
			renderItem.getItemModelMesher().register(emerald_pickaxe, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_pickaxe", "inventory"));
			renderItem.getItemModelMesher().register(emerald_shovel, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_shovel", "inventory"));
			renderItem.getItemModelMesher().register(emerald_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_sickle", "inventory"));
			renderItem.getItemModelMesher().register(emerald_sword, 0, new ModelResourceLocation(RedPower.MODID + ":emerald_sword", "inventory"));
			renderItem.getItemModelMesher().register(flax_seed, 0, new ModelResourceLocation(RedPower.MODID + ":flax_seed", "inventory"));
			renderItem.getItemModelMesher().register(gold_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":gold_sickle", "inventory"));
			renderItem.getItemModelMesher().register(indigo_dye, 0, new ModelResourceLocation(RedPower.MODID + ":indigo_dye", "inventory"));
			renderItem.getItemModelMesher().register(iron_handsaw, 0, new ModelResourceLocation(RedPower.MODID + ":iron_handsaw", "inventory"));
			renderItem.getItemModelMesher().register(iron_nugget, 0, new ModelResourceLocation(RedPower.MODID + ":iron_nugget", "inventory"));
			renderItem.getItemModelMesher().register(iron_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":iron_sickle", "inventory"));
			renderItem.getItemModelMesher().register(iron_wire, 0, new ModelResourceLocation(RedPower.MODID + ":iron_wire", "inventory"));
			renderItem.getItemModelMesher().register(lumar, 0, new ModelResourceLocation(RedPower.MODID + ":lumar", "inventory"));
			renderItem.getItemModelMesher().register(nikolite, 0, new ModelResourceLocation(RedPower.MODID + ":nikolite", "inventory"));
			renderItem.getItemModelMesher().register(paint_brush, 0, new ModelResourceLocation(RedPower.MODID + ":paint_brush", "inventory"));
			renderItem.getItemModelMesher().register(paint_can, 0, new ModelResourceLocation(RedPower.MODID + ":paint_can", "inventory"));
			renderItem.getItemModelMesher().register(plan, 0, new ModelResourceLocation(RedPower.MODID + ":plan", "inventory"));
			renderItem.getItemModelMesher().register(red_alloy_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":red_alloy_ingot", "inventory"));
			renderItem.getItemModelMesher().register(red_doped_wafer, 0, new ModelResourceLocation(RedPower.MODID + ":red_doped_wafer", "inventory"));
			renderItem.getItemModelMesher().register(ruby, 0, new ModelResourceLocation(RedPower.MODID + ":ruby", "inventory"));
			renderItem.getItemModelMesher().register(ruby_axe, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_axe", "inventory"));
			renderItem.getItemModelMesher().register(ruby_handsaw, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_handsaw", "inventory"));
			renderItem.getItemModelMesher().register(ruby_hoe, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_hoe", "inventory"));
			renderItem.getItemModelMesher().register(ruby_pickaxe, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_pickaxe", "inventory"));
			renderItem.getItemModelMesher().register(ruby_shovel, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_shovel", "inventory"));
			renderItem.getItemModelMesher().register(ruby_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_sickle", "inventory"));
			renderItem.getItemModelMesher().register(ruby_sword, 0, new ModelResourceLocation(RedPower.MODID + ":ruby_sword", "inventory"));
			renderItem.getItemModelMesher().register(sapphire, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_axe, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_axe", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_handsaw, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_handsaw", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_hoe, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_hoe", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_pickaxe, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_pickaxe", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_shovel, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_shovel", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_sickle", "inventory"));
			renderItem.getItemModelMesher().register(sapphire_sword, 0, new ModelResourceLocation(RedPower.MODID + ":sapphire_sword", "inventory"));
			renderItem.getItemModelMesher().register(screwdriver, 0, new ModelResourceLocation(RedPower.MODID + ":screwdriver", "inventory"));
			renderItem.getItemModelMesher().register(seed_bag, 0, new ModelResourceLocation(RedPower.MODID + ":seed_bag", "inventory"));
			renderItem.getItemModelMesher().register(silicon_boule, 0, new ModelResourceLocation(RedPower.MODID + ":silicon_boule", "inventory"));
			renderItem.getItemModelMesher().register(silicon_chip, 0, new ModelResourceLocation(RedPower.MODID + ":silicon_chip", "inventory"));
			renderItem.getItemModelMesher().register(silicon_wafer, 0, new ModelResourceLocation(RedPower.MODID + ":silicon_wafer", "inventory"));
			renderItem.getItemModelMesher().register(silver_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":silver_ingot", "inventory"));
			renderItem.getItemModelMesher().register(silver_nugget, 0, new ModelResourceLocation(RedPower.MODID + ":silver_nugget", "inventory"));
			renderItem.getItemModelMesher().register(sonic_screwdriver, 0, new ModelResourceLocation(RedPower.MODID + ":sonic_screwdriver", "inventory"));
			renderItem.getItemModelMesher().register(stone_anode, 0, new ModelResourceLocation(RedPower.MODID + ":stone_anode", "inventory"));
			renderItem.getItemModelMesher().register(stone_assembly, 0, new ModelResourceLocation(RedPower.MODID + ":stone_assembly", "inventory"));
			renderItem.getItemModelMesher().register(stone_bundle, 0, new ModelResourceLocation(RedPower.MODID + ":stone_bundle", "inventory"));
			renderItem.getItemModelMesher().register(stone_cathode, 0, new ModelResourceLocation(RedPower.MODID + ":stone_cathode", "inventory"));
			renderItem.getItemModelMesher().register(stone_pointer, 0, new ModelResourceLocation(RedPower.MODID + ":stone_pointer", "inventory"));
			renderItem.getItemModelMesher().register(stone_redwire, 0, new ModelResourceLocation(RedPower.MODID + ":stone_redwire", "inventory"));
			renderItem.getItemModelMesher().register(stone_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":stone_sickle", "inventory"));
			renderItem.getItemModelMesher().register(stone_wafer, 0, new ModelResourceLocation(RedPower.MODID + ":stone_wafer", "inventory"));
			renderItem.getItemModelMesher().register(stone_wire, 0, new ModelResourceLocation(RedPower.MODID + ":stone_wire", "inventory"));
			renderItem.getItemModelMesher().register(tainted_silicon_chip, 0, new ModelResourceLocation(RedPower.MODID + ":tainted_silicon_chip", "inventory"));
			renderItem.getItemModelMesher().register(tin_ingot, 0, new ModelResourceLocation(RedPower.MODID + ":tin_ingot", "inventory"));
			renderItem.getItemModelMesher().register(tin_nugget, 0, new ModelResourceLocation(RedPower.MODID + ":tin_nugget", "inventory"));
			renderItem.getItemModelMesher().register(tin_plate, 0, new ModelResourceLocation(RedPower.MODID + ":tin_plate", "inventory"));
			renderItem.getItemModelMesher().register(voltmeter, 0, new ModelResourceLocation(RedPower.MODID + ":voltmeter", "inventory"));
			renderItem.getItemModelMesher().register(wooden_sickle, 0, new ModelResourceLocation(RedPower.MODID + ":wood_sickle", "inventory"));
			renderItem.getItemModelMesher().register(wooden_sail, 0, new ModelResourceLocation(RedPower.MODID + ":wooden_sail", "inventory"));
			renderItem.getItemModelMesher().register(wooden_wind_turbine, 0, new ModelResourceLocation(RedPower.MODID + ":wooden_wind_turbine", "inventory"));
			renderItem.getItemModelMesher().register(wooden_windmill, 0, new ModelResourceLocation(RedPower.MODID + ":wooden_windmill", "inventory"));
			renderItem.getItemModelMesher().register(wool_card, 0, new ModelResourceLocation(RedPower.MODID + ":wool_card", "inventory"));
		}
	}
}