package candy.redpowerreborn;

import candy.redpowerreborn.block.BlockAlloyFurnace;
import candy.redpowerreborn.block.RedPowerBlocks;
import candy.redpowerreborn.client.gui.GuiHandler;
import candy.redpowerreborn.crafting.RedPowerCrafting;
import candy.redpowerreborn.crafting.RedPowerOreDictionary;
import candy.redpowerreborn.item.RedPowerItems;
import candy.redpowerreborn.network.MessageAssemblerUpdate;
import candy.redpowerreborn.network.NetworkSetup;
import candy.redpowerreborn.tileentity.TileEntityAlloyFurnace;
import candy.redpowerreborn.tileentity.TileEntityAssembler;
import candy.redpowerreborn.tileentity.TileEntityBuffer;
import candy.redpowerreborn.tileentity.TileEntityDeployer;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.SidedProxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = RedPower.MODID, name = RedPower.MODNAME, version = RedPower.MODVER)
public class RedPower {
	public static final String MODID = "redpowerreborn";
	public static final String MODNAME = "RedPower Reborn";
	public static final String MODVER = "0.0.0a";
	
	// use GUI_GUINAME = guiIndex++;
	
	@Instance(value = RedPower.MODID)
	public static RedPower instance;
	
	public static SimpleNetworkWrapper network;
	
	public static CreativeTabs tabRedPower = new CreativeTabs(MODID + "_redpowertab") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(RedPowerBlocks.project_table);
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(this.MODID);
		//                      always the same,  specific message class,  id,  side (unsure if recieving or sending)
		RedPowerBlocks.preInit(event);
		RedPowerItems.preInit(event);
		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, MODID + "_alloyFurnace");
		GameRegistry.registerTileEntity(TileEntityAssembler.class, MODID + "_assembler");
		GameRegistry.registerTileEntity(TileEntityBuffer.class, MODID + "_buffer");
		GameRegistry.registerTileEntity(TileEntityDeployer.class, MODID + "_deployer");
		GameRegistry.registerTileEntity(TileEntityProjectTable.class, MODID + "_projectTable");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkSetup.init(event);
		RedPowerBlocks.init(event);
		RedPowerItems.init(event);
		RedPowerCrafting.init(event);
		RedPowerOreDictionary.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}