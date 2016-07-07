package candy.redpowerreborn.client.gui;

import org.lwjgl.opengl.GL11;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.inventory.ContainerCanvasBag;
import candy.redpowerreborn.inventory.InventoryItem;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiCanvasBag extends GuiContainer
{
	/** x and y size of the inventory window in pixels. Defined as float, passed as int
	 * These are used for drawing the player model. */
	private float xSize_lo;
	private float ySize_lo;

	/** ResourceLocation takes 2 parameters: ModId, path to texture at the location:
	 * "src/minecraft/assets/modid/"
	 * 
	 * I have provided a sample texture file that works with this tutorial. Download it
	 * from Forge_Tutorials/textures/gui/
	 */
	private static final ResourceLocation iconLocation = new ResourceLocation("inventoryitemmod", "textures/gui/inventoryitem.png");

	/** The inventory to render on screen */
	private final InventoryItem inventory;

	public GuiCanvasBag(ContainerCanvasBag container)
	{
		super(container);
		this.inventory = container.inventory;
	}

//	/**
//	 * Draws the screen and all the components in it.
//	 */
//	public void drawScreen(int par1, int par2, float par3)
//	{
//		super.drawScreen(par1, par2, par3);
//		this.xSize_lo = (float)par1;
//		this.ySize_lo = (float)par2;
//	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		//String s = this.inventory.isInvNameLocalized() ? this.inventory.getInvName() : I18n.getString(this.inventory.getInvName());
		//this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 0, 4210752);
		//this.fontRenderer.drawString(I18n.getString("container.inventory"), 26, this.ySize - 96 + 4, 4210752);
		
		fontRendererObj.drawString(I18n.format("container." + RedPower.MODID + "_canvasbag"), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 95 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j -1, 0, 0, this.xSize, 71);
        this.drawTexturedModalRect(i, j+70, 0, 126, this.xSize, 96);
	}
}