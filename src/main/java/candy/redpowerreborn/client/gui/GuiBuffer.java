package candy.redpowerreborn.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.inventory.ContainerBuffer;
import candy.redpowerreborn.tileentity.TileEntityBuffer;

public class GuiBuffer extends GuiContainer {
	
	private IInventory tileEntityBuffer;
	
	public GuiBuffer(InventoryPlayer inventoryPlayer, TileEntityBuffer tileEntity) {
		// the container is instantiated and passed to the superclass for
		// handling
		super(new ContainerBuffer(inventoryPlayer, tileEntity));
		tileEntityBuffer = tileEntity;
		ySize = 186;
	}
	
	@Override
	public void initGui() {
		super.initGui(); // make buttons //id, x, y, width, height, text
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// TODO
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		String s = I18n.format("container." + RedPower.MODID + "_buffer");
		fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
		// int texture = mc.renderEngine.bindTexture("/gui/trap.png");
		
		// TODO find a better place for this code so it doesn't run every render
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.mc.renderEngine.bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/buffer.png"));
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
	}
}