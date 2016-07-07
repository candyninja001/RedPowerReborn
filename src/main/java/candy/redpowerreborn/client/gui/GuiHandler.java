package candy.redpowerreborn.client.gui;

import candy.redpowerreborn.inventory.ContainerAlloyFurnace;
import candy.redpowerreborn.inventory.ContainerAssembler;
import candy.redpowerreborn.inventory.ContainerBuffer;
import candy.redpowerreborn.inventory.ContainerCanvasBag;
import candy.redpowerreborn.inventory.ContainerProjectTable;
import candy.redpowerreborn.inventory.InventoryItem;
import candy.redpowerreborn.tileentity.TileEntityAlloyFurnace;
import candy.redpowerreborn.tileentity.TileEntityAssembler;
import candy.redpowerreborn.tileentity.TileEntityBuffer;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	private static int guiIndex = 0;
	
	public static final int GUI_ALLOYFURNACE = guiIndex++;
	public static final int GUI_CANVASBAG = guiIndex++;
	public static final int GUI_CANVASBAG_OFF = guiIndex++;
	public static final int GUI_NINESLOT = guiIndex++;
	public static final int GUI_ASSEMBLER = guiIndex++;
	public static final int GUI_BUFFER = guiIndex++;
	public static final int GUI_PROJECTTABLE = guiIndex++;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GUI_ALLOYFURNACE)
			return new ContainerAlloyFurnace(player.inventory, (TileEntityAlloyFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_CANVASBAG)
			return new ContainerCanvasBag(player, player.inventory, new InventoryItem(player.getHeldItemMainhand()));
		else if (id == GUI_CANVASBAG_OFF)
			return new ContainerCanvasBag(player, player.inventory, new InventoryItem(player.getHeldItemOffhand()));
		else if (id == GUI_NINESLOT)
			return new ContainerDispenser((IInventory)player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_ASSEMBLER)
			return new ContainerAssembler(player.inventory, (TileEntityAssembler) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_BUFFER)
			return new ContainerBuffer(player.inventory, (TileEntityBuffer) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_PROJECTTABLE)
			return new ContainerProjectTable(player.inventory, (TileEntityProjectTable) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GUI_ALLOYFURNACE)
			return new GuiAlloyFurnace(player.inventory, (TileEntityAlloyFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_CANVASBAG)
			return new GuiCanvasBag((ContainerCanvasBag) new ContainerCanvasBag(player, player.inventory, new InventoryItem(player.getHeldItemMainhand())));
		else if (id == GUI_CANVASBAG_OFF)
			return new GuiCanvasBag((ContainerCanvasBag) new ContainerCanvasBag(player, player.inventory, new InventoryItem(player.getHeldItemOffhand())));
		else if (id == GUI_NINESLOT)
			return new GuiDispenser(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_ASSEMBLER)
			return new GuiAssembler(player.inventory, (TileEntityAssembler) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_BUFFER)
			return new GuiBuffer(player.inventory, (TileEntityBuffer) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GUI_PROJECTTABLE)
			return new GuiProjectTable(player.inventory, (TileEntityProjectTable) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
		
	}
}
