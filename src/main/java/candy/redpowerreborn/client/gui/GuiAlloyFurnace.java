package candy.redpowerreborn.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.inventory.ContainerAlloyFurnace;
import candy.redpowerreborn.tileentity.TileEntityAlloyFurnace;

public class GuiAlloyFurnace extends GuiContainer {

	private IInventory tileEntityAlloyFurnace;

	public GuiAlloyFurnace(InventoryPlayer inventoryPlayer, TileEntityAlloyFurnace tileEntity) {
		// the container is instanciated and passed to the superclass for
		// handling
		super(new ContainerAlloyFurnace(inventoryPlayer, tileEntity));
		tileEntityAlloyFurnace = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// TODO
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRendererObj.drawString(I18n.format("container." + RedPower.MODID + "_alloyfurnace"), 8, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
		// int texture = mc.renderEngine.bindTexture("/gui/trap.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/afurnacegui.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (TileEntityFurnace.isBurning(this.tileEntityAlloyFurnace)) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(x + 17, y + 26 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(x + 107, y + 34, 176, 14, l + 1, 16);
	}

	private int getCookProgressScaled(int pixels) {

		int i = this.tileEntityAlloyFurnace.getField(2);
		int j = this.tileEntityAlloyFurnace.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels) {
		int i = this.tileEntityAlloyFurnace.getField(1);

		if (i == 0) {
			i = 200;
		}

		return this.tileEntityAlloyFurnace.getField(0) * pixels / i;
	}
}