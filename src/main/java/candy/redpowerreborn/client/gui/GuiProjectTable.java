package candy.redpowerreborn.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.inventory.ContainerProjectTable;
import candy.redpowerreborn.item.ItemPlan;
import candy.redpowerreborn.network.MessageProjectTablePlan;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;

public class GuiProjectTable extends GuiContainer {
	
	private IInventory tileEntityProjectTable;
	private World worldObj;
	private List<ItemStack> supplyCheckStacks;
	
	public GuiProjectTable(InventoryPlayer inventoryPlayer, TileEntityProjectTable tileEntity) {
		// the container is instantiated and passed to the superclass for
		// handling
		super(new ContainerProjectTable(inventoryPlayer, tileEntity));
		this.worldObj = inventoryPlayer.player.worldObj;
		tileEntityProjectTable = tileEntity;
		ySize = 222;
	}
	
	@Override
	public void initGui() {
		super.initGui(); // make buttons //id, x, y, width, height, text
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		buttonList.add(new GuiProjectTableButton(0, x + 18, y + 55, 14, 14));
		buttonList.get(0).visible = ((TileEntityProjectTable) tileEntityProjectTable).getField(0) == 1;
	}
	
	protected void actionPerformed(GuiButton guibutton) { // id is the id you give your button
		switch (guibutton.id) {
			case 0:
				// ((TileEntityProjectTable) tileEntityProjectTable).recordPlan();
				RedPower.network.sendToServer(new MessageProjectTablePlan(((TileEntityProjectTable) this.tileEntityProjectTable).getPos(), ((TileEntityProjectTable) this.tileEntityProjectTable).getWorld().provider.getDimension()));
				break;
			default:
				
				break;
			// TODO send packet code to make this work?
		} // Packet code here //
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// TODO
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		String s = I18n.format("container." + RedPower.MODID + "_projecttable");
		fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
		// int texture = mc.renderEngine.bindTexture("/gui/trap.png");
		
		this.buttonList.get(0).visible = ((TileEntityProjectTable) tileEntityProjectTable).getField(0) == 1;
		// this.buttonList.get(0).visible = true;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.mc.renderEngine.bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/advbench.png"));
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	/**
	 * Draws the screen and all the components in it.
	 * 
	 * @param mouseX
	 *            Mouse x coordinate
	 * @param mouseY
	 *            Mouse y coordinate
	 * @param partialTicks
	 *            How far into the current tick (1/20th of a second) the game is
	 */
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		// this.drawDefaultBackground();
		ItemStack[] stacks = ItemPlan.getCraftingStacks(this.inventorySlots.inventorySlots.get(9).getStack());
		if (stacks != null && stacks.length == 9) {
			int i = this.guiLeft;
			int j = this.guiTop;
			// this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
			GlStateManager.disableRescaleNormal();
			// RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			// super.drawScreen(mouseX, mouseY, partialTicks);
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) i, (float) j, 0.0F);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableRescaleNormal();
			// this.theSlot = null;
			int k = 240;
			int l = 240;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k, (float) l);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.resetSupplyCheck();
			for (int i1 = 0; i1 < 9; ++i1) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i1);
				if (!slot.getHasStack() && stacks[i1] != null) {
					this.renderGhost(slot, stacks[i1], i1);
					
					if (!this.canSupply(stacks[i1])) {
						// this.theSlot = slot;
						GlStateManager.disableLighting();
						GlStateManager.disableDepth();
						int j1 = slot.xDisplayPosition;
						int k1 = slot.yDisplayPosition;
						GlStateManager.colorMask(true, true, true, false);
						this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433); //TODO find the right color values
						GlStateManager.colorMask(true, true, true, true);
						GlStateManager.enableLighting();
						GlStateManager.enableDepth();
					}
				}
			}
			
			RenderHelper.disableStandardItemLighting();
			// this.drawGuiContainerForegroundLayer(mouseX, mouseY);
			// RenderHelper.enableGUIStandardItemLighting();
			// InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
			// ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;
			//
			// if (itemstack != null)
			// {
			// int j2 = 8;
			// int k2 = this.draggedStack == null ? 8 : 16;
			// String s = null;
			//
			// if (this.draggedStack != null && this.isRightMouseClick)
			// {
			// itemstack = itemstack.copy();
			// itemstack.stackSize = MathHelper.ceiling_float_int((float)itemstack.stackSize / 2.0F);
			// }
			// else if (this.dragSplitting && this.dragSplittingSlots.size() > 1)
			// {
			// itemstack = itemstack.copy();
			// itemstack.stackSize = this.dragSplittingRemnant;
			//
			// if (itemstack.stackSize == 0)
			// {
			// s = "" + TextFormatting.YELLOW + "0";
			// }
			// }
			//
			// this.drawItemStack(itemstack, mouseX - i - j2, mouseY - j - k2, s);
			// }
			//
			// if (this.returningStack != null)
			// {
			// float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
			//
			// if (f >= 1.0F)
			// {
			// f = 1.0F;
			// this.returningStack = null;
			// }
			//
			// int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
			// int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
			// int l1 = this.touchUpX + (int)((float)l2 * f);
			// int i2 = this.touchUpY + (int)((float)i3 * f);
			// this.drawItemStack(this.returningStack, l1, i2, (String)null);
			// }
			//
			// GlStateManager.popMatrix();
			//
			// if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
			// {
			// ItemStack itemstack1 = this.theSlot.getStack();
			// this.renderToolTip(itemstack1, mouseX, mouseY);
			// }
			//
			// GlStateManager.enableLighting();
			// GlStateManager.enableDepth();
			// RenderHelper.enableStandardItemLighting();
		}
	}
	
	private void renderGhost(Slot slotIn, ItemStack stack, int i1) {
		int i = slotIn.xDisplayPosition;
		int j = slotIn.yDisplayPosition;
		boolean flag = false;
		// boolean flag1 = slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
		ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
		String s = null;
		
		// if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null)
		// {
		// itemstack = itemstack.copy();
		// itemstack.stackSize /= 2;
		// }
		// else
		// if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && itemstack1 != null) {
		// if (this.dragSplittingSlots.size() == 1) {
		// return;
		// }
		//
		// if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
		// itemstack = itemstack1.copy();
		// flag = true;
		// Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack() == null ? 0 : slotIn.getStack().stackSize);
		//
		// if (itemstack.stackSize > itemstack.getMaxStackSize()) {
		// s = TextFormatting.YELLOW + "" + itemstack.getMaxStackSize();
		// itemstack.stackSize = itemstack.getMaxStackSize();
		// }
		//
		// if (itemstack.stackSize > slotIn.getItemStackLimit(itemstack)) {
		// s = TextFormatting.YELLOW + "" + slotIn.getItemStackLimit(itemstack);
		// itemstack.stackSize = slotIn.getItemStackLimit(itemstack);
		// }
		// } else {
		// this.dragSplittingSlots.remove(slotIn);
		// this.func_146980_g();
		// }
		// }
		
		this.zLevel = 100.0F;
		this.itemRender.zLevel = 100.0F;
		
		GlStateManager.enableDepth();
		this.itemRender.renderItemAndEffectIntoGUI(this.mc.thePlayer, stack, i, j);
		
		// if (!flag1)
		// {
		// if (flag)
		// {
		// drawRect(i, j, i + 16, j + 16, -2130706433);
		// }
		//
		// GlStateManager.enableDepth();
		// this.itemRender.renderItemAndEffectIntoGUI(this.mc.thePlayer, itemstack, i, j);
		// this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
		// }
		
		this.itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}
	
	private boolean canSupply(ItemStack itemStack) {
		for (int i = 11; i < 28; i++) {
			if (this.supplyCheckStacks.get(i).func_185136_b(itemStack) && this.supplyCheckStacks.get(i).stackSize > 0) {
				if (this.supplyCheckStacks.get(i).hasTagCompound() && this.supplyCheckStacks.get(i).getTagCompound().equals(itemStack.getTagCompound())) {
					this.supplyCheckStacks.get(i).stackSize--;
					return true;
				} else if (!this.supplyCheckStacks.get(i).hasTagCompound()) {
					this.supplyCheckStacks.get(i).stackSize--;
					return true;
				}
			}
		}
		return false;
	}
	
	private void resetSupplyCheck() {
		this.supplyCheckStacks = new ArrayList<ItemStack>();
		for (Slot slot : this.inventorySlots.inventorySlots)
			this.supplyCheckStacks.add(slot.getStack().copy());
	}
	
	// /**
	// * Draws the given slot: any item in it, the slot's background, the hovered highlight, etc.
	// *
	// * @param slotIn
	// * The slot to draw
	// */
	// // TODO add extra bits for rendering the information from the current plan
	// private void drawSlot(Slot slotIn) {
	// // code for hovering making slot covered in white, could change to red
	// //
	// // if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered())
	// // {
	// // this.theSlot = slot;
	// // GlStateManager.disableLighting();
	// // GlStateManager.disableDepth();
	// // int j1 = slot.xDisplayPosition;
	// // int k1 = slot.yDisplayPosition;
	// // GlStateManager.colorMask(true, true, true, false);
	// // this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
	// // GlStateManager.colorMask(true, true, true, true);
	// // GlStateManager.enableLighting();
	// // GlStateManager.enableDepth();
	// // }
	//
	// int i = slotIn.xDisplayPosition;
	// int j = slotIn.yDisplayPosition;
	// ItemStack itemstack = slotIn.getStack();
	// boolean flag = false;
	// boolean flag1 = slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
	// ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
	// String s = null;
	//
	// if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
	// itemstack = itemstack.copy();
	// itemstack.stackSize /= 2;
	// } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && itemstack1 != null) {
	// if (this.dragSplittingSlots.size() == 1) {
	// return;
	// }
	//
	// if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
	// itemstack = itemstack1.copy();
	// flag = true;
	// Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack() == null ? 0 : slotIn.getStack().stackSize);
	//
	// if (itemstack.stackSize > itemstack.getMaxStackSize()) {
	// s = TextFormatting.YELLOW + "" + itemstack.getMaxStackSize();
	// itemstack.stackSize = itemstack.getMaxStackSize();
	// }
	//
	// if (itemstack.stackSize > slotIn.getItemStackLimit(itemstack)) {
	// s = TextFormatting.YELLOW + "" + slotIn.getItemStackLimit(itemstack);
	// itemstack.stackSize = slotIn.getItemStackLimit(itemstack);
	// }
	// } else {
	// this.dragSplittingSlots.remove(slotIn);
	// this.func_146980_g();
	// }
	// }
	//
	// this.zLevel = 100.0F;
	// this.itemRender.zLevel = 100.0F;
	//
	// if (itemstack == null && slotIn.canBeHovered()) {
	// TextureAtlasSprite textureatlassprite = slotIn.getBackgroundSprite();
	//
	// if (textureatlassprite != null) {
	// GlStateManager.disableLighting();
	// this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
	// this.drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
	// GlStateManager.enableLighting();
	// flag1 = true;
	// }
	// }
	//
	// if (!flag1) {
	// if (flag) {
	// drawRect(i, j, i + 16, j + 16, -2130706433);
	// }
	//
	// GlStateManager.enableDepth();
	// this.itemRender.renderItemAndEffectIntoGUI(this.mc.thePlayer, itemstack, i, j);
	// this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
	// }
	//
	// this.itemRender.zLevel = 0.0F;
	// this.zLevel = 0.0F;
	// }
}